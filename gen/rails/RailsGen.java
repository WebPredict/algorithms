package gen.rails;

import alg.io.FileUtils;
import alg.strings.StringUtils;
import alg.web.HTMLUtils;
import alg.words.WordUtils;
import gen.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
            generateModels();
            generateControllers();
            generateViews();

            if (app.getAppConfig().isNeedsAuth())
                generateLoginSignupPages();

            generateStaticPages();
            generateSharedPages();
            generateHelperMethods();
            generateAssets();

           if (app.isGenerateUpgrades())
                generateUpgrades();

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

        HTMLUtils.addParagraph(buf, "Thank you for registering <%= @" + userModelName + ".name %> with " + app.getTitle());
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
        StringBuilder buf = new StringBuilder();
        HTMLUtils.addRuby(buf, "if object.errors.any?");
        HTMLUtils.addDivId(buf, "error_explanation");
        HTMLUtils.addDiv(buf, "alert alert-error");
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
    	StringUtils.addLine(buf, "<div class=\"navbar navbar-inverse navbar-fixed-top\">");
    	StringUtils.addLine(buf, "<div class=\"navbar-inner\">");
    	StringUtils.addLine(buf, "<div class=\"container\">");
    	StringUtils.addLine(buf, "<a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">");
    	StringUtils.addLine(buf, "<span class=\"icon-bar\"></span>");
    	StringUtils.addLine(buf, "<span class=\"icon-bar\"></span>");
    	StringUtils.addLine(buf, "<span class=\"icon-bar\"></span>");
    	StringUtils.addLine(buf, "</a>");
    	StringUtils.addLine(buf, "<%= link_to raw(\"<span style='color: #9999ff'>" + app.getTitle() + "</span>\"), root_path, id: \"logo\" %>");
    	StringUtils.addLine(buf, "<div class=\"nav-collapse\">");
    	StringUtils.addLine(buf, "<ul class=\"nav pull-right\">");
    	StringUtils.addLine(buf, "<% if logged_in? %>");
    	StringUtils.addLine(buf, "<li><%= link_to \"Dashboard\", root_path %></li>");
    	StringUtils.addLine(buf, "<% end %>");
    	StringUtils.addLine(buf, "<% if !logged_in? %>");
    	
    	String searchModelName = app.getTopLevelSearchModelName();
    	StringUtils.addLine(buf, "<form action=\"/" + searchModelName + "\" class=\"navbar-search pull-right\">");
    	StringUtils.addLine(buf, "<input type=\"text\" class=\"search-query\" id=\"search\" name=\"search\" placeholder=\"Search\">");
    	StringUtils.addLine(buf, "</form>");

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
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);

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

        StringUtils.addLine(buf, "<div class=\"container\"> ");
        HTMLUtils.addRuby(buf, "flash.each do |key, value|");
        StringUtils.addLine(buf, "<div class=\"alert alert-<%= key %>\"><%= value %></div>  ");
        HTMLUtils.addRuby(buf, "end");
        HTMLUtils.addRubyOutput(buf, "yield");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.addRubyOutput(buf, "render 'layouts/footer'");
        StringUtils.addLine(buf, "</body>");
        StringUtils.addLine(buf, "</html>");

        /**
        <%= stylesheet_link_tag    "application", :media => "all" %>
        <%= javascript_include_tag "application" %>
        <%= csrf_meta_tags %>
        <%= render 'layouts/shim' %>
        </head>
        <body>
        <%= render 'layouts/header' %>

        <div class="container">
        <% flash.each do |key, value| %>
        <div class="alert alert-<%= key %>"><%= value %></div>
        <% end %>
        <%= yield %>
        </div>
        <%= render 'layouts/footer' %>
        </body>
        </html>

        */

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

            HTMLUtils.addParagraph(buf, page.getContent());

            FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/" + name + ".html.erb", true);
        }
    }

    public void generateNewsPage () throws Exception {
        StringBuilder buf = new StringBuilder();
        HTMLUtils.addRuby(buf, "provide(:title, 'News')");
        HTMLUtils.addH1(buf, "News about " + WordUtils.capitalize(app.getName()));

        ArrayList<Blurb> blurbs = app.getNewsBlurbs();

        for (int i = 0; i < blurbs.size(); i++) {
            HTMLUtils.addH2(buf, blurbs.get(i).getTitle());
            HTMLUtils.addParagraph(buf, blurbs.get(i).getContent());
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
            HTMLUtils.addH3(buf, userModel.getCapName() + " Dashboard for <%= current_" +userModel.getName() + ".name %>");

            boolean topLevelModelsInTabs = true; // TODO
            
            if (topLevelModelsInTabs) {
            	HTMLUtils.addRubyOutput(buf, "tabs_tag do |tab|");
            	for (Model model : app.getTopLevelModels()) {
            		HTMLUtils.addRubyOutput(buf, "tab." + model.getName() + "' " +
            				model.getCapName() + "', " + model.getName() + "_path");
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
        StringUtils.addLine(buf, "<div class=\"center hero-unit\">");
        HTMLUtils.addH1(buf, "Welcome to " + app.getTitle(), "color: #9999ff");

        HTMLUtils.addH2(buf, app.getTagLine(), "color: #9999ff");

        Model  frontSearchModel = app.getFrontPageSearchModel();

        if (frontSearchModel != null) {
            String plural =  frontSearchModel.getPluralName();
            HTMLUtils.addRubyOutput(buf, "link_to \"Search " + plural + "\", " + plural + "_path, class: \"btn btn-large btn-primary\"");
        }
        HTMLUtils.addRubyOutput(buf, "link_to \"Signup!\", signup_path, class: \"btn btn-large\"");
        HTMLUtils.closeDiv(buf);
        HTMLUtils.addLineBreak(buf);

         Model  frontPageModelList = app.getFrontPageListModel();

         if (frontPageModelList != null)
             generateTableFor(buf, frontPageModelList);

        HTMLUtils.addRuby(buf, "end");
        FileUtils.write(HTMLUtils.formatHTML(buf.toString(), 2), app.getWebAppDir() + "/app/views/static_pages/home.html.erb", true);
    }

    public void generateTableFor(StringBuilder buf, Model model) {
        String pluralModelList = model.getPluralName();

        HTMLUtils.addRuby(buf, "if @" + pluralModelList + ".any?");
        StringUtils.addLine(buf, "<table class=\"table table-striped\">");

        StringUtils.addLine(buf, "</table>");

        ArrayList<Field> fields = model.getFields();

        // TODO: this should probably get pushed in to the render for a table view of the model
        if (fields != null) {
            String line = "<tr>";

            for (Field f : fields) {
                line += "<th><%= sortable \"" + f.getName() + "\", \"" + WordUtils.capitalizeAndSpace(f.getName()) + "\" %></th>";
            }
            line += "<th>Actions</th></tr>";
            StringUtils.addLine(buf, line);
        }
        HTMLUtils.addRubyOutput(buf, "render @" + pluralModelList + "_rows");

        HTMLUtils.addRubyOutput(buf, "will_paginate @" + model.getPluralName());
        HTMLUtils.addRuby(buf, "end");
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

                if (fType.equals(Type.BOOLEAN))
                    line += "<td><%= yesno(" + modelName + "." + fieldName + ") %></td>";
                else if (fType.equals(Type.CURRENCY))
                    line += "<td><%= number_to_currency(" + modelName + "." + fieldName + ", :unit => \"$\") %></td>";
                else if (fType.equals(Type.IMAGE)) {
                    StringBuilder imgBuilder = new StringBuilder("<td>");
                    HTMLUtils.addRuby(imgBuilder, "if " + modelName + ".image_url.nil?"); // TODO remove hardcoding
                    HTMLUtils.addRubyOutput(imgBuilder, "image_tag(\"ImagePlaceholderSmall.png\")");
                    HTMLUtils.addRuby(imgBuilder, "else");
                    HTMLUtils.addRubyOutput(imgBuilder, "image_tag(listing.image_url, :size => \"50x50\", :style => \"width: 50px; height: 50px\")");
                    HTMLUtils.addRuby(imgBuilder, "end");
                    imgBuilder.append("</td>");
                }
                else if (fType.equals(Type.URL))
                    line += "<td><%= " + modelName + "." + fieldName + " %></td>";
                else if (fieldName.equals("created_at"))
                    line += "<td><%= time_ago_in_words(" + modelName + "." + fieldName + ") %></td>";
                else if (fieldName.equals("name"))
                    line += "<td><%= link_to " + modelName + "." + fieldName + ", " + modelName + " %></td>";
                else
                    line += "<td><%= " + modelName + "." + fieldName + " %></td>";
            }

            StringUtils.addLine(buf, line);

            // Actions:
            StringUtils.addLine(buf, "<td>");
            HTMLUtils.addRuby(buf, "if current_user?(" + modelName + ".user) || (current_user != nil && current_user.admin?)");
            HTMLUtils.addRubyOutput(buf, "link_to \"edit\", edit_" + modelName + "_path(" + modelName + "), :class => 'btn btn-mini'");
            HTMLUtils.addRuby(buf, "if " + modelName + ".disabled");
            HTMLUtils.addRubyOutput(buf, "link_to \"enable\", enable_" + modelName + "_path(" + modelName + "), :class => 'btn btn-mini'");
            HTMLUtils.addRuby(buf, "else");
            HTMLUtils.addRubyOutput(buf, "link_to \"disable\", disable_" + modelName + "_path(" + modelName + "), :class => 'btn btn-mini'");
            HTMLUtils.addRuby(buf, "end");
            HTMLUtils.addRubyOutput(buf, "link_to \"delete\", " + modelName + ", :class => 'btn btn-mini btn-danger', method: :delete, " +
             "confirm: \"You sure you want to delete this " + modelName + "?\"," +
             "title: " + modelName + ".title");
            HTMLUtils.addRuby(buf, "end");

            StringUtils.addLine(buf, "</td>");
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

        HTMLUtils.addRubyOutput(buf, "f.label :email");
        HTMLUtils.addRubyOutput(buf, "f.text_field :email");

        HTMLUtils.addRubyOutput(buf, "f.label :password");
        HTMLUtils.addRubyOutput(buf, "f.password_field :password");

        HTMLUtils.addRubyOutput(buf, "f.label :remember_me, class: \"checkbox inline\" do");
        HTMLUtils.addRubyOutput(buf, "f.check_box :remember_me");
        HTMLUtils.addSpan(buf, "Remember me on this computer");
        HTMLUtils.addRuby(buf, "end");

        generateFormEnd(buf, "Sign in");
        HTMLUtils.addParagraph(buf, "New " + userModelName + "? <%= link_to \"Sign up now!\", signup_path %>");
        HTMLUtils.addParagraph(buf, "Forgot password? <%= link_to \"Reset password\",  send_password_path %>");

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/sessions/new.html.erb", true);
    }

    private void generateFormForStart (StringBuilder buf, String modelName) {
        HTMLUtils.addRow(buf, "span6", "offset3");
        HTMLUtils.addRubyOutput(buf, "form_for(:" + modelName + ", url: " + WordUtils.pluralize(modelName) + "_path) do |f|");
    }

    private void generateFormEnd (StringBuilder buf, String buttonText) {
        HTMLUtils.addRubyOutput(buf, "f.submit \"" + buttonText + "\", class: \"btn btn-large btn-primary\" ");
        HTMLUtils.addRuby(buf, "end");
        HTMLUtils.closeRow(buf);
    }

    public void generateContactPage () throws Exception {

        StringBuilder buf = new StringBuilder();
        HTMLUtils.addRuby(buf, "provide(:title, 'Contact')");
        HTMLUtils.addH1(buf, "Contact Us");

        HTMLUtils.addRow(buf, "span6", "offset3");
        HTMLUtils.addForm(buf, "submitcontact");

        StringUtils.addLine(buf, "<%= hidden_field_tag :authenticity_token, form_authenticity_token %>");

        HTMLUtils.addFormElement(buf, "email", "string", "Your Email");
        HTMLUtils.addFormElement(buf, "name", "string", "Your Name");
        HTMLUtils.addFormElement(buf, "comment", "long_string", "Your Message");

        HTMLUtils.addSubmitButton(buf, "commit", "Send Message");
        StringUtils.addLine(buf, "</form>");
        HTMLUtils.closeRow(buf);

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/contact.html.erb", true);
    }

    public void generateHelperMethods () throws Exception {
        if (app.getAppConfig().isNeedsAuth())
            generateSessionsHelper();
        generateApplicationHelper();
    }

    public void generateSessionsHelper () throws Exception {

        Model userModel = app.getUserModel();
        String  name = userModel.getName();
        String  capName = userModel.getCapName();

        StringBuilder buf = new StringBuilder();
        StringUtils.addLine(buf, "module SessionsHelper");
        StringUtils.addLineBreak(buf);


        /**
         * def forget
         update_attribute(:remember_digest, nil)

         def forget(user)
         user.forget
         cookies.delete(:user_id)
         cookies.delete(:remember_token)
         end

         # Logs out the current user.
         def log_out
         forget(current_user)
         session.delete(:user_id)
         @current_user = nil
         end

         def logged_in?
         !current_user.nil?
         end

         def remember(user)
         user.remember
         cookies.permanent.signed[:user_id] = user.id
         cookies.permanent[:remember_token] = user.remember_token
         end
         */

        addMethod(buf, "logged_in?", new String[] {"!current_" + name + ".nil?"});
        addMethod(buf, "log_in(" + name + ")", new String[] {"session[:" + name + "_id] = " + name + ".id"});
        addMethod(buf, "remember(" + name + ")", new String[] {name + ".remember", "cookies.permanent.signed[:" + name + "_id] = " + name + ".id",
                "cookies.permanent[:remember_token] = " + name + ".remember_token"});
        addMethod(buf, "forget(" + name + ")", new String[] {name + ".forget", "cookies.delete(:" + name + "_id)", "cookies.delete(:remember_token)"});
        addMethod(buf, "log_out", new String[] {"forget(current_" + name + ")", "session.delete(:" + name + "_id)", "@current_" + name + " = nil"});

        addMethod(buf, "current_" + name, new String[] {"@current_" + name + " ||= " + capName + ".find_by(id: session[:" + name + "_id])"});


//        addMethod(buf, "signed_in?", new String[] {"!current_" + name + ".nil?"});
//        addMethod(buf, "sign_out", new String[] {"self.current_" + name + " = nil", "cookies.delete(:remember_token)"});
//        addMethod(buf, "current_" + name, new String[] {"@current_" + name + " ||= " + capName + ".find_by_remember_token(cookies[:remember_token])"});
//        addMethod(buf, "current_" + name + "?(" + name + ")", new String[] {name + " == current_" + name});
//        addMethod(buf, "current_" + name + "=(" + name + ")", new String[] {"@current_" + name + " = " + name});
//        addMethod(buf, "redirect_back_or(default)", new String[] {"redirect_to(session[:return_to] || default)", "session.delete(:return_to)"});
//        addMethod(buf, "sign_in(" + name + ")", new String[] {"cookies.permanent[:remember_token] = " + name + ".remember_token", "self.current_" + name + " = " + name});
//        addMethod(buf, "redirect_back_or(default)", new String[] {"redirect_to(session[:return_to] || default)", "session.delete(:return_to)"});
//        addMethod(buf, "store_location", new String[] {"session[:return_to] = request.fullpath"});
//        addMethod(buf, "signed_in_" + name, new String[] {"unless signed_in?", "\tstore_location", "\tredirect_to signin_path, notice: \"Please sign in.\"", "end"});

        StringUtils.addLine(buf, "end ");

        FileUtils.write(buf, app.getWebAppDir() + "/app/helpers/sessions_helper.rb", true);
    }

    public void generateRoutes () throws Exception {
        //StringBuilder buf = new StringBuilder();

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

        //StringUtils.addLineBreak(buf);

        insertTabbedIfNotThere(routeLines, "match '/submitcontact', to: 'static_pages#submitcontact', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/signup', to: '" + pluralName + "#new', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/send_password', to: '" + pluralName + "#send_password', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/submit_send_password', to: '" + pluralName + "#submit_send_password', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/signin', to: 'sessions#new', via: [:get, :post]", index++);
        insertTabbedIfNotThere(routeLines, "match '/signout', to: 'sessions#destroy', via: :delete", index++);

        // TODO: don't reinsert this if it's already done to routes.rb:
        //FileUtils.insertAtInFile(app.getWebAppDir() + "/config/routes.rb", 2, new String[] {buf.toString()}, true);

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

    public void generateApplicationHelper () throws Exception {
        StringBuilder buf = new StringBuilder();

        StringUtils.addLine(buf, "module ApplicationHelper ");
        StringUtils.addLineBreak(buf);
        tabbed(buf, "def sortable(column, title = nil)");
        tabbed(buf, "title ||= column.titleize ", 2);
        tabbed(buf, "css_class = column == sort_column ? \"current #{sort_direction}\" : nil", 2);
        tabbed(buf, "direction = column == sort_column && sort_direction == \"asc\" ? \"desc\" : \"asc\" ", 2);
        tabbed(buf, "link_to title, {:sort => column, :direction => direction}, {:class => css_class}", 2);
        tabbed(buf, "end");
        StringUtils.addLineBreak(buf);
        tabbed(buf, "def full_title(page_title)");
        tabbed(buf, "base_title = \"" + WordUtils.capitalize(app.getName()) + "\" ", 2);
        tabbed(buf, "if page_title.empty? ", 2);
        tabbed(buf, "base_title  ", 3);
        tabbed(buf, "else  ", 2);
        tabbed(buf, "\"#{base_title} | #{page_title}\"  ", 3);
        tabbed(buf, "end   ", 2);
        StringUtils.addLineBreak(buf);
        tabbed(buf, "end  ");

        StringUtils.addLineBreak(buf);
        tabbed(buf, "def yesno(value)");
        tabbed(buf, "if value", 2);
        tabbed(buf, "\"Yes\"", 3);
        tabbed(buf, "else", 2);
        tabbed(buf, "\"No\"", 3);
        tabbed(buf, "end", 2);
        StringUtils.addLine(buf, "end");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def image_for_options(imageholder, options)");
        tabbed(buf, "if imageholder.nil? or imageholder.image_url.nil?", 2);
        tabbed(buf, "image_tag(\"ImagePlaceholderSmall.png\", options)", 3);
        tabbed(buf, "else", 2);
        tabbed(buf, "image_tag(imageholder.image_url, options)", 3);
        tabbed(buf, "end", 2);
        tabbed(buf, "end");

        StringUtils.addLineBreak(buf);
        tabbed(buf, "def image_for(imageholder)");
        tabbed(buf, "if imageholder.nil? or imageholder.image_url.nil?", 2);
        tabbed(buf, "image_tag(\"ImagePlaceholderSmall.png\")", 3);
        tabbed(buf, "else", 2);
        tabbed(buf, "image_tag(imageholder.image_url)", 3);
        tabbed(buf, "end", 2);
        tabbed(buf, "end");

        ArrayList<String> methodLines = new ArrayList<String>();

        methodLines.add("ret = ''");
        methodLines.add("if website != nil");
        methodLines.add("\tif website[0..7] == \"http://\" ");
        methodLines.add("\t\tret = website");
        methodLines.add("\telse");
        methodLines.add("\t\tret = \"http://\" + website ");
        methodLines.add("\tend");
        methodLines.add("end");

        addMethod(buf, "render_website(website)", methodLines);

        methodLines.clear();
        methodLines.add("addr = ''");
        methodLines.addAll(generateIf("address != nil", "addr = address"));

        methodLines.addAll(generateIf("city != nil", "addr != '' ? addr += ' ' + city : city"));
        methodLines.addAll(generateIf("state != nil", "addr != '' ? addr += ', ' + state : state"));
        methodLines.addAll(generateIf("postal != nil", "addr != '' ? addr += ', ' + postal : postal"));

        addMethod(buf, "render_address(address)", methodLines);

        StringUtils.addLine(buf, "end");

        FileUtils.write(buf, app.getWebAppDir() + "/app/helpers/application_helper.rb", true);
    }

    public void generateAssets () throws Exception {

        FileUtils.insertAfterInFileIfNotExists(app.getWebAppDir() + "/app/assets/javascripts/application.js", "//= require jquery_ujs",
                "//= require bootstrap", true, true);
        FileUtils.insertAfterInFileIfNotExists(app.getWebAppDir() + "/app/assets/javascripts/application.js", "//= require jquery_ujs",
                "//= require jquery.ui.datepicker", true, true);

        FileUtils.insertAfterInFileIfNotExists(app.getWebAppDir() + "/app/assets/stylesheets/application.css", " *= require_self",
               " *= require jquery.ui.datepicker", true, true);

//        if (FileUtils.fileExists(app.getWebAppDir() + "/app/assets/stylesheets/bootstrap_and_overrides.css.less")) {
//            FileUtils.insertAfterInFile(app.getWebAppDir() + "/app/assets/stylesheets/bootstrap_and_overrides.css.less", new String[] {"twitter/bootstrap/bootstrap"},
//                    new String[] {"body {\n" +
//                            "    padding-top: 60px;\n" +
//                            "}"}, true, true);
//
//            FileUtils.insertAfterInFile(app.getWebAppDir() + "/app/assets/stylesheets/bootstrap_and_overrides.css.less", new String[] {"twitter/bootstrap/bootstrap"},
//                    new String[] {"body {\n" +
//                            "    padding-top: 60px;\n" +
//                            "}"}, true, true);

        if (app.getJumbotronImageUrl() != null)
            addStyle(new String[] {".hero-unit {", "\tbackground-image: url('" + app.getJumbotronImageUrl() + "');", "}"});

        FileUtils.copyTextFile("C:/Users/jsanchez/Downloads/apps/resources/custom.css.scss", app.getWebAppDir() + "/app/assets/stylesheets/custom.css.scss");

        // TODO: not sure we need to do this if running with --without production:
        //String 	railsCmd = app.isWindows() ? "C:/RailsInstaller/Ruby2.1.0/bin/rake.bat" : "rake";

        //runCommandInApp(railsCmd + " assets precompile");
    }

    public void addStyle (String [] styleInfo) throws Exception {
        FileUtils.append(app.getWebAppDir() + "/app/assets/stylesheets/bootstrap_and_overrides.css.less", styleInfo);
    }

    public void generateModels () throws Exception {
        ArrayList<Model>  models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                String name = model.getName();
                String              capName = model.getCapName();
                ArrayList<Field>    fields = model.getFields();
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuilder buf = new StringBuilder();
                StringUtils.addLine(buf, "class " + capName + " < ActiveRecord::Base");
                String attrs = "attr_accessible ";

                ArrayList<Field> computedFields = new ArrayList<Field>();

                if (fields != null) {
                    for (int i = 0; i < fields.size(); i++) {
                        Field f = fields.get(i);
                        attrs += ":" + f.getName();

                        if (f.isComputed())
                            computedFields.add(f);

                        if (i < fields.size() - 1) {
                            attrs += ", ";
                        }
                    }
                }

                if (rels != null) {
                    // Need to do the _ids
                    for (Rel rel : rels) {
                        if (rel.getRelType().equals(RelType.MANY_TO_ONE)) {
                            attrs += ", :" + rel.getModel().getName() + "_id";
                        }
                        else if (rel.getRelType().equals(RelType.ONE_TO_MANY)) {
                            attrs += ", :" + rel.getModel().getName() + "_ids";
                        }
                    }
                }


                // Seems like we don't need this anymore:
                //tabbed(buf, attrs);

                if (model.isSecure()) {
                    tabbed(buf, "attr_accessor :remember_token");
                    tabbed(buf, "has_secure_password");
                }

                if (rels != null) {
                    for (int i = 0; i < rels.size(); i++) {
                        Rel rel = rels.get(i);

                        RelType relType = rel.getRelType();
                        Model   relModel = rel.getModel();
                        if (relType.equals(RelType.ONE_TO_MANY)) {
                            String  toAdd =  "has_many :" + relModel.getPluralName();
                            if (rel.isDependent())
                                toAdd += ", dependent: :destroy";

                            StringUtils.addTabbedLine(buf, toAdd);
                        }
                        else if (relType.equals(RelType.MANY_TO_ONE)) {
                            tabbed(buf, "belongs_to :" + relModel.getPluralName());
                        }
                        else if (relType.equals(RelType.ONE_TO_ONE)) {
                            tabbed(buf, "has_one :" + relModel.getName());
                        }
                        else if (relType.equals(RelType.MANY_TO_MANY)) {
                            String  toAdd =  "has_many :" + relModel.getPluralName();
                            if (rel.getThrough() != null) {
                                toAdd += ", through: " + rel.getThrough().getPluralName();
                            }
                            tabbed(buf, toAdd);
                        }

                    }
                }
                if (model.hasImages()) {
                    List<Field> imageFields = model.getImageFields();

                    for (Field imageField : imageFields) {
                        // TODO: this needs to change for a model with a set of images as opposed to just one:
                        tabbed(buf, "mount_uploader :" + imageField.getName() + ", " + capName + "imageUploader");
                    }
                }

                StringUtils.addLineBreak(buf);

                if (model.hasEmail ()) {
                    tabbed(buf, "before_save { |" + name + "| " + name + ".email = email.downcase }");
                }
                if (model.isSecure()) {
                    tabbed(buf, "before_save :create_remember_token");
                    StringUtils.addLineBreak(buf);
                }

                if (fields != null) {
                    if (model.hasEmail()) {
                        tabbed(buf, "VALID_EMAIL_REGEX = /\\A[\\w+\\-.]+@[a-z\\d\\-.]+\\.[a-z]+\\z/i");
                    }
                    if (model.hasURL()) {
                        tabbed(buf, "VALID_URL_REGEX = /\\A[\\w+\\-.]+[a-z\\d\\-.]+\\.[a-z]+\\z/i");
                    }
                    for (int i = 0; i < fields.size(); i++) {
                        Field f = fields.get(i);
                        List<Validation> validations = f.getValidations();
                        if (validations != null) {
                            for (Validation validation : validations) {
                                ValidationType validationType = validation.getValidationType();
                                switch (validationType) {
                                    case NOT_NULL:
                                        tabbed(buf, "validates :" + f.getName() + ", presence: true");
                                        break;
                                    case MIN_LENGTH:
                                        tabbed(buf, "validates :" + f.getName() + ", length: { minimum: " + validation.getDetailsAsInt().toString() + " }");
                                        break;
                                    case MAX_LENGTH:
                                        tabbed(buf, "validates :" + f.getName() + ", length: { maximum: " + validation.getDetailsAsInt().toString() + " }");
                                        break;
                                    case EMAIL:
                                        tabbed(buf, "validates :" + f.getName() + ", format: { with: VALID_EMAIL_REGEX }");
                                        break;
                                    case URL:
                                        tabbed(buf, "validates :" + f.getName() + ", format: { with: VALID_URL_REGEX }");
                                        break;
                                    case UNIQUE:
                                        tabbed(buf, "validates :" + f.getName() + ", uniqueness: { case_sensitive: false }");
                                        break;
                                }
                            }
                        }
                    }

                    if (rels != null) {
                        // Need to do the _ids
                        for (Rel rel : rels) {
                            if (rel.getRelType().equals(RelType.MANY_TO_ONE)) {
                                tabbed(buf, "validates :" + rel.getModel().getName() + "_id, presence: true");
                            }
                        }
                    }

                    if (model.isSecure()) {
                        tabbed(buf, "validates :password, presence: true, :if => :should_validate_password?, length: { minimum: 6 }");
                        tabbed(buf, "validates :password_confirmation, presence: true, :if => :should_validate_password?");
                        StringUtils.addLineBreak(buf);

                        tabbed(buf, "def should_validate_password?");
                        tabbed(buf, "new_record?", 2);
                        tabbed(buf, "end");
                        StringUtils.addLineBreak(buf);
                    }
                }

                StringUtils.addLineBreak(buf);

                for (Field f : computedFields) {
                    tabbed(buf, "def compute_" + f.getName());
                    tabbed(buf, "# TODO: fill in custom logic to compute this field!!!", 2);
                    tabbed(buf, "end");
                    StringUtils.addLineBreak(buf);
                }


                if (model.isSecure()) {
                    addMethod(buf, model.getCapName() + ".digest(string)", new String[] {"cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST :",
                            "                 BCrypt::Engine.cost",
                            "                 BCrypt::Password.create(string, cost: cost)"});

                    addMethod(buf, model.getCapName() + ".new_token", new String[] {"SecureRandom.urlsafe_base64"});
                    addMethod(buf, "remember", new String[] {"self.remember_token = " + model.getCapName() + ".new_token", "update_attribute(:remember_digest, User.digest(remember_token))"});
                    addMethod(buf, "authenticated?(remember_token)", new String[] {"return false if remember_digest.nil?",
                            "BCrypt::Password.new(remember_digest).is_password?(remember_token)"});
                    addMethod(buf, "forget", new String[] {"update_attribute(:remember_digest, nil)"});
                }


//                tabbed(buf, "private");
//                if (model.isSecure()) {
//                    tabbed(buf, "def create_remember_token", 2);
//                    tabbed(buf, "self.remember_token = SecureRandom.urlsafe_base64", 3);
//                    tabbed(buf, "end", 2);
//                }
                StringUtils.addLine(buf, "end");

                FileUtils.write(buf, app.getWebAppDir() + "/app/models/" + capName + ".rb", true);
            }
        }
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
                generateEditView(model);
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
        ArrayList<Rel>      rels = model.getRelationships();

        StringBuilder       buf = new StringBuilder();
        
        // TODO: need to not hardcode this
        StringUtils.addLine(buf, "<% provide(:title, @" + name + ".name) %>");
        StringBuilder       bodyContent = new StringBuilder();

        boolean useTabs = useTabs(model);

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
                // TODO: different layout for all these different types
                // TODO: sets, lists, range generation
                // TODO: collections as tables generation (partial renderer calls?)

                String nameFName = name + "." + fName;
                String capName = WordUtils.capitalize(fName);
                if (fTypeName.equals(Type.SET_ONE_OR_MORE) || fTypeName.equals(Type.LIST)) {
                    if (fType.getSubtype().isPrimitive()) {
                        // TODO: display collection of strings, dates, ints, etc.
                    }
                    else {
                        StringUtils.addLine(bodyContent, "<% if @" + name + "." + capName + ".any? %>");

                        tabbed(bodyContent, "<%= render @" + capName + " %>");
                        tabbed(bodyContent, "<% else %>");
                        tabbed(bodyContent, "<p> No " + capName + " for this " + name + " yet.</p>");
                        tabbed(bodyContent, "<% end %>");
                        tabbed(bodyContent, "<% if logged_in? && current_" + name + "?(@" + name + ") %>");
                        tabbed(bodyContent, "<%= link_to \"Add " + fName + "\", new_" + fName + "_path, class: \"btn btn-large btn-primary\" %>");
                        tabbed(bodyContent, "<% end %>");
                    }

                }
                else if (fName.equals("name")) {
                    HTMLUtils.addH3(bodyContent, "<%= @" + nameFName + " %>");
                }
                else if (fTypeName.equals(Type.IMAGE.getName())) {
                    generateReadOnlySection(bodyContent, "image_for(@" + nameFName + ")", fName);
                }
                else if (fTypeName.equals(Type.DATETIME.getName())) {
                    generateReadOnlySection(bodyContent, "@" + nameFName, fName);
                }
                else if (fTypeName.equals(Type.CODE.getName())) {
                	generateReadOnlySection(bodyContent, "CodeRay.scan(@" + nameFName + ", :ruby).div(:line_numbers => :table)", fName);               	
                }
                else if (fTypeName.equals(Type.CURRENCY.getName())) {
                    generateReadOnlySection(bodyContent, "number_to_currency(@" + nameFName + ", :unit => \"$\")", fName);
                }
                else if (fTypeName.equals(Type.ADDRESS.getName())) {

                    generateReadOnlySection(bodyContent, "render_address(@" + nameFName + ")", fName);
                }
                else if (fTypeName.equals(Type.URL.getName())) {
                    String content = " <% if @" + nameFName + "!= nil %>\n" +
                            "        <a href=\"<%= render_website(@" + nameFName + ") %>\" target=\"_blank\"><%= @" + nameFName +" %></a>\n" +
                            "        <% end %>\n";
                    generateReadOnlySection(bodyContent, content, fName);
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

        Layout layout = app.getAppConfig().getLayout();

        if (layout == null)
            layout = Layout.ONE_COL;

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
        StringUtils.addLine(bodyContent, "<section><h4>" + WordUtils.capitalizeAndSpace(fieldName) + "</h4>");
        HTMLUtils.addRubyOutput(bodyContent, fieldDetails);
        StringUtils.addLine(bodyContent, "</section>");
    }

    public void generateListView (Model model) throws Exception {

        String name = model.getName();
        String names = model.getPluralName();
        ArrayList<Field>    fields = model.getFields();
        ArrayList<Rel>      rels = model.getRelationships();

        StringBuilder buf = new StringBuilder();

        ModelLayout modelLayout = app.getAppConfig().getComplexModelLayout();

        StringBuilder bodyContent = new StringBuilder();
        generateTableFor(bodyContent, model);

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
        writeViewFile(buf, names, "_" + name + "_rows");
    }

    private void writeViewFile(StringBuilder buf, String subdir, String fileName) throws Exception {

        FileUtils.write(HTMLUtils.formatHTML(buf.toString(), 2), app.getWebAppDir() + "/app/views/" + subdir + "/" + fileName + ".html.erb", true);
    }

    public void generateEditView (Model model) throws Exception {

        String name = model.getName();
        String names = model.getPluralName();
        ArrayList<Field>    fields = model.getFields();
        ArrayList<Rel>      rels = model.getRelationships();

        StringBuilder   buf = new StringBuilder();
        boolean         useTabs = useTabs(model);
        StringBuilder   bodyContent = new StringBuilder();

        if (fields != null) {

             HTMLUtils.addRubyOutput(bodyContent, "form_for @" + name + ", :html => {:multipart => true} do |f|");
             HTMLUtils.addRubyOutput(bodyContent, "render 'shared/error_messages', object: f.object");

             for (Field f : fields) {
                 if (f.isReadOnly())
                     continue;

                 String fName = f.getName();

                 Type fType = f.getTheType();
                 String capitalized = WordUtils.capitalizeAndSpace(fName);

                 if (fType.equals(Type.BOOLEAN)) {
                    StringUtils.addLine(bodyContent, "<label class=\"checkbox line\">");
                    StringUtils.addLine(bodyContent, "<%= f.check_box :" + fName + "%>" + capitalized + "?");
                    StringUtils.addLine(bodyContent, "</label></br>"); // TODO change so we don't have to use line breaks
                 }
                 else if (fType.equals(Type.SHORT_STRING)) {
                     HTMLUtils.addRubyOutput(bodyContent, "f.label :" + fName);
                     HTMLUtils.addRubyOutput(bodyContent, "f.text_field :" + fName);
                 }
                 else if (fType.equals(Type.LONG_STRING)) {
                     HTMLUtils.addRubyOutput(bodyContent, "f.label :" + fName);
                     HTMLUtils.addRubyOutput(bodyContent, "f.text_area :" + fName + ", :rows => \"5\"");
                 }
                 else if (fType.equals(Type.STRING)) {
                     HTMLUtils.addRubyOutput(bodyContent, "f.label :" + fName);
                     HTMLUtils.addRubyOutput(bodyContent, "f.text_field :" + fName);
                 }
                 else if (fType.equals(Type.IMAGE)) {
                     rubyout(bodyContent, "f.label :" + fName + ", \"Your " + WordUtils.capitalize(fName) + " Image\"");
                     rubyout(bodyContent, "image_for(@" + fName + ")");
                     HTMLUtils.addDiv(bodyContent, "fileUpload btn");
                     aline(bodyContent, "<span>Change Image</span>");
                     rubyout(bodyContent, "f.file_field :" + fName + " :class => \"upload\"");
                     HTMLUtils.closeDiv(bodyContent);
                 }
                 else if (fType.equals(Type.PHONE)) {
                     HTMLUtils.addRubyOutput(bodyContent, "f.label :" + fName);
                     HTMLUtils.addRubyOutput(bodyContent, "f.phone_field :" + fName);
                 }
                 else if (fType.equals(Type.CURRENCY)) {
                     HTMLUtils.addRubyOutput(bodyContent, "f.label :" + fName);
                     HTMLUtils.addRubyOutput(bodyContent, "f.text_field :" + fName); // TODO: currency symbol?
                 }
                 else if (fType instanceof FixedList) {

                     FixedList fl = (FixedList)fType;
                     Object []  values = fl.getValues();

                     HTMLUtils.addRubyOutput(bodyContent, "f.label :" + fName);

                     StringUtils.addLine(bodyContent, "<select name=\"" + name + "[" + fName + "]\" id=\"" + name + "_" + fName + "\" class=\"input-medium\">");
                     StringUtils.addLine(bodyContent, "<%= options_for_select ([");

                     if (values != null) {
                         for (int i = 0; i < values.length; i++) {
                             StringUtils.addLine(bodyContent, "\"" + values [i].toString() + "\"" + (i < values.length - 1 ? ", " : ""));
                         }
                     }
                     StringUtils.addLine(bodyContent, "], selected: @" + name + "." + fName + ") %>");
                     HTMLUtils.close(bodyContent, "select");
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
                         aline(bodyContent, "<fieldset>");
                         aline(bodyContent, "<legend>" + WordUtils.capitalizeAndSpace(subTypeName) + "</legend>");
                         rubyout(bodyContent, "<%= hidden_field_tag \"" + name + "[" + subTypeName + "_ids][]\", nil %>");
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
                 else if (fType.equals(Type.FILE)) {
                     rubyout(bodyContent, "f.label :" + fName + ", \"Your " + WordUtils.capitalize(fName) + " File\"");
                     rubyout(bodyContent, "file_for(@" + fName + ")");         // TODO support for file_for
                     HTMLUtils.addDiv(bodyContent, "fileUpload btn");
                     aline(bodyContent, "<span>Change File</span>");
                     rubyout(bodyContent, "f.file_field :" + fName + " :class => \"upload\"");
                     HTMLUtils.closeDiv(bodyContent);
                 }

                 else if (fType.equals(Type.SET_PICK_ONE)) {
                     /**
                      * <div class="form-group">
                      <div class="controls-row"
                      <label class="radio inline">Weaned <%= f.radio_button :weened, true %> Unweaned <%= f.radio_button :weened, false %></label>
                      </div>

                      </div>

                      */
                 }
                 else if (fType.equals(Type.PASSWORD)) {
                     HTMLUtils.addRubyOutput(bodyContent, "f.label :" + fName);
                     HTMLUtils.addRubyOutput(bodyContent, "f.password_field :" + fName);
                 }
                 else {
                    HTMLUtils.addRubyOutput(bodyContent, "f.label :" + fName);
                    HTMLUtils.addRubyOutput(bodyContent, "f.text_field :" + fName);
                 }

                 aline(bodyContent, "");
             }
            ruby(bodyContent, "end");
        }


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

        // TODO: how should new differ from edit here?
        writeViewFile(buf, names, "new");

        writeViewFile(buf, names, "edit");
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

    protected List<String> generateIf (String condition, String content, String elseCond, String elseContent) {
        ArrayList<String> ret = new ArrayList<String>();

        ret.add("if " + condition);
        ret.add("\t" + content);
        if (elseCond != null) {
            ret.add("else if " + elseCond);
            ret.add("\t" + elseContent);
        }
        ret.add("end");

        return (ret);
    }

    protected List<String> generateIf (String condition, String content) {
         return (generateIf(condition, content, null, null));
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

    public void generateControllers () throws Exception {
        ArrayList<Model>  models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                String              name = model.getName();
                String              capName = WordUtils.capitalize(name);
                String              names = model.getPluralName();
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuilder buf = new StringBuilder();
                String  className = WordUtils.capitalizeAndJoin(names, "controller");
                StringUtils.addLine(buf, "class " + className + " < ApplicationController");
                tabbed(buf, "before_filter :signed_in_" + name + ", only: [:edit, :update, :destroy]");
                tabbed(buf, "before_filter :correct_" + name + ", only: [:edit, :update]");

                ArrayList<String> createLines = new ArrayList<String>();
                createLines.add("@" + name + " = " + capName + ".new(params[:" + name + "])");
                createLines.add("if @" + name + ".save");
                createLines.add("\tredirect_to root_path");
                createLines.add("else");
                createLines.add("\trender 'new'");
                createLines.add("end");

                addMethod(buf, "create", createLines);
                addMethod(buf, "new", new String[] {"@" + name + " = " + capName + ".new"});

                ArrayList<String> showMethod = new ArrayList<String>();
                showMethod.add("@" + name + " = " + capName + ".find(params[:id])");

                if (rels != null && rels.size() > 0) {
                    for (Rel rel : rels) {
                        // TODO: we need additional information to know if this relationship is needed for this particular action
                        Model   relModel = rel.getModel();
                        RelType relType = rel.getRelType();
                        String  relModelName = relModel.getName();

                        switch (relType) {
                            case ONE_TO_MANY:
                                showMethod.add("@" + relModel.getPluralName() + " = @" + name + "." + relModel.getPluralName() + ".paginate(page: params[:page])");
                                break;

                            case ONE_TO_ONE:
                                showMethod.add("@" + relModel.getName() + " = @" + name + "." +relModelName);  // TODO: needed?
                                break;

                            case MANY_TO_MANY:
                                showMethod.add("@" + relModel.getPluralName() + " = @" + name +
                                        "." + relModel.getPluralName() + ".paginate(page: params[:page])");  // TODO: verify this
                                break;
                        }
                    }
                }
                addMethod(buf, "show", showMethod);

                ArrayList<String> indexMethod = new ArrayList<String>();
                indexMethod.add("query = \"(disabled = 'f' or disabled is null)\"");
                indexMethod.add("condarr = [query]");
                indexMethod.add("@" + names + " = " + capName + ".paginate(:page => params[:page], :conditions => condarr, :order => sort_column + \" \" + sort_direction)");
                
                addMethod(buf, "index", indexMethod);

                addMethod(buf, "destroy", new String[] {capName + ".find(params[:id]).destroy",
                        "flash[:success] = \"" + capName + " removed from system.\"",
                        "redirect_to " + names + "_path"});

                StringUtils.addLineBreak(buf);
                tabbed(buf, "private");
                tabbed(buf, "def correct_" + name, 2);
                tabbed(buf, "@" + name + " = " + capName + ".find(params[:id])", 3);
                tabbed(buf, "end", 2);

                addMethodTabbed(buf, "sort_column", new String[]{
                        capName + ".column_names.include?(params[:sort]) ? params[:sort] : \"title\" "});

                addMethodTabbed(buf, "sort_direction", new String[]{"%w[asc desc].include?(params[:direction]) ? params[:direction] : \"asc\" "});

                StringUtils.addLine(buf, "end");

                FileUtils.write(buf, app.getWebAppDir() + "/app/controllers/" + names + "_controller.rb", true);
            }

            if (app.getAppConfig().isNeedsAuth()) {

                StringBuilder buf = new StringBuilder();
                StringUtils.addLine(buf, "class SessionsController < ApplicationController");

                addMethod(buf, "new", new String[] {});
                StringUtils.addLineBreak(buf);

//                tabbed(buf, "def create");
//                tabbed(buf, "email = params[:session][:email]", 2);
//                tabbed(buf, "if email != nil", 2);
//                tabbed(buf, "email = email.downcase ", 3);
//                tabbed(buf, "end", 2);
//                StringUtils.addLineBreak(buf);

                Model   userModel = app.getUserModel();
                String  userModelName = userModel.getName();

                tabbed(buf, "def create ");
                 tabbed(buf, userModelName + " = " + userModel.getCapName() + ".find_by(email: params[:session][:email].downcase)", 2);
                 tabbed(buf, "if " + userModelName + " && " + userModelName + ".authenticate(params[:session][:password])", 2);
                 tabbed(buf, "log_in " + userModelName, 2);
                tabbed(buf, "params[:session][:remember_me] == '1' ? remember(" + userModelName + ") : forget(" + userModelName + ")", 2);
                 tabbed(buf, "redirect_to " + userModelName, 2);
                 tabbed(buf, "else", 2);
                 tabbed(buf, "flash.now[:danger] = 'Invalid email/password combination'", 3);
                 tabbed(buf, "render 'new'", 3);
                 tabbed(buf, "end ", 2);
                 tabbed(buf, "end ");


//                tabbed(buf, userModelName + " = " + WordUtils.capitalize(userModelName) + ").find_by_email(email)", 2);
//                tabbed(buf, "if " + userModelName + " && " + userModelName + ".authenticate(params[:session][:password])", 2);
//                tabbed(buf, "sign_in user", 3);
//                tabbed(buf, "redirect_back_or root_path ", 3);
//                tabbed(buf, "else", 2);
//                tabbed(buf, "flash[:error] = \"Invalid email/password combination\" ", 3);
//                tabbed(buf, "render 'new'", 3);
//                tabbed(buf, "end", 2);
//                tabbed(buf, "end");

                addMethod(buf, "destroy", new String[] {"log_out if logged_in?", "redirect_to root_path"});

                StringUtils.addLine(buf, "end");

                FileUtils.write(buf, app.getWebAppDir() + "/app/controllers/sessions_controller.rb", true);
                
                buf = new StringBuilder();
               
                StringUtils.addLine(buf, "class ApplicationController < ActionController::Base");
                tabbed(buf, "protect_from_forgery");
                tabbed(buf, "include SessionsHelper");
                StringUtils.addLine(buf, "end");

                FileUtils.write(buf, app.getWebAppDir() + "/app/controllers/application_controller.rb", true);

            }
        }
        generateStaticPagesController();
    }

    public void generateStaticPagesController () throws Exception {

        StringBuilder buf = new StringBuilder();
        StringUtils.addLine(buf, "class StaticPagesController < ApplicationController");
        tabbed(buf, "protect_from_forgery");
        tabbed(buf, "include SessionsHelper");
        tabbed(buf, "helper_method :sort_column, :sort_direction");

        StringUtils.addLineBreak(buf);

        String [] frontContent = new String[] {""};

        Model frontListModel = app.getFrontPageListModel();
        if (frontListModel != null) {
            frontContent = new String []
                    {"query = \"(disabled = 'f' or disabled is null)\"",
                            "paramarr = []",
                            "",
                            "condarr = [query]",
                            "condarr.concat(paramarr)",
                            "@" + frontListModel.getPluralName() + " = " + frontListModel.getCapName() + ".paginate(page: params[:page])"
                     //       "@" + frontListModel.getPluralName() + " = " + frontListModel.getCapName() + ".paginate(:page => params[:page], per_page: 10, :conditions => condarr, :order => sort_column + \" \" + sort_direction)"};
                    };

        }
        addMethod(buf, "home", frontContent);

        for (StaticPage page : app.getStaticPages()) {
            addMethod(buf, page.getName(), new String[] {""});
        }

        // TODO: needs to be configurable
        frontContent = new String[] {"UserMailer.contact_admin(params[:email], params[:name], params[:comment]).deliver",
        "flash[:success] = \"Thanks for your feedback... we will review it soon.\"",
        "redirect_to root_path"};
        addMethod(buf, "submitcontact", frontContent);


        tabbed(buf, "private");
        addMethod(buf, "sort_column", new String[] {frontListModel.getCapName() + ".column_names.include?(params[:sort]) ? params[:sort] : \"created_at\""});
        addMethod(buf, "sort_direction", new String[] {"%w[asc desc].include?(params[:direction]) ? params[:direction] : \"desc\""});

        StringUtils.addLine(buf, "end");

        FileUtils.write(buf, app.getWebAppDir() + "/app/controllers/static_pages_controller.rb", true);

        /**
         * class StaticPagesController < ApplicationController
         protect_from_forgery

         def home
         if !signed_in?
         query = "(disabled = 'f' or disabled is null)"
         paramarr = []

         condarr = [query]
         condarr.concat(paramarr)

         @listings = Listing.paginate(:page => params[:page], per_page: 10, :conditions => condarr, :order => sort_column + " " + sort_direction)
         else
         @listings = current_breeder.listings.paginate(:page => params[:page], per_page: 10, :order => sort_column + " " + sort_direction)
         end

         end

         def articles
         end
         */
    }

    public String getRailsType (Type type) {
        if (type.equals(Type.STRING))
            return ("string");
        else  if (type.equals(Type.BOOLEAN))
            return ("boolean");
        else  if (type.equals(Type.SHORT_STRING))
            return ("string");
        else  if (type.equals(Type.EMAIL))
            return ("string");
        else  if (type.equals(Type.PHONE))
            return ("string");
        else  if (type.equals(Type.URL))
            return ("string");
        else  if (type.equals(Type.DATE))
            return ("date");
        else  if (type.equals(Type.TIME))
            return ("time");
        else  if (type.equals(Type.DATETIME))
            return ("datetime");
        else  if (type.equals(Type.LONG_STRING))
            return ("text");
        else  if (type.equals(Type.INT))
            return ("integer");
        else  if (type.equals(Type.FLOAT))
            return ("float");
        else  {
            return ("integer"); // assume it's a model type for now?
        }
//        else
//            throw new RuntimeException("Unsupported type: " + type);
    }

    public void generateUpgrades () throws Exception {
        ArrayList<Model>  models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                String name = model.getName();
                ArrayList<Field>    fields = model.getFields();
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuilder buf = new StringBuilder();

                String upperPluralName = WordUtils.pluralize(WordUtils.capitalize(name));
                String className = "Create" + upperPluralName;
                StringUtils.addLine(buf, "class " + className + " < ActiveRecord::Migration");

                tabbed(buf, "def change");
                tabbed(buf, "create_table :" + model.getPluralName() + " do |t|", 2);

                if (fields != null) {
                    for (Field field : fields) {
                        if (field.isComputed())
                            continue;

                        tabbed(buf, "t." + getRailsType(field.getTheType()) + " :" + field.getName(), 3);
                    }
                }

                if (model.isSecure()) {
                    tabbed(buf, "t.string :remember_digest");
                }

                if (rels != null) {
                    for (Rel rel : rels) {
                        tabbed(buf, "t.integer :" + rel.getModel().getName() + "_id", 3);
                    }
                }
                tabbed(buf, "t.timestamps", 3);
                tabbed(buf, "end", 2);
                tabbed(buf, "end");
                StringUtils.addLine(buf, "end");

                String endFileName = "_create_" + WordUtils.pluralize(name) + ".rb";

                File f = FileUtils.fileEndingIn(app.getWebAppDir() + "/db/migrate/", endFileName);

                if (f != null) {
                    f.delete();
                }

                String fileName = String.valueOf(System.currentTimeMillis()) + endFileName;
                FileUtils.write(buf, app.getWebAppDir() + "/db/migrate/" + fileName, true);
            }
        }

        String 	railsCmd = app.isWindows() ? "C:/RailsInstaller/Ruby2.1.0/bin/bundle.bat" : "bundle";

        //runCommandInApp(railsCmd + " exec rake db:migrate");
    }

    private void runCommandInApp (String command) throws Exception {
        if (true) {
        //if (!app.isWindows()) {
            String [] cmd = command.split(" ");
            String result = runCommandWithEnv(app.getWebAppDir(), cmd, null);
            
            System.out.println ("Result of running command: '" + command + "' was: " + result);
        }
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
        addGemToGroup(gemfileLines, "assets", "jquery-ui-rails", null, false);

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
            addGem(gemfileLines, "fog");
        }

        addGem(gemfileLines, "actionmailer");
        addGem(gemfileLines, "coderay");
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

    public void generateAppStructure () throws Exception {
        /**
         * run: rails new 'app name'
         */
    	String 	railsCmd = app.isWindows() ? "C:/RailsInstaller/Ruby2.1.0/bin/rails.bat" : "rails";
    	
    	String result = runCommand(app.getRootDir(), railsCmd, "new", app.getName());
    	
    	System.out.println(result);
    }


    public void generateDeploymentScript () throws Exception {
    	
    	StringBuilder builder = new StringBuilder();
    	StringUtils.addLine(builder, "#!/bin/sh");
    	StringUtils.addLine(builder, "bundle install --without production");
    	StringUtils.addLine(builder, "bundle assets precompile");
    	StringUtils.addLine(builder, "bundle exec rake db:migrate");
    	//StringUtils.addLine(builder, "git add .");
    	//StringUtils.addLine(builder, "git commit -a -m \"the app code\"");
    	//StringUtils.addLine(builder, "git push heroku master");

    	FileUtils.write(builder, app.getWebAppDir() + "/setup.sh", true);

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
