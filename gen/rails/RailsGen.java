package gen.rails;

import alg.io.FileUtils;
import alg.strings.StringUtils;
import alg.web.HTMLUtils;
import alg.words.WordUtils;
import gen.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class RailsGen extends Generator {

    public RailsGen (App app) {
        super(app);
    }

    @Override
    public void generate() throws Exception {
        List<String> errors = app.doPreprocessing();

        if (errors.size() > 0) {
            System.out.println("Cannot generate Rails application due to the following errors: ");
            for (String error : errors) {
                System.out.println(error);
            }
        }
        else {
            if (!FileUtils.dirExists(app.getWebAppDir()))
                generateAppStructure();
            generateDeploymentScript();
            generateGems();
            new ModelsGen(app).generateModels();

            if (app.isGenerateUpgrades())
                new DBGen(app).generateUpgrades();

            new ControllersGen(app).generateControllers();
            generateViews();

            if (app.getAppConfig().isNeedsAuth())
                generateLoginSignupPages();

            generateStaticPages();
            generateSharedPages();
            new HelpersGen(app).generateHelperMethods();
            generateAssets();

            generateRoutes();
            if (app.getAppConfig().isNeedsAuth())
                generateMailers();
            generateImageUploaders();
        }
    }

    public void generateMailers () throws Exception {
        /**
         * TODO:
         notify_admin.html.erb  send_password.html.erb ??
         */

        StringBuilder buf = new StringBuilder();

        Model userModel = app.getUserModel();
        String  userModelName = userModel.getName();

        HTMLUtils.addParagraph(buf, "Thank you for registering <%= @" + userModelName + "." + userModel.getUserIndentifierFieldName() +" %> with " + app.getTitle());
        HTMLUtils.addLineBreak(buf);
        HTMLUtils.addParagraph(buf, "You can view or change your account details <%= link_to \"here\", edit_" + userModelName + "_url(@" + userModelName + ") %>.");
        HTMLUtils.addParagraph(buf, "You can get started <%= link_to \"here\", edit_" + userModelName + "_url(@" + userModelName + ") %>.");
        HTMLUtils.addLineBreak(buf);
        HTMLUtils.addParagraph(buf, "Regards,");
        HTMLUtils.addParagraph(buf, "The " + WordUtils.capitalize(app.getTitle()) + " Team");
        FileUtils.write(buf, app.getWebAppDir() + "/app/views/" + userModelName + "_mailer/registration_confirmation.html.erb", true);

        buf = new StringBuilder();
        HTMLUtils.addParagraph(buf, "<%= @name %> with email <%= @email %> has the following comment/question: <%= @comment %>.");
        HTMLUtils.addLineBreak(buf);
        HTMLUtils.addLineBreak(buf);
        FileUtils.write(buf, app.getWebAppDir() + "/app/views/" + userModelName + "_mailer/contact_admin.html.erb", true);

        buf = new StringBuilder();
        String capName = WordUtils.capitalize(userModelName);
        StringUtils.addLine(buf, "class " + capName + "Mailer < ActionMailer::Base");
        tabbed (buf, "default from: \"admin@website.com\"");

        StringUtils.addLineBreak(buf);
        tabbed (buf, "def registration_confirmation(breeder)");
        tabbed (buf, "@" + userModelName + " = " + userModelName, 2);
        tabbed (buf, "mail(:to => " + userModelName + ".email, :subject => \"" + capName + " Registered\")", 2);
        tabbed (buf, "end");

        StringUtils.addLineBreak(buf);
        tabbed (buf, "def send_password(" + userModelName + ", newpassword)");
        tabbed (buf, "@" + userModelName + " = " + userModelName, 2);
    	tabbed (buf, "@newpassword = newpassword", 2);
    	tabbed (buf, "mail(:to => " + userModelName + ".email, :subject => \"Your Password\")", 2);
    	tabbed (buf, "end");

        StringUtils.addLineBreak(buf);
    	tabbed (buf, "def notify_admin(" + userModelName + ")");
    	tabbed (buf, "@" + userModelName + " = " + userModelName, 2);
    	tabbed (buf, "mail(:to => \"something@somewhere.com\", :subject => \"New " + capName + " Signed Up\")", 2);
    	tabbed (buf, "end");

        StringUtils.addLineBreak(buf);
    	tabbed (buf, "def contact_admin(email, name, comment)");
    	tabbed (buf, "@name = name", 2);
    	tabbed (buf, "@email = email", 2);
    	tabbed (buf, "@comment = comment", 2);
    	tabbed (buf, "mail(:to => \"something@somewhere.com\", :subject => \"Contact Us\", :from => email)", 2);
    	tabbed (buf, "end");

    	StringUtils.addLine (buf, "end");

        FileUtils.write(buf, app.getWebAppDir() + "/app/mailers/" + userModelName + "_mailer.html.erb", true);

    }

    public void generateImageUploaders () throws Exception {
        // Hmm carrierwave or paperclip?
    }

    public void generateStaticPages () throws Exception {

        generateHeader();
        generateMainLayoutPage();
        generateFooter();
        generateCustomStaticPages();
        generateHomePage();
        generateContactPage();
        generateNewsPage();
    }

    public void generateSharedPages () throws Exception {
        /**
         * <% if @user.errors.any? %>
         <div id="error_explanation">
         <div class="alert alert-danger">
         The form contains <%= pluralize(@user.errors.count, "error") %>.
         </div>
         <ul                                                      <% @user.errors.full_messages.each do |msg| %>
         <li><%= msg %></li>
         <% end %>
         </ul>
         </div>
         <% end %>
         */
        StringBuilder buf = new StringBuilder();
        HTMLUtils.addRuby(buf, "if object.errors.any?");
        HTMLUtils.addDivId(buf, "error_explanation");
        HTMLUtils.addDiv(buf, "alert alert-danger");
        StringUtils.addLine(buf, "Please correct the following <%= pluralize(object.errors.count, \"error\") %>.");
        HTMLUtils.closeDiv(buf);
        StringUtils.addLine(buf, "<ul>");
        StringUtils.addLine(buf, "<% object.errors.full_messages.each do |msg| %>");
        StringUtils.addLine(buf, "<li>* <%= msg %></li>");
        StringUtils.addLine(buf, "<% end %>");
        StringUtils.addLine(buf, "</ul>");
        HTMLUtils.closeDiv(buf);
        StringUtils.addLine(buf, "<% end %>");

        writeViewFile(buf, "shared", "_error_messages");
    }

    public void generateHeader () throws Exception {

    	StringBuilder buf = new StringBuilder();
        StringUtils.addLine(buf, "<nav class=\"navbar navbar-inverse navbar-fixed-top\">");
        HTMLUtils.addDiv(buf, "container");
        HTMLUtils.addDiv(buf, "navbar-header");

        StringUtils.addLine(buf, "<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">");
        StringUtils.addLine(buf, "<span class=\"sr-only\">Toggle navigation</span>");
        StringUtils.addLine(buf, "<span class=\"icon-bar\"></span>");
    	StringUtils.addLine(buf, "<span class=\"icon-bar\"></span>");
    	StringUtils.addLine(buf, "<span class=\"icon-bar\"></span>");
    	StringUtils.addLine(buf, "</button>");

    	StringUtils.addLine(buf, "<%= link_to \"" + app.getTitle() + "\", root_path, class: \"navbar-brand\", id: \"logo\" %>");
        HTMLUtils.closeDiv(buf);

        StringUtils.addLine(buf, "<div id=\"navbar\" class=\"navbar-collapse collapse\">");
        String searchModelName = app.getTopLevelSearchModelName();
        if (searchModelName != null) {
            StringUtils.addLine(buf, "<form action=\"/" + searchModelName + "\" class=\"navbar-form navbar-right\">");
            HTMLUtils.addDiv(buf, "form-group");
            StringUtils.addLine(buf, "<input type=\"text\" class=\"form-control\" id=\"search\" name=\"search\" placeholder=\"Search\">");
            HTMLUtils.closeDiv(buf);
            StringUtils.addLine(buf, "<button type=\"submit\" class=\"btn btn-primary\">Search</button>");
            StringUtils.addLine(buf, "</form>");
        }

        StringUtils.addLine(buf, "<ul class=\"nav navbar-nav navbar-right\">");
    	StringUtils.addLine(buf, "<% if logged_in? %>");
    	StringUtils.addLine(buf, "<li><%= link_to \"Dashboard\", root_path %></li>");
    	StringUtils.addLine(buf, "<% end %>");
    	StringUtils.addLine(buf, "<% if !logged_in? %>");
    	

        // TODO: this is not going to work if these item_paths don't exist
        for (String item : app.getStaticMenuItems()) {
    	    StringUtils.addLine(buf, "<li><%= link_to \"" + WordUtils.capitalize(item) + "\", " + item + "_path %></li>");
        }

    	StringUtils.addLine(buf, "<% end %>");

    	// TODO: needs to be configurable
        Model userModel = app.getUserModel();
        if (userModel != null) {
            String modelName = userModel.getName();

            StringUtils.addLine(buf, "<li><%= link_to \"" + WordUtils.pluralize(WordUtils.capitalize(modelName)) + "\", " + WordUtils.pluralize(modelName) + "_path %></li>");
            StringUtils.addLine(buf, "<% if logged_in? %>");
            StringUtils.addLine(buf, "<li><%= link_to \"Edit Profile\", edit_" + modelName + "_path(current_" + modelName + ") %></li>");
            StringUtils.addLine(buf, "<li class=\"divider\"></li>");
            StringUtils.addLine(buf, "<li><%= link_to \"Sign out\", signout_path, method: \"delete\" %></li>");
            StringUtils.addLine(buf, "<% else %>");
            StringUtils.addLine(buf, "<li><%= link_to \"Sign In\", signin_path %></li>");
            StringUtils.addLine(buf, "<% end %>");
        }

    	//StringUtils.addLine(buf, "<li><%= link_to \"Help\", help_path %></li>");
    	StringUtils.addLine(buf, "</ul>");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
        StringUtils.addLine(buf, "</nav>");

    	FileUtils.write(buf, app.getWebAppDir() + "/app/views/layouts/_header.html.erb", true);
    }

    public void generateMainLayoutPage () throws Exception {
        StringBuilder buf = new StringBuilder();

        StringUtils.addLine(buf, "<!DOCTYPE html>");
        StringUtils.addLine(buf, "<html>");
        StringUtils.addLine(buf, "<head>");
        tabbed(buf, "<title><%= full_title(yield(:title)) %></title>");
        HTMLUtils.addRubyOutput(buf, "stylesheet_link_tag    \"application\", :media => \"all\"");
        HTMLUtils.addRubyOutput(buf, "javascript_include_tag \"application\"");
        HTMLUtils.addRubyOutput(buf, "csrf_meta_tags");
        StringUtils.addLine(buf, "</head>");

        StringUtils.addLine(buf, "<body>");
        HTMLUtils.addRubyOutput(buf, "render 'layouts/header'");

        if (!app.isFullWidthJumbotron()) {
            StringUtils.addLine(buf, "<div class=\"container\"> ");
        }

        HTMLUtils.addRuby(buf, "flash.each do |key, value|");
        StringUtils.addLine(buf, "<div class=\"alert alert-<%= key %>\"><%= value %></div>  ");
        HTMLUtils.addRuby(buf, "end");
        HTMLUtils.addRubyOutput(buf, "yield");

        if (!app.isFullWidthJumbotron()) {
            HTMLUtils.closeDiv(buf);
        }
        HTMLUtils.addRubyOutput(buf, "render 'layouts/footer'");
        HTMLUtils.addRubyOutput(buf, "debug(params) if Rails.env.development?");
        StringUtils.addLine(buf, "</body>");
        StringUtils.addLine(buf, "</html>");

        FileUtils.write(HTMLUtils.formatHTML(buf.toString(), 2), app.getWebAppDir() + "/app/views/layouts/application.html.erb", false);
//        FileUtils.replaceInFile(app.getWebAppDir() + "/app/views/layouts/application.html.erb", new String[]{"<%= yield %>"},
//                new String [] {buf.toString()}, true, true);

    }

    public void generateFooter () throws Exception {
        StringBuilder buf = new StringBuilder();
        StringUtils.addLine(buf, "<footer class=\"footer\"> ");
        StringUtils.addLine(buf, "<small>");

        String siteName = app.getName();
        StringUtils.addLine(buf, "<a href=\"http://" + siteName + ".com\">@ 2015 " + siteName + ".com. All Rights Reserved.</a>");
        StringUtils.addLine(buf, "</small>");
        StringUtils.addLine(buf, "<nav>");
        StringUtils.addLine(buf, "<ul>");

        for (StaticPage page : app.getStaticPages()) {
            // TODO make configurable list of footer items
            StringUtils.addLine(buf, "<li><%= link_to \"" + page.getTitle() + "\", " + page.getName() + "_path %></li>");
        }
        StringUtils.addLine(buf, "</ul>");
        StringUtils.addLine(buf, "</nav>");
        StringUtils.addLine(buf, "</footer>");
        FileUtils.write(buf, app.getWebAppDir() + "/app/views/layouts/_footer.html.erb", true);
    }

    public void generateCustomStaticPages () throws Exception {

        for (StaticPage page : app.getStaticPages()) {
            StringBuilder buf = new StringBuilder();
            String title = page.getTitle();
            String name = page.getName();
            HTMLUtils.addRuby(buf, "provide(:title, '" + title + "')");
            HTMLUtils.addH1(buf, title + " Placeholder Page");

            HTMLUtils.addParagraph(buf, page.getContent(), "lead text-center");

            FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/" + name + ".html.erb", true);
        }
    }

    public void generateNewsPage () throws Exception {
        StringBuilder buf = new StringBuilder();
        HTMLUtils.addRuby(buf, "provide(:title, 'News')");
        HTMLUtils.addH1(buf, "News about " + WordUtils.capitalize(app.getName()));

        ArrayList<Blurb> blurbs = app.getNewsBlurbs();

        for (int i = 0; i < blurbs.size(); i++) {
            HTMLUtils.addParagraph(buf, blurbs.get(i).getTitle(), "lead");
            HTMLUtils.addParagraph(buf, blurbs.get(i).getContent());
            HTMLUtils.addLineBreak(buf);
        }

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/news.html.erb", true);
    }

    public void generateHomePage () throws Exception {

        /**
         * Default is a jumbotron with background image, search field, login/signup, and some menu options
         * when logged in, default is a dashboard of some sort showing main model
         */

        StringBuilder buf = new StringBuilder();

        if (app.getUserModel() != null) {
            HTMLUtils.addRuby(buf, "if logged_in?");
            Model userModel = app.getUserModel();
            HTMLUtils.addH3(buf, userModel.getCapName() + " Dashboard for <%= current_" +userModel.getName() + "." + userModel.getUserIndentifierFieldName() +" %>");

            boolean topLevelModelsInTabs = true; // TODO
            
            if (topLevelModelsInTabs) {
            	HTMLUtils.addRubyOutput(buf, "tabs_tag do |tab|");
            	for (Model model : app.getTopLevelModels()) {
            		HTMLUtils.addRubyOutput(buf, "tab." + model.getName() + "' " +
            				model.getCapName() + "', " + model.getPluralName() + "_path");
            	}
            	HTMLUtils.addRuby(buf, "end");
            }
            /**
             * <%= tabs_tag do |tab| %>
  <%= tab.home      'Homepage', root_path %>
  <%= tab.dashboard 'Dashboard', dashboard_path %>
  <%= tab.account   'Account', account_path %>
<% end %>
             */
            // TODO: generate default logged in view here
            // if it's a user, display all user properties I guess
            // display all top level models in the side menu, and/or the top menu in the header
        }
        else {
            ArrayList<Model> topLevelModels = app.getTopLevelModels();

            for (Model model : topLevelModels) {
                // if sidebar, put one in each sidebar entry in a vertical list
                // TODO: also do the same in the header
            }
        }

        HTMLUtils.addRuby(buf, "else");
        HTMLUtils.addDiv(buf, "jumbotron");
        HTMLUtils.addDiv(buf, "container");
        HTMLUtils.addH1(buf, "Welcome to " + app.getTitle());

        HTMLUtils.addParagraph(buf, app.getTagLine(), "lead text-center");

        Model  frontSearchModel = app.getFrontPageSearchModel();

        if (frontSearchModel != null) {
            String plural =  frontSearchModel.getPluralName();
            HTMLUtils.addRubyOutput(buf, "link_to \"Search " + plural + "\", " + plural + "_path, class: \"btn btn-lg btn-primary\"");
        }
        HTMLUtils.addRubyOutput(buf, "link_to \"Sign Up!\", signup_path, class: \"btn btn-default btn-lg\"");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
        HTMLUtils.addLineBreak(buf);

         Model  frontPageModelList = app.getFrontPageListModel();

         if (frontPageModelList != null)
             generateTableFor(buf, frontPageModelList, "static_pages", true, false, false);

        HTMLUtils.addRuby(buf, "end");
        FileUtils.write(HTMLUtils.formatHTML(buf.toString(), 2), app.getWebAppDir() + "/app/views/static_pages/home.html.erb", true);
    }

    public void generateTableFor(StringBuilder buf, Model model, boolean paginate) {
        generateTableFor(buf, model, null, paginate, true, true);
    }

    public void generateTableFor(StringBuilder buf, Model model, String collectionOverride, boolean paginate, boolean emptyMsg, boolean actions) {
        String pluralModelList = model.getPluralName();

        if (paginate)
            HTMLUtils.addRubyOutput(buf, "will_paginate"); // TODO figure out when to pass param here
        HTMLUtils.addRuby(buf, "if @" + (collectionOverride == null ? pluralModelList : collectionOverride) + ".any?");
        HTMLUtils.addDiv(buf, "table-responsive");
        StringUtils.addLine(buf, "<table class=\"table table-striped\">");

        ArrayList<Field> fields = model.getFields();

        // TODO: this should probably get pushed in to the render for a table view of the model
        if (fields != null) {
            String line = "<tr>";

            for (Field f : fields) {
                if (f.isAdminOnly())
                    continue;
                else if (f.getName().indexOf("password") != -1)
                    continue;

                line += "<th><%= sortable \"" + f.getName() + "\", \"" + WordUtils.capitalizeAndSpace(f.getName()) + "\" %></th>";
            }
            if (actions)
                line += "<th>Actions</th>";

            line += "</tr>";
            StringUtils.addLine(buf, line);
        }
        //HTMLUtils.addRuby(buf, "@" + (collectionOverride == null ? pluralModelList : collectionOverride) + ".each do |" + model.getName() + "|");
        //HTMLUtils.addRubyOutput(buf, "render " + model.getName() + ", locals: {show_actions: " + actions + "}");
        HTMLUtils.addRubyOutput(buf, "render partial: \"" + pluralModelList + "/" + model.getName() + "\", collection: @" +
                (collectionOverride == null ? pluralModelList : collectionOverride) + ", locals: {show_actions: " + actions + "}");
        //HTMLUtils.addRuby(buf, "end");

        //HTMLUtils.addRubyOutput(buf, "will_paginate @" + model.getPluralName());
        StringUtils.addLine(buf, "</table>");
        HTMLUtils.closeDiv(buf);
        tabbed(buf, "<% else %>");
        tabbed(buf, "<p> No " + model.getPluralName() + " yet.</p>");
        tabbed(buf, "<% end %>");

        if (paginate)
            HTMLUtils.addRubyOutput(buf, "will_paginate");
    }

    public void generateTablePartial(StringBuilder buf, Model model) {
        String pluralModelList = model.getPluralName();
        String modelName = model.getName();

        ArrayList<Field> fields = model.getFields();

        if (fields != null) {
            String line = "<tr>";

            for (Field f : fields) {
                String fieldName = f.getName();
                Type    fType = f.getTheType();

                if (f.isAdminOnly())
                    continue;
                else if (f.getName().indexOf("password") != -1)
                    continue;

                if (fType.equals(Type.BOOLEAN))
                    line += "<td><%= yesno(" + modelName + "." + fieldName + ") %></td>";
                else if (fType.equals(Type.CURRENCY))
                    line += "<td><%= number_to_currency(" + modelName + "." + fieldName + ", :unit => \"$\") %></td>";
                else if (fType.equals(Type.IMAGE)) {
                    StringBuilder imgBuilder = new StringBuilder("<td>");
                    HTMLUtils.addRuby(imgBuilder, "if " + modelName + "." + fieldName + ".url.nil?"); // TODO remove hardcoding
                    HTMLUtils.addRubyOutput(imgBuilder, "image_tag(\"ImagePlaceholderSmall.png\")");
                    HTMLUtils.addRuby(imgBuilder, "else");
                    //<%= image_tag micropost.picture.url if micropost.picture? %>
                    HTMLUtils.addRubyOutput(imgBuilder, "image_tag(" + modelName + "." + fieldName + ".url, :size => \"50x50\", :style => \"width: 50px; height: 50px\")");
                    HTMLUtils.addRuby(imgBuilder, "end");
                    imgBuilder.append("</td>");
                    line += imgBuilder.toString();
                }
                else if (fType.equals(Type.URL))
                    line += "<td><%= " + modelName + "." + fieldName + " %></td>";
                else if (fieldName.equals("created_at"))
                    line += "<td><%= time_ago_in_words(" + modelName + "." + fieldName + ")%> ago</td>";
                else if (fieldName.equals("name") || fieldName.equals("username"))
                    line += "<td><%= link_to " + modelName + "." + fieldName + ", " + modelName + " %></td>";
                else
                    line += "<td><%= " + modelName + "." + fieldName + " %></td>";
            }

            StringUtils.addLine(buf, line);

            HTMLUtils.addRuby(buf, "if show_actions");
            // Actions:
            StringUtils.addLine(buf, "<td>");
            //HTMLUtils.addRuby(buf, "if current_" + app.getUserModel().getName() + "?(" + modelName + ".user)"); // || (current_user != nil && current_user.admin?)");
            HTMLUtils.addRuby(buf, "if current_" + app.getUserModel().getName() + " != nil"); // || (current_user != nil && current_user.admin?)");
            //HTMLUtils.addRuby(buf, "if true");
            HTMLUtils.addRubyOutput(buf, "link_to \"edit\", edit_" + modelName + "_path(" + modelName + "), :class => 'btn btn-mini'");
            HTMLUtils.addRuby(buf, "if " + modelName + ".disabled");
            HTMLUtils.addRubyOutput(buf, "link_to \"enable\", enable_" + modelName + "_path(" + modelName + "), :class => 'btn btn-mini'");
           // HTMLUtils.addRuby(buf, "else");
            //HTMLUtils.addRubyOutput(buf, "link_to \"disable\", disable_" + modelName + "_path(" + modelName + "), :class => 'btn btn-mini'");
            HTMLUtils.addRuby(buf, "end");
            HTMLUtils.addRubyOutput(buf, "link_to \"delete\", " + modelName + ", :class => 'btn btn-mini btn-danger', method: :delete, " +
             "confirm: \"You sure you want to delete this " + modelName + "?\"," +
             "name: " + modelName + "." + model.getUserIndentifierFieldName());  // TODO don't hardcode
            HTMLUtils.addRuby(buf, "end");

            StringUtils.addLine(buf, "</td>");
            HTMLUtils.addRuby(buf, "end");
            StringUtils.addLine(buf, "</tr>");

        }
    }

    public void generateLoginSignupPages () throws Exception {

        Model userModel = app.getUserModel();
        String userModelName = userModel.getName();

        StringBuilder buf = new StringBuilder();
        HTMLUtils.addRuby(buf, "provide(:title, \"Sign in\")");
        HTMLUtils.addH1(buf, "Sign In");

        generateFormForStart(buf, "session");

        generateFormField(buf, "email", "text", 6);
        generateFormField(buf, "password", "password", 6);

        generateCheckboxField(buf, "remember_me", "Remember me on this computer", 6);

        generateFormEnd(buf, "Sign in", 6);
        HTMLUtils.addLineBreak(buf);
        HTMLUtils.addDiv(buf, "row");
        HTMLUtils.addDiv(buf, "col-sm-offset-4 col-sm-3");
        StringUtils.addLine(buf, "Forgot password? <%= link_to \"Reset password\", send_password_path %>");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.addDiv(buf, "col-sm-3");
        StringUtils.addLine(buf, "New " + userModelName + "? <%= link_to \"Sign up now!\", signup_path, {:class => \"btn btn-default\"} %>");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/sessions/new.html.erb", true);
    }

    private void generateFormForStart (StringBuilder buf, String modelName) {
        HTMLUtils.addRubyOutput(buf, "form_for(:" + modelName + ", url: " + WordUtils.pluralize(modelName) + "_path, :html => {:class => \"form-horizontal\" }) do |f|");
    }

    private void generateFormEnd (StringBuilder buf, String buttonText, int width) {
        /**
         * <div class="form-group">
         <div class="col-sm-offset-2 col-sm-8">
         */
        HTMLUtils.addDiv(buf, "form-group");
        HTMLUtils.addDiv(buf, "col-sm-offset-" + (COL_WIDTH - width) + " col-sm-" + width);
        HTMLUtils.addRubyOutput(buf, "f.submit \"" + buttonText + "\", class: \"btn btn-lg btn-primary\" ");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
        HTMLUtils.addRuby(buf, "end");
    }

    private void generateFormField (StringBuilder buf, String name) {
        generateFormField(buf, name, "text");
    }

    private static final int COL_WIDTH = 10; // todo move
    private void generateCheckboxField (StringBuilder buf, String name, String text, int width) {
        HTMLUtils.addDiv(buf, "form-group");

        HTMLUtils.addDiv(buf, "col-sm-offset-" + (COL_WIDTH - width) + " col-sm-" + width);
        HTMLUtils.addRubyOutput(buf, "f.label :" + name + ", class: \"checkbox inline\" do");
        HTMLUtils.addRubyOutput(buf, "f.check_box :" + name);
        HTMLUtils.addSpan(buf, text);
        HTMLUtils.addRuby(buf, "end");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    private void generateTextArea (StringBuilder buf, String name, int rows) {
        HTMLUtils.addDiv(buf, "form-group");
        HTMLUtils.addRubyOutput(buf, "f.label(:" + name + ", class: \"col-sm-2 control-label\") ");
        HTMLUtils.addDiv(buf, "col-sm-8");
        HTMLUtils.addRubyOutput(buf, "f.text_area(:" + name + ", :rows => \"" + rows + "\", class: \"form-control\") ");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    private void generateFormField (StringBuilder buf, String name, String fieldType) {
        generateFormField(buf, name, fieldType, 8);
    }

    private void generateFormField (StringBuilder buf, String name, String fieldType, int width) {
        HTMLUtils.addDiv(buf, "form-group");

        int offset = COL_WIDTH - width;
        HTMLUtils.addRubyOutput(buf, "f.label(:" + name + ", class: \"col-sm-" + offset + " control-label\") ");
        HTMLUtils.addDiv(buf, "col-sm-" + width);
        HTMLUtils.addRubyOutput(buf, "f." + fieldType + "_field(:" + name + ", class: \"form-control\") ");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    private void generateStaticFormField (StringBuilder buf, String modelName, String fieldName) {
        HTMLUtils.addDiv(buf, "form-group");
        HTMLUtils.addRubyOutput(buf, "f.label(:" + fieldName + ", class: \"col-sm-2 control-label\") ");
        HTMLUtils.addDiv(buf, "col-sm-8");
        HTMLUtils.addParagraph(buf, "<%= @" + modelName + "." + fieldName + " %>", "form-control-static");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    private void generateCurrencyField (StringBuilder buf, String name) {
        HTMLUtils.addDiv(buf, "form-group");
        HTMLUtils.addRubyOutput(buf, "f.label(:" + name + ", class: \"col-sm-2 control-label\") ");
        HTMLUtils.addDiv(buf, "col-sm-8");
        HTMLUtils.addDiv(buf, "input-group");
        HTMLUtils.addDivWithContent(buf, "input-group-addon", "$");
        HTMLUtils.addRubyOutput(buf, "f.text_field(:" + name + ", class: \"form-control\") ");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    private void generateDurationField (StringBuilder buf, String name, Type subType) {
        HTMLUtils.addDiv(buf, "form-group");
        HTMLUtils.addRubyOutput(buf, "f.label(:" + name + ", class: \"col-sm-2 control-label\") ");
        HTMLUtils.addDiv(buf, "col-sm-8");
        String typeName = subType == null ? "Minutes:Hours" : subType.getName();
        HTMLUtils.addRubyOutput(buf, "f.text_field(:" + name + ", class: \"form-control\", placeholder:  \"" + typeName + "\") ");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    private void generateRangeFields (StringBuilder buf, String name, Type subType) {
        HTMLUtils.addDiv(buf, "form-group");
        HTMLUtils.addRubyOutput(buf, "f.label(:" + name + ", class: \"col-sm-2 control-label\") ");
        String fieldType = "text"; // TODO switch on subType
        HTMLUtils.addDiv(buf, "col-sm-3");
        HTMLUtils.addRubyOutput(buf, "f." + fieldType + "_field(:" + name + ", class: \"form-control\") ");
        StringUtils.addLine(buf, "...");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.addDiv(buf, "col-sm-3");
        HTMLUtils.addRubyOutput(buf, "f." + fieldType + "_field(:" + name + ", class: \"form-control\") ");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    private void generateFileUploadField (StringBuilder buf, String name) {
        HTMLUtils.addDiv(buf, "form-group");
        rubyout(buf, "f.label(:" + name + ", \"" + WordUtils.capitalize(name) + " File\", class: \"col-sm-2 control-label\") ");
        HTMLUtils.addDiv(buf, "col-sm-8");
        //rubyout(buf, "file_for(@" + name + ")");         // TODO support for file_for
        HTMLUtils.addDiv(buf, "fileUpload btn");
        aline(buf, "<span>Change File</span>");
        rubyout(buf, "f.file_field :" + name + ", :class => \"form-control upload\"");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    private void generateRadioButtonsField (StringBuilder buf, String name) {
        HTMLUtils.addDiv(buf, "form-group");
        HTMLUtils.addRubyOutput(buf, "f.label(:" + name + ", class: \"col-sm-2 control-label\") ");
        HTMLUtils.addDiv(buf, "col-sm-8");
        // TODO finish
        HTMLUtils.addRubyOutput(buf, "f.radio_button(:" + name + ", class: \"form-control\") ");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
        /**
         * <div class="form-group">
         <div class="controls-row"
         <label class="radio inline">Weaned <%= f.radio_button :weened, true %> Unweaned <%= f.radio_button :weened, false %></label>
         </div>

         </div>

         */
    }

    public void generateContactPage () throws Exception {

        StringBuilder buf = new StringBuilder();
        HTMLUtils.addRuby(buf, "provide(:title, 'Contact')");
        HTMLUtils.addH1(buf, "Contact Us");

        HTMLUtils.addForm(buf, "submitcontact", "form-horizontal");

        StringUtils.addLine(buf, "<%= hidden_field_tag :authenticity_token, form_authenticity_token %>");

        HTMLUtils.addFormElement(buf, "email", "string", "Your Email");
        HTMLUtils.addFormElement(buf, "name", "string", "Your Name");
        HTMLUtils.addFormElement(buf, "comment", "long_string", "Your Message");

        HTMLUtils.addDiv(buf, "form-group");
        HTMLUtils.addDiv(buf, "col-sm-offset-2 col-sm-8");
        HTMLUtils.addSubmitButton(buf, "commit", "Send Message");

        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);

        StringUtils.addLine(buf, "</form>");

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/contact.html.erb", true);
    }

    public void generateRoutes () throws Exception {
        Model       userModel = app.getUserModel();
        String      pluralName = userModel.getPluralName();
        List<Model> models = app.getModels();

        List<String> routeLines = FileUtils.getLinesCreateIfEmpty(app.getWebAppDir() + "/config/routes.rb");

        int index = 2;
        for (Model model : models) {
            boolean didIt = insertTabbedIfNotThere(routeLines, "resources :" + model.getPluralName() + " do", index++);

            if (!didIt)
                continue;

            ArrayList<Rel>  rels = model.getRelationships();

            if (rels != null && rels.size() > 0) {
                String s = "get ";
                for (int i = 0; i < rels.size(); i++) {
                    Rel rel = rels.get(i);
                    if (rel.getRelType().equals(RelType.ONE_TO_MANY)) {
                        s += ":" + rel.getModel().getPluralName();
                        if (i < rels.size() - 1)
                            s += ", ";
                    }
                }
                if (!s.equals("get "))
                    insertTabbed(routeLines, "\t" + s, index++);
            }
            insertTabbed(routeLines, "end", index++);
        }

        insertTabbedIfNotThere(routeLines, "resources :sessions, only: [:new, :create, :destroy]", index++);

        //StringUtils.addLineBreak(buf);
        insertTabbedIfNotThere(routeLines, "root to: 'static_pages#home'", index++);
        //StringUtils.addLineBreak(buf);

        for (StaticPage page : app.getStaticPages()) {
            String pageName = page.getName();
            insertTabbedIfNotThere(routeLines, "get '" + pageName + "'   => 'static_pages#" + pageName +"'", index++);
        }

        insertTabbedIfNotThere(routeLines, "match '/submitcontact', to: 'static_pages#submitcontact', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/signup', to: '" + pluralName + "#new', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/send_password', to: '" + pluralName + "#send_password', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/submit_send_password', to: '" + pluralName + "#submit_send_password', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/signin', to: 'sessions#new', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/signout', to: 'sessions#destroy', via: :delete", index++);

        FileUtils.putLines(routeLines, app.getWebAppDir() + "/config/routes.rb");

        /**
        *
         * // TODO: what does this get :on construction do again??
        resources :listings do
        get :disable, :on => :member
        get :enable, :on => :member
        end
        */
    }

    public void generateAssets () throws Exception {

        FileUtils.insertAfterInFileIfNotExists(app.getWebAppDir() + "/app/assets/javascripts/application.js", "//= require jquery_ujs",
                "//= require bootstrap", true, true);

        // Where is datepicker now?
//        FileUtils.insertAfterInFileIfNotExists(app.getWebAppDir() + "/app/assets/javascripts/application.js", "//= require jquery_ujs",
//                "//= require jquery-ui", true, true);
//
//        FileUtils.insertAfterInFileIfNotExists(app.getWebAppDir() + "/app/assets/stylesheets/application.css", " *= require_self",
//               " *= require jquery-ui", true, true);

        FileUtils.copyTextFile("C:/Users/jsanchez/Downloads/apps/resources/simple2.css.scss", app.getWebAppDir() + "/app/assets/stylesheets/custom.css.scss");
        FileUtils.copyFile("C:/Users/jsanchez/Downloads/apps/resources/ImagePlaceholderSmall.png", app.getWebAppDir() + "/app/assets/images/ImagePlaceholderSmall.png");
        if (app.getJumbotronImageUrl() != null)
            addStyle(new String[] {".jumbotron {", "\tbackground-image: url('" + app.getJumbotronImageUrl() + "');", "}"});

        if (app.getColorScheme() != null) {
            ColorScheme scheme = app.getColorScheme();
            //$jumbotron-color: #007700;
            //$jumbotron-heading-color: #007700;

            HashMap<String, String> varToColorMap = new HashMap<String, String>();
            varToColorMap.put("jumbotron-color", scheme.getFirstAccent());
            varToColorMap.put("jumbotron-heading-color", scheme.getFirstAccent());
            varToColorMap.put("body-bg", scheme.getBgPrimary());
            varToColorMap.put("text-color", scheme.getLettering());
            varToColorMap.put("link-color", scheme.getSecondAccent());
            varToColorMap.put("btn-default-bg", scheme.getSecondAccent());
            if (!scheme.getSecondAccent().equals("FFFFFF"))
                varToColorMap.put("btn-default-color", "FFFFFF"); // TODO better logic here for contrasting button text color
            //varToColorMap.put("navbar-default-color", scheme.getFirstAccent());
            varToColorMap.put("brand-primary", scheme.getFirstAccent());
            varToColorMap.put("brand-info", scheme.getFirstAccent());
            varToColorMap.put("navbar-inverse-bg", scheme.getBgSecondary());

            // TODO: full navbar, buttons, tables, forms, header, footer
            overrideStyles(varToColorMap);

        }
        // TODO: not sure we need to do this if running with --without production:
        //String 	railsCmd = app.isWindows() ? "C:/RailsInstaller/Ruby2.1.0/bin/rake.bat" : "rake";

        //runCommandInApp(railsCmd + " assets precompile");
    }

    public void addStyle (String [] styleInfo) throws Exception {
        //FileUtils.append(app.getWebAppDir() + "/app/assets/stylesheets/bootstrap_and_overrides.css.less", styleInfo);
        FileUtils.insertInFileIfNotExists(app.getWebAppDir() + "/app/assets/stylesheets/custom.css.scss", styleInfo);
    }

    public void overrideStyles (HashMap<String, String> varToColorMap) throws Exception {
        //FileUtils.append(app.getWebAppDir() + "/app/assets/stylesheets/bootstrap_and_overrides.css.less", styleInfo);
        String [] lines = new String[varToColorMap.size()];
        int index = 0;
        Set<String> keys = varToColorMap.keySet();
        for (String key : keys) {
            lines [index++] = "$" + key + ": #" + varToColorMap.get(key) + ";";
        }
        FileUtils.prependInFileIfNotExists(app.getWebAppDir() + "/app/assets/stylesheets/custom.css.scss", lines);
    }


    private void tabbed (StringBuilder buf, String content) {
        StringUtils.addTabbedLine(buf, content);
    }

    private void tabbed (StringBuilder buf, String content, int tabs) {
        StringUtils.addTabbedLine(buf, content, tabs);
    }

    public void generateViews () throws Exception {
        ArrayList<Model>  models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                generateShowView(model);
                generateListView(model);
                generateEditView(model, true);
                generateEditView(model, false);
            }
        }
    }

    public boolean useTabs (Model model) {
        boolean useTabs;
        switch (app.getAppConfig().getComplexModelLayout()) {
             case TABS:
                 return (false);

            case DYNAMIC:
                return (model.getRelationships() != null && model.getRelationships().size() > 3);

            default:
                return (false);
        }
    }

    public void generateShowView (Model model) throws Exception {

        String              name = model.getName();
        String              names = model.getPluralName();
        ArrayList<Field>    fields = model.getFields();

        StringBuilder       bodyContent = new StringBuilder();

        StringUtils.addLine(bodyContent, "<dl class=\"dl-horizontal\">");
        if (fields != null) {
            for (Field f : fields) {

                String  fName = f.getName();
                Type    fType = f.getTheType();
                String  fTypeName = fType.getName();

                if (fName.startsWith("password") || f.isAdminOnly())
                    continue;

                if (model.isDependent()) {
                    if (fName.equals("updated_by") || fName.equals("created_by") || fName.equals("updated_at") || fName.equals("created_at"))
                        continue;
                }
                // TODO: range generation

                String nameFName = name + "." + fName;
                String capName = WordUtils.capitalize(fName);
                if (fTypeName.equals(Type.SET_ONE_OR_MORE) || fTypeName.equals(Type.LIST)) {
                    if (fType.getSubtype().isPrimitive()) {
                        // TODO: if it's a list, display vertically, if it's a set, just put it in a line
                        generateReadOnlySection(bodyContent, "@" + nameFName, fName);
                    }
                    else {
                        generateSublistViewReadonly(bodyContent, name, fName);
                    }
                }
//                else if (fName.equals("name")) {
//                    HTMLUtils.addH3(bodyContent, "<%= @" + nameFName + " %>");
//                }
                else if (fTypeName.equals(Type.IMAGE.getName())) {
                    generateReadOnlySection(bodyContent, "image_for(@" + nameFName + ")", fName);
                }
                else if (fTypeName.equals(Type.DATETIME.getName())) {
                    generateReadOnlySection(bodyContent, "@" + nameFName, fName);
                }
                else if (fTypeName.equals(Type.CODE.getName())) {
                    // TODO: why isn't RedCloth working?
                	//generateReadOnlySection(bodyContent, "RedCloth.new(CodeRay.scan(@" + nameFName + ", :ruby).div(:line_numbers => :table)).to_html", fName);
                    generateReadOnlySection(bodyContent, "CodeRay.scan(@" + nameFName + ", :ruby).div(:line_numbers => :table).html_safe", fName);
                }
                else if (fTypeName.equals(Type.CURRENCY.getName())) {
                    generateReadOnlySection(bodyContent, "number_to_currency(@" + nameFName + ", :unit => \"$\")", fName);
                }
                else if (fTypeName.equals(Type.ADDRESS.getName())) {

                    generateReadOnlySection(bodyContent, "render_address(@" + nameFName + ") if @" + nameFName + "?", fName);
                }
                else if (fTypeName.equals(Type.URL.getName())) {
                    //StringUtils.addLine(bodyContent, "<% if @" + nameFName + "!= nil %>");
                    String content  = "        <a href=\"<%= render_website(@" + nameFName + ") %>\" target=\"_blank\"><%= @" + nameFName +" %></a>";
                    generateReadOnlySectionNoRuby(bodyContent, content, fName);
                    //StringUtils.addLine(bodyContent, "<% end %>");
                }
                else if (fTypeName.equals(Type.BOOLEAN.getName())) {
                    generateReadOnlySection(bodyContent, "yesno(@" + nameFName + ")", fName);
                }
                else {
                    generateReadOnlySection(bodyContent, "@" + nameFName, fName);
                }

                StringUtils.addLineBreak(bodyContent);
            }
        }
        StringUtils.addLine(bodyContent, "</dl>");

        boolean             useTabs = useTabs(model);
        ArrayList<Rel>      rels = model.getRelationships();
        if (rels != null) {
            /**
             * For each rel:
             *
             * if it's one-to-one, display link_to name and the show path
             * if it's one-to-many or many-to-many, display read only table with link_to on name columns
             */
            StringUtils.addLine(bodyContent, "<dl class=\"dl-horizontal\">");
            for (Rel rel : rels) {
                RelType rt = rel.getRelType();
                String fName = rel.getModel().getName();
                String nameFName = name + "." + fName;
                String relNameDisplayName = nameFName + "." + rel.getModel().getUserIndentifierFieldName();
                switch (rt) {
                    case ONE_TO_ONE:
                        generateReadOnlySection(bodyContent, "@" + nameFName, fName);
                        //fieldDisplayName, String fieldName, String linkPath
                        /**
                         * <% if @blog.user != nil %>
                         <%= link_to @blog.user.username, user_path(@blog.user) %>
                         <% else %>
                         None
                         <% end %>

                         */
                        generateReadOnlyLinkSection(bodyContent, WordUtils.capitalizeAndSpace(rel.getModel().getName()), relNameDisplayName, nameFName, fName + "_path");
                        break;

                    case ONE_TO_MANY:
                        generateSublistViewAsTableReadonly(bodyContent, name, rel.getModel());
                        break;

                    case MANY_TO_MANY:
                        generateSublistViewAsTableReadonly(bodyContent, name, rel.getModel());
                        break;

                    case MANY_TO_ONE:
                        generateReadOnlyLinkSection(bodyContent, WordUtils.capitalizeAndSpace(rel.getModel().getName()), relNameDisplayName, nameFName, fName + "_path");
                        //generateReadOnlyLinkSection(bodyContent, "link_to @" + nameFName, fName + ", show_" + fName + "_path");
                        break;
                }
            }
            StringUtils.addLine(bodyContent, "</dl>");

        }

        /**
         * TODO: fix this logic... should be:
         * if user is logged in and owns this object, show the link
         * else if user is logged in and anyone can edit it, show edit link (uncommon scenario)
         * else if user is admin, allow edit
         */
        StringUtils.addLine(bodyContent, "<% if logged_in? %>");
        StringUtils.addLine(bodyContent, "<%= link_to \"Edit " + model.getCapName() + "\", edit_" + name + "_path(@" + name + "), class: \"btn btn-default btn-sm\" %></li>");
        StringUtils.addLine(bodyContent, "<% end %>");

        Layout layout = app.getAppConfig().getLayout();

        if (layout == null)
            layout = Layout.ONE_COL;

        StringBuilder       buf = new StringBuilder();
        // TODO: need to not hardcode this
        StringUtils.addLine(buf, "<% provide(:title, @" + name + "." + model.getUserIndentifierFieldName() + ") %>");

        HTMLUtils.addDiv(buf, "page-header");
        HTMLUtils.addH1(buf, model.getCapName() + ": <%= @" + name + "." + model.getUserIndentifierFieldName() + " %>");
        HTMLUtils.closeDiv(buf);

        switch (layout) {
            case TWO_COL_THIN_LEFT: {
                String left = inDiv(getLeftSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(left + body, "row-fluid"), "container-fluid"));
            }
            break;

            case ONE_COL: {
                String body = inDiv(bodyContent.toString(), "span10"); // TODO
                buf.append(inDiv(inDiv(body, "row-fluid"), "container-fluid"));
            }
            break;

            case ONE_COL_FIXED_WIDTH: {
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(body, "row-fluid"), "container-fluid"));
            }
            break;

            case TWO_COL_THIN_RIGHT: {
                String right = inDiv(getRightSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(body + right, "row-fluid"), "container-fluid"));
            }
            break;

            case THREE_COL: {
                String left = inDiv(getRightSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span8");
                String right = inDiv(getRightSidebarContent(model), "span2");
                buf.append(inDiv(inDiv(left + body + right, "row-fluid"), "container-fluid"));
            }
            break;
        }

        writeViewFile(buf, names, "show");
    }

    private void generateReadOnlySection (StringBuilder bodyContent, String fieldDetails, String fieldName) {
        StringUtils.addLine(bodyContent, "<dt>" + WordUtils.capitalizeAndSpace(fieldName) + "</dt><dd>");
        HTMLUtils.addRubyOutput(bodyContent, fieldDetails);
        StringUtils.addLine(bodyContent, "</dd>");
    }

    private void generateReadOnlySectionNoRuby (StringBuilder bodyContent, String fieldDetails, String fieldName) {
        StringUtils.addLine(bodyContent, "<dt>" + WordUtils.capitalizeAndSpace(fieldName) + "</dt><dd>");
        StringUtils.addLine(bodyContent, fieldDetails);
        StringUtils.addLine(bodyContent, "</dd>");
    }

    private void generateReadOnlyLinkSectionInForm (StringBuilder bodyContent, String fieldDisplayName, String fieldName, String linkPath, boolean addButton) {
        HTMLUtils.addDiv(bodyContent, "form-group");
        StringUtils.addLine(bodyContent, "<label class=\"control-label col-sm-2\">" + fieldDisplayName + "</label>");
        HTMLUtils.addDiv(bodyContent, "col-sm-8");
        HTMLUtils.addRuby(bodyContent, "if @" + fieldName + " != nil");
        StringUtils.addLine(bodyContent, "<%= link_to @" + fieldName + ".id, " + linkPath + "(@" + fieldName + ") %>");
        HTMLUtils.addRuby(bodyContent, "else");
        StringUtils.addLine(bodyContent, "None");
        HTMLUtils.addRuby(bodyContent, "end");
        if (addButton) {
            // TODO: this button needs to allow use to select another item - either list selection, lookup field, popup from a table select?
            HTMLUtils.addSmallButton(bodyContent, "change", "Change", "button");
        }
        HTMLUtils.closeDiv(bodyContent);
        HTMLUtils.closeDiv(bodyContent);
    }

    private void generateReadOnlyLinkSection (StringBuilder bodyContent, String fieldDisplayValue, String fieldDisplayName, String fieldName, String linkPath) {
        StringUtils.addLine(bodyContent, "<dt>" + fieldDisplayValue + "</dt><dd>");
        /**
         * <% if @blog.user != nil %>
         <%= link_to @blog.user.username, user_path(@blog.user) %>
         <% else %>
         None
         <% end %>

         */
        HTMLUtils.addRuby(bodyContent, "if @" + fieldName + " != nil");
        //"Breeder Profile", breeder_path(@listing.breeder),
        StringUtils.addLine(bodyContent, "<%= link_to @" + fieldDisplayName + ", " + linkPath + "(@" + fieldName + ") %>");
        HTMLUtils.addRuby(bodyContent, "else");
        StringUtils.addLine(bodyContent, "None");
        HTMLUtils.addRuby(bodyContent, "end");
        StringUtils.addLine(bodyContent, "</dd>");
        //StringUtils.addLine(bodyContent, "</dd>");
    }

    private void generateSublistViewReadonly (StringBuilder builder, String modelName, String collectionName) throws Exception {
        String pluralColName = WordUtils.pluralize(collectionName);
        StringUtils.addLine(builder, "<dt>" + WordUtils.capitalize(pluralColName) + "</dt><dd>");
        StringUtils.addLine(builder, "<% if @" + modelName + "." + pluralColName + ".any? %>");

        tabbed(builder, "<%= render @" + modelName + "." + pluralColName + " %>");
        tabbed(builder, "<% else %>");
        tabbed(builder, "No " + pluralColName + " for this " + modelName + " yet.");
        tabbed(builder, "<% end %>");

        StringUtils.addLine(builder, "</dd>");
    }

    private void generateSublistViewAsTableReadonly (StringBuilder builder, String modelName, Model collectionModel) throws Exception {
        String pluralColName = WordUtils.pluralize(collectionModel.getName());
        StringUtils.addLine(builder, "<dt>" + WordUtils.capitalize(pluralColName) + "</dt><dd>");

        generateTableFor(builder, collectionModel, modelName + "." + pluralColName, false, true, false);

        StringUtils.addLine(builder, "</dd>");
    }

    //generateTableFor(builder, relModel, fullCollectionName, false, true);

    private void generateSublistView (StringBuilder builder, String modelName, String collectionName, boolean addAddBtn, String additionalParams, Model relModel) throws Exception {
        String pluralColName = WordUtils.pluralize(collectionName);
        StringUtils.addLine(builder, "<label class=\"control-label col-sm-2\">" + WordUtils.capitalize(pluralColName) + "</label>");
        HTMLUtils.addDiv(builder, "col-sm-8");
        String fullCollectionName = modelName + "." + pluralColName;
        //StringUtils.addLine(builder, "<% if @" + collectionName + ".any? %>");

        //tabbed(builder, "<%= render @" + modelName + "." + pluralColName + " %>");

        generateTableFor(builder, relModel, fullCollectionName, false, true, true);

        if (addAddBtn) {
            tabbed(builder, "<% if logged_in? && current_" + modelName + "?(@" + modelName + ") %>");
            String path = "new_" + collectionName + "_path";
            if (additionalParams != null) {
                path += "(" + additionalParams + ")";
            }
            tabbed(builder, "<%= link_to \"Add " + collectionName + "\", " + path + ", class: \"btn btn-default btn-sm\" %>");
            tabbed(builder, "<% end %>");
        }
        HTMLUtils.closeDiv(builder);
    }

    public void generateListView (Model model) throws Exception {

        String name = model.getName();
        String names = model.getPluralName();
        ArrayList<Field>    fields = model.getFields();
        ArrayList<Rel>      rels = model.getRelationships();

        StringBuilder buf = new StringBuilder();

        ModelLayout modelLayout = app.getAppConfig().getComplexModelLayout();

        StringBuilder bodyContent = new StringBuilder();

        HTMLUtils.addDiv(bodyContent, "page-header");
        HTMLUtils.addH1(bodyContent, WordUtils.pluralize(model.getCapName()));
        HTMLUtils.closeDiv(bodyContent);

        generateTableFor(bodyContent, model, false);

        switch (app.getAppConfig().getLayout()) {
            case TWO_COL_THIN_LEFT: {
                String left = inDiv(getLeftSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(left + body, "row-fluid"), "container-fluid"));
            }
            break;

            case ONE_COL: {
                String body = inDiv(bodyContent.toString(), "span10"); // TODO
                buf.append(inDiv(inDiv(body, "row-fluid"), "container-fluid"));
            }
            break;

            case ONE_COL_FIXED_WIDTH: {
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(body, "row-fluid"), "container-fluid"));
            }
            break;

            case TWO_COL_THIN_RIGHT: {
                String right = inDiv(getRightSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(body + right, "row-fluid"), "container-fluid"));
            }
            break;

            case THREE_COL: {
                String left = inDiv(getRightSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span8");
                String right = inDiv(getRightSidebarContent(model), "span2");
                buf.append(inDiv(inDiv(left + body + right, "row-fluid"), "container-fluid"));
            }
            break;
        }

        writeViewFile(buf, names, "index");

        buf = new StringBuilder();
        generateTablePartial(buf, model);
        writeViewFile(buf, names, "_" + name);
    }

    private void writeViewFile(StringBuilder buf, String subdir, String fileName) throws Exception {

        FileUtils.write(HTMLUtils.formatHTML(buf.toString(), 2), app.getWebAppDir() + "/app/views/" + subdir + "/" + fileName + ".html.erb", true);
    }

    public void generateEditView (Model model, boolean isNew) throws Exception {

        String name = model.getName();
        String names = model.getPluralName();
        ArrayList<Field>    fields = model.getFields();

        StringBuilder   buf = new StringBuilder();
        boolean         useTabs = useTabs(model);
        StringBuilder   bodyContent = new StringBuilder();
        HTMLUtils.addH1(buf, (isNew ? "New " : "Edit ") + model.getCapName());

        boolean newUser = isNew && model.isSecure();

        if (fields != null) {

             HTMLUtils.addRubyOutput(bodyContent, "form_for @" + name + ", :html => {:multipart => true, :class => \"form-horizontal\" } do |f|");
             HTMLUtils.addRubyOutput(bodyContent, "render 'shared/error_messages', object: f.object");

             for (Field f : fields) {
                 if (f.isReadOnly() || f.isAdminOnly() || f.isComputed())
                     continue;

                 // Special case: minimize the new user form:
                 if (newUser) {
                     if (!(f.getName().equals("email") || f.getName().equals("name") || f.getName().equals("username") || f.getName().indexOf("password") != -1))
                         continue;
                 }
                 else {
                     // Don't typically allow editing these properties
                     if (f.getName().indexOf("password") != -1)
                         continue;
                 }

                 String fName = f.getName();

                 Type fType = f.getTheType();
                 String capitalized = WordUtils.capitalizeAndSpace(fName);

                 if (fType.equals(Type.BOOLEAN)) {
                     generateCheckboxField(bodyContent, fName, capitalized + "?", 8);
                 }
                 else if (f.getName().equals("username") && !newUser) {
                     generateStaticFormField(bodyContent, model.getName(), fName);
                 }
                 else if (fType.equals(Type.SHORT_STRING)) {
                     generateFormField(bodyContent, fName);
                 }
                 else if (fType.equals(Type.LONG_STRING)) {
                     generateTextArea(bodyContent, fName, 5);
                 }
                 else if (fType.equals(Type.CODE)) {
                     generateTextArea(bodyContent, fName, 5);
                 }
                 else if (fType.equals(Type.STRING)) {
                     generateTextArea(bodyContent, fName, 2);
                 }
                 else if (fType.equals(Type.IMAGE)) {
                     HTMLUtils.addDiv(bodyContent, "form-group");
                     HTMLUtils.addRubyOutput(bodyContent, "f.label(:" + fName + ", class: \"col-sm-2 control-label\") ");
                     HTMLUtils.addDiv(bodyContent, "col-sm-8");
                     rubyout(bodyContent, "image_for(@" + name + "." + fName + ")");
                     HTMLUtils.addDiv(bodyContent, "fileUpload btn");
                     //aline(bodyContent, "<span>Change Image</span>");
                     rubyout(bodyContent, "f.file_field :" + fName + ", :class => \"upload\"");
                     HTMLUtils.closeDiv(bodyContent);
                     HTMLUtils.closeDiv(bodyContent);
                     HTMLUtils.closeDiv(bodyContent);
                 }
                 else if (fType.equals(Type.PHONE)) {
                     generateFormField(bodyContent, fName, "phone");
                 }
                 else if (fType.equals(Type.DURATION)) {
                     generateDurationField(bodyContent, fName, fType.getSubtype());
                 }
                 else if (fType.equals(Type.RANGE)) {
                     generateRangeFields(bodyContent, fName, fType.getSubtype());
                 }
                 else if (fType.equals(Type.CURRENCY)) {
                     generateCurrencyField(bodyContent, fName);   // TODO: currency symbol?
                 }
                 else if (fType instanceof FixedList) {

                     FixedList fl = (FixedList)fType;
                     Object []  values = fl.getValues();

                     HTMLUtils.addDiv(bodyContent, "form-group");
                     HTMLUtils.addRubyOutput(bodyContent, "f.label(:" + fName + ", class: \"col-sm-2 control-label\") ");
                     HTMLUtils.addDiv(bodyContent, "col-sm-8");

                     StringUtils.addLine(bodyContent, "<select name=\"" + name + "[" + fName + "]\" id=\"" + name + "_" + fName + "\" class=\"form-control\">");
                     StringUtils.addLine(bodyContent, "<%= options_for_select ([");

                     if (values != null) {
                         for (int i = 0; i < values.length; i++) {
                             StringUtils.addLine(bodyContent, "\"" + values [i].toString() + "\"" + (i < values.length - 1 ? ", " : ""));
                         }
                     }
                     StringUtils.addLine(bodyContent, "]) %>"); //  TODO: fix this , selected: @" + name + "." + fName + ") %>");
                     HTMLUtils.close(bodyContent, "select");
                     HTMLUtils.closeDiv(bodyContent);
                     HTMLUtils.closeDiv(bodyContent);
                 }
                 else if (fType instanceof Collection) {

                     Collection col = (Collection)fType;
                     String     colName = col.getName();
                     boolean    isOrdered = col.isOrdered();
                     Type       subType = col.getSubtype();
                     String     subTypeName = subType.getName();

                     if (colName.equals(Type.SET_PICK_ONE.getName())) {
                         aline(bodyContent, "<label>" + capitalized + "</label>");
                         aline(bodyContent, "<select id=\"" + name + "_" + fName + "\" name=\"" + name + "[" + fName + "]\" >");
                         aline(bodyContent, "<%= options_from_collection_for_select(" + WordUtils.capitalize(subType.getName()) + ".all, :name, :name, @" + name + "." + fName + ") %>");
                     }
                     else if (colName.equals(Type.SET_ONE_OR_MORE.getName())) {
                         if (subType.isPrimitive()) {
                             // TODO figure out how this should work
                         }
                         else {
                             aline(bodyContent, "<fieldset>");
                             aline(bodyContent, "<legend>" + WordUtils.capitalizeAndSpace(subTypeName) + "</legend>");
                             rubyout(bodyContent, "hidden_field_tag \"" + name + "[" + subTypeName + "_ids][]\", nil");
                             ruby(bodyContent, WordUtils.capitalize(subTypeName) + ".all.each do |" + subTypeName + "|");
                             aline(bodyContent, "<label class=\"checkbox inline\">");
                             rubyout(bodyContent, "check_box_tag \"" + name + "[" + subType.getName() + "_ids][]\", " +
                                     subTypeName + ".id, @" + name + "." + subTypeName + "_ids.include?(" + subTypeName + ".id), id: dom_id(" + subTypeName + ")");
                             rubyout(bodyContent, subType.getName() + ".name");
                             aline(bodyContent, "</label>");
                             ruby(bodyContent, "end");
                             aline(bodyContent, "</fieldset>");
                         }
                     }
                 }
                 else if (fType.equals(Type.FILE)) {
                     generateFileUploadField(bodyContent, fName);
                 }

                 else if (fType.equals(Type.SET_PICK_ONE)) {
                     generateRadioButtonsField(bodyContent, fName);
                 }
                 else if (fType.equals(Type.PASSWORD)) {
                     generateFormField(bodyContent, fName, "password");
                 }
                 else {
                     generateFormField(bodyContent, fName);
                 }

                 aline(bodyContent, "");
             }
        }


        ArrayList<Rel>      rels = model.getRelationships();
        if (!newUser && rels != null) {
            /**
             * For each rel:
             *
             * if it's one-to-one, display link_to name and the show path
             * if it's one-to-many or many-to-many, display read only table with link_to on name columns
             */

            for (Rel rel : rels) {
                RelType rt = rel.getRelType();
                String fName = rel.getModel().getName();
                String nameFName = name + "." + fName;
                String relNameField = "@" + nameFName + ".name"; // TODO
                String additionalParams = model.getName() + "_id: @" + model.getName() + ".id";
                switch (rt) {
                    case ONE_TO_ONE:
                        generateReadOnlyLinkSectionInForm(bodyContent, rel.getModel().getCapName(), nameFName, fName + "_path", true);
                        break;

                    case ONE_TO_MANY:
                        HTMLUtils.addDiv(bodyContent, "form-group");
                        //generateTableFor(bodyContent, rel.getModel());
                        generateSublistView(bodyContent, name, fName, model.isSecure(), additionalParams, rel.getModel());
                        HTMLUtils.closeDiv(bodyContent);
                        break;

                    case MANY_TO_MANY:
                        HTMLUtils.addDiv(bodyContent, "form-group");
                        generateSublistView(bodyContent, name, fName, model.isSecure(), additionalParams, rel.getModel());
                        HTMLUtils.closeDiv(bodyContent);
                        break;

                    case MANY_TO_ONE:
                        generateReadOnlyLinkSectionInForm(bodyContent, rel.getModel().getCapName(), nameFName, fName + "_path", false);
                        StringUtils.addLine(bodyContent, "<%= f.hidden_field :" + rel.getModelName() + "_id %>");
                        break;
                }
            }

        }

        generateFormEnd(bodyContent, isNew ? "Submit" : "Save", 8);

        // TODO: these side sections should be moved to the application.html.erb layout page ?
        switch (app.getAppConfig().getLayout()) {
            case TWO_COL_THIN_LEFT: {
                String left = inDiv(getLeftSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(left + body, "row-fluid"), "container-fluid"));
            }
            break;

            case ONE_COL: {
                String body = inDiv(bodyContent.toString(), "span10"); // TODO
                buf.append(inDiv(inDiv(body, "row-fluid"), "container-fluid"));
            }
            break;

            case ONE_COL_FIXED_WIDTH: {
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(body, "row-fluid"), "container-fluid"));
            }
            break;

            case TWO_COL_THIN_RIGHT: {
                String right = inDiv(getRightSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span10");
                buf.append(inDiv(inDiv(body + right, "row-fluid"), "container-fluid"));
            }
            break;

            case THREE_COL: {
                String left = inDiv(getRightSidebarContent(model), "span2");
                String body = inDiv(bodyContent.toString(), "span8");
                String right = inDiv(getRightSidebarContent(model), "span2");
                buf.append(inDiv(inDiv(left + body + right, "row-fluid"), "container-fluid"));
            }
            break;
        }

        writeViewFile(buf, names, isNew ? "new" : "edit");
    }

    public static void  aline (StringBuilder builder, String content) {
        StringUtils.addLine(builder, content);
    }

    public static void  rubyout (StringBuilder builder, String content) {
        HTMLUtils.addRubyOutput(builder, content);
    }

    public static void  ruby (StringBuilder builder, String content) {
        HTMLUtils.addRuby(builder, content);
    }

    public static String inDiv (String content, String divClass) {
        content = "<" + divClass + ">\n" + content + "</" + divClass + ">\n";

        return (content);
    }

    protected String	getLeftSidebarContent (Model m) {

        SidebarContent content = app.getLeftSidebarContent();

        if (content != null) {
            // TODO
        }
        // Hmm what is this typically? either an expandable tree of some sort, or a vertical list of options

        // Make it configurable in the app
    	return ("");// TODO
    }

    protected String	getRightSidebarContent (Model m) {
        SidebarContent content = app.getRightSidebarContent();

        if (content != null) {
            // TODO
        }
        // Hmm what is this typically? often ad space

        // Make it configurable in the app
        return ("");// TODO
    }

    protected void addMethod (StringBuilder buf, String name, String [] contentLines) {
        buf.append("\tdef " + name + "\n");
        if (contentLines != null) {
            for (String line : contentLines) {
                buf.append("\t\t" + line + "\n");
            }
        }
        buf.append("\tend\n");
        StringUtils.addLineBreak(buf);
    }



    protected void addMethodTabbed (StringBuilder buf, String name, String [] contentLines) {
        StringUtils.addLineBreak(buf);
        buf.append("\t\tdef " + name + "\n");
        if (contentLines != null) {
            for (String line : contentLines) {
                buf.append("\t\t\t" + line + "\n");
            }
        }
        buf.append("\t\tend\n");
    }

    protected void addMethod (StringBuilder buf, String name, List<String> contentLines) {
        StringUtils.addLineBreak(buf);
        buf.append("\tdef " + name + "\n");
        if (contentLines != null) {
            for (String line : contentLines) {
                buf.append("\t\t" + line + "\n");
            }
        }
        buf.append("\tend\n");
    }

    private void runCommandInApp (String command) throws Exception {

        String [] cmd = command.split(" ");
        String result = runCommandWithEnv(app.getWebAppDir(), cmd, null);

        System.out.println ("Result of running command: '" + command + "' was: " + result);
    }

    private void addGem (List<String> gemfileLines, String gemName) {
        String toAdd =  "gem '" + gemName + "'";

        // TODO: fix inefficiency
        for (String line : gemfileLines) {
            if (toAdd.equals(line))
                return; // don't add it again
        }
        gemfileLines.add(toAdd);
    }

    private boolean lineExists (List<String> lines, String toAdd) {
        for (String line : lines) {
            if (toAdd.equals(line))
                return (true);
        }
        return (false);
    }

    private boolean insertTabbedIfNotThere (List<String> lines, String toAdd, int index) {
        toAdd = "\t" + toAdd;
        if (!lineExists(lines, toAdd)) {
            lines.add(index, toAdd);
            return (true);
        }
        else
            return (false);
    }

    private void insertTabbed (List<String> lines, String toAdd, int index) {
        toAdd = "\t" + toAdd;

        lines.add(index, toAdd);
    }

    private void addGem (List<String> gemfileLines, String gemName, String version, boolean exact) {
        String toAdd =  "gem '" + gemName + "'" + (version == null ? "" : ", '" + (exact ? "" : "~> ") + version + "'");
        
        // TODO: fix inefficiency
        for (String line : gemfileLines) {
        	if (toAdd.equals(line)) 
        		return; // don't add it again
        }
        gemfileLines.add(toAdd);
    }

    private void addGemToGroup (List<String> gemfileLines, String groupName, String gemName, String version, boolean exact) {
        String toAdd =  "\tgem '" + gemName + "'" + (version == null ? "" : ", '" + (exact ? "" : "~> ") + version + "'");

        int insertIdx = -1;
        for (int i = 0; i < gemfileLines.size(); i++) {
            if (gemfileLines.get(i).equals("group :" + groupName + " do")) {
                insertIdx = i;
                break;
            }
        }

        if (insertIdx == -1) {
            gemfileLines.add("group :" + groupName + " do");
            gemfileLines.add(toAdd);
            gemfileLines.add("end");
            return;
        }

        // TODO: fix inefficiency
        for (int i = insertIdx; i < gemfileLines.size(); i++) {
            if (toAdd.equals(gemfileLines.get(i)))
                return; // don't add it again
        }
        gemfileLines.add(insertIdx + 1, toAdd);
    }

    public void generateGems () throws Exception {

        /**
         * retrieve file
         */
        List<String> gemfileLines = FileUtils.getLinesCreateIfEmpty(app.getWebAppDir() + "/Gemfile");

        // Heroku stuff:
        addGemToGroup(gemfileLines, "production", "pg", "0.17.1", false);
        addGemToGroup(gemfileLines, "production", "rails_12factor", "0.0.2", false);
        //addGemToGroup(gemfileLines, "assets", "bootstrap-datepicker-rails", null, false);
        addGemToGroup(gemfileLines, "assets", "jquery-ui-rails", "4.2.1", false);

        /**
         * group :assets do
         gem 'jquery-ui-rails'
         end
         */
//         FileUtils.insertAfterOrAtEnd(gemfileLines, new String[] {"group :production do"},
//                new String[] {"gem 'pg',     '0.17.1'", "gem 'rails_12factor',      '0.0.2'"}, true, true);


//        boolean didIt = FileUtils.insertAfter(gemfileLines, new String[] {"group :assets do"},
//                new String[] {"  gem 'sass-rails', '~> 3.2.3'"}, true, true);

        // if using AWS for images:
        if (app.hasImages()) {
        	//addGem(gemfileLines, "paperclip");
            addGem(gemfileLines, "carrierwave");
            //addGem(gemfileLines, "mini-magick"); TODO can't find this gem
            addGem(gemfileLines, "fog");
        }

        addGem(gemfileLines, "actionmailer");
        addGem(gemfileLines, "coderay");
        addGem(gemfileLines, "RedCloth");
        addGem(gemfileLines, "tabs_on_rails");
        //addGem(gemfileLines, "twitter-bootstrap-rails");
        addGem(gemfileLines, "bootstrap-sass", "3.2.0.0", false);
        addGem(gemfileLines, "bcrypt", "3.1.7", false);
        addGem(gemfileLines, "will_paginate", "3.0.5", false);      //gem 'will_paginate', '~> 3.0.5'
        addGem(gemfileLines, "bootstrap-will_paginate", "0.0.6", true);
        addGem(gemfileLines, "faker", "1.0.1", true);

        FileUtils.putLines(gemfileLines, app.getWebAppDir() + "/Gemfile");

        String 	railsCmd = app.isWindows() ? "C:/RailsInstaller/Ruby2.1.0/bin/bundle.bat" : "bundle";

        runCommandInApp(railsCmd + " install --without production");
        
        //String result = runCommand(app.getRootDir(), "bundle", "install", "-without", "production");
    	//System.out.println(result);
    }

    private String  getRailsCommand () {
        return (app.isWindows() ? "C:/RailsInstaller/Ruby2.1.0/bin/rails.bat" : "rails");
    }

    public void generateAppStructure () throws Exception {
        /**
         * run: rails new 'app name'
         */
    	String result = runCommand(app.getRootDir(), getRailsCommand(), "new", app.getName());
    	
    	System.out.println(result);
    }


    public void generateDeploymentScript () throws Exception {
    	
    	StringBuilder builder = new StringBuilder();
    	StringUtils.addLine(builder, "#!/bin/sh");
        String bundle = app.isWindows() ? "bundle.bat" : "bundle";

    	StringUtils.addLine(builder, bundle + " install --without production");
    	StringUtils.addLine(builder, bundle + " assets precompile");
    	StringUtils.addLine(builder, bundle + " exec rake db:migrate");
    	//StringUtils.addLine(builder, "git add .");
    	//StringUtils.addLine(builder, "git commit -a -m \"the app code\"");
    	//StringUtils.addLine(builder, "git push heroku master");

    	FileUtils.write(builder, app.getWebAppDir() + "/setup.sh", true);

        builder = new StringBuilder();
        StringUtils.addLine(builder, "#!/bin/sh");
        StringUtils.addLine(builder, "#This will show ruby processes to kill to stop server:");
        StringUtils.addLine(builder, "tasklist |grep ruby | awk '{print $2}'");
        StringUtils.addLine(builder,  bundle + " exec rake db:drop");
        StringUtils.addLine(builder,  bundle + " exec rake db:migrate");
        StringUtils.addLine(builder, "#Start server: rails.bat s");

        FileUtils.write(builder, app.getWebAppDir() + "/db.sh", true);

        /**
         * bundle install --without production   done
         * bundle exec rake db:migrate               done
         * rake assets precompile       done
         * git add .
         * git commit -m "more changes"
         * git push heroku master
         */
    }
}
