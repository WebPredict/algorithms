package gen;

import alg.io.FileUtils;
import alg.strings.StringUtils;
import alg.web.HTMLUtil;
import alg.words.WordUtil;

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
            generateGems();
            generateModels();
            generateControllers();
            generateViews();
            generateStaticPages();
            generateHelperMethods();
            generateAssets();
            generateUpgrades();
            generateRoutes();
            generateMailers();
            generateImageUploaders();
        }
    }

    public void generateMailers () throws Exception {

    }

    public void generateImageUploaders () throws Exception {

    }

    public void generateStaticPages () throws Exception {

        generateAboutPage();
        generateHomePage();
        generateHelpPage();
        generateContactPage();
        generateNewsPage();
    }

    public void generateAboutPage () throws Exception {
        StringBuffer buf = new StringBuffer();
        HTMLUtil.addRuby(buf, "provide(:title, 'About')");
        HTMLUtil.addH1(buf, "About " + WordUtil.capitalize(app.getName()));
        HTMLUtil.addParagraph(buf, "This is the about section to be filled in.");
        HTMLUtil.addParagraph(buf, "This is the about section second paragraph to be filled in.");

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/about.html.erb", true);
    }

    public void generateHelpPage () throws Exception {
        StringBuffer buf = new StringBuffer();
        HTMLUtil.addRuby(buf, "provide(:title, 'Help')");
        HTMLUtil.addH1(buf, "Help for " + WordUtil.capitalize(app.getName()));
        HTMLUtil.addParagraph(buf, "This is the help item to be filled in.");
        HTMLUtil.addParagraph(buf, "This is the second help item to be filled in.");

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/help.html.erb", true);
    }

    public void generateNewsPage () throws Exception {
        StringBuffer buf = new StringBuffer();
        HTMLUtil.addRuby(buf, "provide(:title, 'News')");
        HTMLUtil.addH1(buf, "News about " + WordUtil.capitalize(app.getName()));
        HTMLUtil.addParagraph(buf, "This is the first news item be filled in.");
        HTMLUtil.addParagraph(buf, "This is the second news item to be filled in.");
        HTMLUtil.addParagraph(buf, "This is the third news item to be filled in.");

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/news.html.erb", true);
    }

    public void generateHomePage () throws Exception {
    }

    public void generateContactPage () throws Exception {

        StringBuffer buf = new StringBuffer();
        HTMLUtil.addRuby(buf, "provide(:title, 'Contact')");
        HTMLUtil.addH1(buf, "Contact Us");

        HTMLUtil.addRow(buf, "span6", "offset3");
        HTMLUtil.addForm(buf, "submitcontact");

        StringUtils.addLine(buf, "<%= hidden_field_tag :authenticity_token, form_authenticity_token %>");

        HTMLUtil.addFormElement(buf, "email", "string", "Your Email");
        HTMLUtil.addFormElement(buf, "name", "string", "Your Name");
        HTMLUtil.addFormElement(buf, "comment", "long_string", "Your Message");

        HTMLUtil.addSubmitButton(buf, "commit", "Send Message");
        StringUtils.addLine(buf, "</form>");
        HTMLUtil.closeRow(buf);

        FileUtils.write(buf, app.getWebAppDir() + "/app/views/static_pages/contact.html.erb", true);
        /**
         * <% provide(:title, 'Contact') %>
         <h1>Contact Us</h1>

         <div class="row">
         <div class="span6 offset3">
         <form action="/submitcontact" method="post">

         <%= hidden_field_tag :authenticity_token, form_authenticity_token %>

         <label for="email">Your Email</label>
         <input id="email" name="email" size="30" type="text" />

         <label for="name">Your Name</label>
         <input id="name" name="name" size="30" type="text" />

         <label for="comment">Your Message</label>
         <textarea id="comment" name="comment" cols="40" rows="5">Message</textarea>

         </br>
         <input class="btn btn-large btn-primary" name="commit" type="submit" value="Send Message" />
         </form>

         </div>
         </div>
         */
    }

    public void generateHelperMethods () throws Exception {
        if (app.getAppConfig().isNeedsAuth())
            generateSessionsHelper();
        generateApplicationHelper();
    }

    public void generateSessionsHelper () throws Exception {

        Model   userModel = app.getUserModel();
        String  name = userModel.getName();
        String  capName = WordUtil.capitalize(name);

        StringBuffer buf = new StringBuffer();
        StringUtils.addLine(buf, "module SessionsHelper");
        StringUtils.addLineBreak(buf);
        tabbed(buf, "def sign_in(" + name + ")");
        tabbed(buf, "cookies.permanent[:remember_token] = " + name + ".remember_token", 2);
        tabbed(buf, "self.current_" + name + " = " + name, 2);
        tabbed(buf, "end");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def current_" + name + "=(" + name + ")");
        tabbed(buf, "@current_" + name + " = " + name, 2);
        tabbed(buf, "end");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def current_" + name);
        tabbed(buf, "@current_" + name + " ||= " + capName + ".find_by_remember_token(cookies[:remember_token])", 2);
        tabbed(buf, "end ");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def current_" + name + "?(" + name + ")");
        tabbed(buf, "" + name + " == current_" + name + "", 2);
        tabbed(buf, "end");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def signed_in?");
        tabbed(buf, "!current_" + name + ".nil? ", 2);
        tabbed(buf, "end ");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def sign_out");
        tabbed(buf, "self.current_" + name + " = nil", 2);
        tabbed(buf, "cookies.delete(:remember_token)", 2);
        tabbed(buf, "end ");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def redirect_back_or(default) ");
        tabbed(buf, "redirect_to(session[:return_to] || default)  ", 2);
        tabbed(buf, "session.delete(:return_to)   ", 2);
        tabbed(buf, "end  ");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def store_location");
        tabbed(buf, "session[:return_to] = request.fullpath ", 2);
        tabbed(buf, "end");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "def signed_in_" + name + "  ");
        tabbed(buf, "unless signed_in? ", 2);
        tabbed(buf, "store_location ", 2);
        tabbed(buf, "redirect_to signin_path, notice: \"Please sign in.\" ", 2);
        tabbed(buf, "end", 2);
        tabbed(buf, "end ");

        StringUtils.addLine(buf, "end ");

        FileUtils.write(buf, app.getWebAppDir() + "/app/helpers/session_helper.rb", true);
    }


    public void generateRoutes () throws Exception {
        StringBuffer buf = new StringBuffer();

        Model       userModel = app.getUserModel();
        String      pluralName = WordUtil.pluralize(userModel.getName());
        List<Model> models = app.getModels();

        for (Model model : models) {
            tabbed(buf, "resources :" + model.getName() + " do");

            ArrayList<Rel>  rels = model.getRelationships();

            if (rels != null && rels.size() > 0) {
                String s = "get ";
                for (int i = 0; i < rels.size(); i++) {
                    Rel rel = rels.get(i);
                    if (rel.getRelType().equals(RelType.ONE_TO_MANY)) {
                        s += ":" + WordUtil.pluralize(rel.getModel().getName());
                        if (i < rels.size() - 1)
                            s += ", ";
                    }
                }
                if (!s.equals("get "))
                    tabbed(buf, s, 2);
            }
            tabbed(buf, "end");
        }

        tabbed(buf, "resources :sessions, only: [:new, :create, :destroy]");

        StringUtils.addLineBreak(buf);
        tabbed(buf, "root to: 'static_pages#home'");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "match '/help', to: 'static-pages#help'");
        tabbed(buf, "match '/about', to: 'static-pages#about'");
        tabbed(buf, "match '/news', to: 'static-pages#news'");
        tabbed(buf, "match '/contact', to: 'static-pages#contact'");
        tabbed(buf, "match '/help', to: 'static-pages#help'");
        StringUtils.addLineBreak(buf);

        tabbed(buf, "match '/submitcontact', to: 'static-pages#submitcontact'");
        tabbed(buf, "match '/signup', to: '" + pluralName + "#new'");
        tabbed(buf, "match '/send_password', to: '" + pluralName + "#send_password'");
        tabbed(buf, "match '/submit_send_password', to: '" + pluralName + "#submit_send_password'");
        tabbed(buf, "match '/signin', to: 'sessions#new'");
        tabbed(buf, "match '/signout', to: 'sessions#destroy', via: :delete");


        // TODO: don't reinsert this if it's already done to routes.rb:
        FileUtils.insertAtInFile(app.getWebAppDir() + "/config/routes.rb", 2, new String[] {buf.toString()}, true);

        /**
        * resources :breeders do
        member do
        get :listings, :birdtypes, :ratings, :inquiries, :subtypes
        end
        get :contact, :on => :member
        get :disable, :on => :member
        get :enable, :on => :member
        end

        resources :listings do
        get :disable, :on => :member
        get :enable, :on => :member
        end

        match '/mark_responded', to: 'inquiries#mark_responded'
        match '/update_species', to: 'listings#update_species'
        match '/update_small_species', to: 'listings#update_small_species'
        match '/update_breeder_species', to: 'breeders#update_species'

        match '/submitcontact', to: 'static-pages#submitcontact'
        match '/signup', to: 'breeders#new'
        match '/send_password', to: 'breeders#send_password'
        match '/submit_send_password', to: 'breeders#submit_send_password'
        match '/signin', to: 'sessions#new'
        match '/signout', to: 'sessions#destroy', via: :delete

        */
    }

    public void generateApplicationHelper () throws Exception {
        StringBuffer buf = new StringBuffer();

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
        tabbed(buf, "base_title = \"" + WordUtil.capitalize(app.getName()) + "\" ", 2);
        tabbed(buf, "if page_title.empty? ", 2);
        tabbed(buf, "base_title  ", 3);
        tabbed(buf, "else  ", 2);
        tabbed(buf, "\"#{base_title} | #{page_title}\"  ", 3);
        tabbed(buf, "end   ", 2);
        StringUtils.addLineBreak(buf);
        tabbed(buf, "end  ");
        StringUtils.addLine(buf, "end");

        FileUtils.write(buf, app.getWebAppDir() + "/app/helpers/application_helper.rb", true);
    }

    public void generateAssets () throws Exception {

        FileUtils.insertAfterInFile(app.getWebAppDir() + "/app/assets/stylesheets/application.css", new String[]{" *= require_self"},
                new String[]{" *= require jquery.ui.datepicker"}, true, true);

        if (FileUtils.fileExists(app.getWebAppDir() + "/app/assets/stylesheets/bootstrap_and_overrides.css.less"))
            FileUtils.insertAfterInFile(app.getWebAppDir() + "/app/assets/stylesheets/bootstrap_and_overrides.css.less", new String[] {"twitter/bootstrap/bootstrap"},
                    new String[] {"body {\n" +
                            "    padding-top: 60px;\n" +
                            "}"}, true, true);

    }

    public void generateModels () throws Exception {
        ArrayList<Model>  models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                String name = model.getName();
                String              capName = WordUtil.capitalize(name);
                ArrayList<Field>    fields = model.getFields();
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuffer buf = new StringBuffer();
                StringUtils.addLine(buf, "class " + capName + " < ActiveRecord::Base");
                String attrs = "attr_accessible ";

                if (fields != null) {
                    for (int i = 0; i < fields.size(); i++) {
                        Field f = fields.get(i);
                        attrs += ":" + f.getName();

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
                    }
                }
                tabbed(buf, attrs);

                if (model.isSecure()) {
                    tabbed(buf, "has_secure_password");
                }

                if (rels != null) {
                    for (int i = 0; i < rels.size(); i++) {
                        Rel rel = rels.get(i);

                        RelType relType = rel.getRelType();
                        Model   relModel = rel.getModel();
                        if (relType.equals(RelType.ONE_TO_MANY)) {
                            String  toAdd =  "has_many :" + WordUtil.pluralize(relModel.getName());
                            if (rel.isDependent())
                                toAdd += ", dependent: :destroy";

                            StringUtils.addTabbedLine(buf, toAdd);
                        }
                        else if (relType.equals(RelType.MANY_TO_ONE)) {
                            tabbed(buf, "belongs_to :" + WordUtil.pluralize(relModel.getName()));
                        }
                        else if (relType.equals(RelType.MANY_TO_MANY)) {
                            String  toAdd =  "has_many :" + WordUtil.pluralize(relModel.getName());
                            if (rel.getThrough() != null) {
                                toAdd += ", through: " + WordUtil.pluralize(rel.getThrough().getName());
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
                        tabbed(buf, "validates :password, presence: true, if => should_validate_password?, length: { minimum: 6 }");
                        tabbed(buf, "validates :password_confirmation, presence: true, if => should_validate_password?");
                        StringUtils.addLineBreak(buf);

                        tabbed(buf, "def should_validate_password?");
                        tabbed(buf, "new_record?", 2);
                        tabbed(buf, "end");
                        StringUtils.addLineBreak(buf);
                    }
                }

                StringUtils.addLineBreak(buf);

                tabbed(buf, "private");
                if (model.isSecure()) {
                    tabbed(buf, "create_remember_token", 2);
                    tabbed(buf, "self.remember_token = SecureRandom.urlsafe_base64", 3);
                    tabbed(buf, "end", 2);
                }
                StringUtils.addLine(buf, "end");

                FileUtils.write(buf, app.getWebAppDir() + "/app/models/" + capName + ".rb", true);
            }
        }
    }

    private void tabbed (StringBuffer buf, String content) {
        StringUtils.addTabbedLine(buf, content);
    }

    private void tabbed (StringBuffer buf, String content, int tabs) {
        StringUtils.addTabbedLine(buf, content, tabs);
    }

    public void generateViews () throws Exception {
        ArrayList<Model>  models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                String name = model.getName();
                String names = WordUtil.pluralize(name);
                ArrayList<Field>    fields = model.getFields();
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuffer buf = new StringBuffer();


                // TODO: decide when to show tabs for collections
                // have it configurable, and/or determined by # of collections ( > 2 means tabs)

                /**
                 * in app/views, show.html.erb example:
                 * <%- model_class = Contact -%>
                 <div class="page-header">
                 <h1><%=t '.title', :default => model_class.model_name.human.titleize %></h1>
                 </div>

                 <dl class="dl-horizontal">
                 <dt><strong><%= model_class.human_attribute_name(:name) %>:</strong></dt>
                 <dd><%= @contact.name %></dd>
                 <dt><strong><%= model_class.human_attribute_name(:phone) %>:</strong></dt>
                 <dd><%= @contact.phone %></dd>
                 </dl>

                 <div class="form-actions">
                 <%= link_to t('.back', :default => t("helpers.links.back")),
                 contacts_path, :class => 'btn'  %>
                 <%= link_to t('.edit', :default => t("helpers.links.edit")),
                 edit_contact_path(@contact), :class => 'btn' %>
                 <%= link_to t('.destroy', :default => t("helpers.links.destroy")),
                 contact_path(@contact),
                 :method => 'delete',
                 :data => { :confirm => t('.confirm', :default => t("helpers.links.confirm", :default => 'Are you sure?')) },
                 :class => 'btn btn-danger' %>
                 </div>

                 *
                 */

            // TODO: generate partials too
                FileUtils.write(buf, app.getWebAppDir() + "/app/views/" + names + "/show.html.erb", true);
//                FileUtils.write(buf, app.getWebAppDir() + "/app/models/edit.rb");
//                FileUtils.write(buf, app.getWebAppDir() + "/app/models/list.rb");
               // FileUtils.write(buf, app.getWebAppDir() + "/app/models/show.rb");
            }
        }
    }

    protected void addMethod (StringBuffer buf, String name, String [] contentLines) {
        buf.append("\tdef " + name + "\n");
        if (contentLines != null) {
            for (String line : contentLines) {
                buf.append("\t\t" + line + "\n");
            }
        }
        buf.append("\tend\n");
    }

    protected void addMethodTabbed (StringBuffer buf, String name, String [] contentLines) {
        buf.append("\t\tdef " + name + "\n");
        if (contentLines != null) {
            for (String line : contentLines) {
                buf.append("\t\t\t" + line + "\n");
            }
        }
        buf.append("\t\tend\n");
    }

    protected void addMethod (StringBuffer buf, String name, List<String> contentLines) {
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
                String              capName = WordUtil.capitalize(name);
                String              names = WordUtil.pluralize(name);
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuffer buf = new StringBuffer();
                String  className = WordUtil.capitalizeAndJoin(names, "controller");
                StringUtils.addLine(buf, "class " + className + " < ApplicationController");
                tabbed(buf, "before_filter :signed_in_" + name + ", only: [:edit, :update, :destroy]");
                tabbed(buf, "before_filter :correct_" + name + ", only: [:edit, :update]");

                ArrayList<String> createLines = new ArrayList<String>();
                createLines.add("@" + name + " = " + capName + ".new(params[:" + name + "])");
                createLines.add("if @" + name + ".save");
                createLines.add("\tredirect_to root_path");
                createLines.add("else");
                createLines.add("\trender new");
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
                                showMethod.add("@" + WordUtil.pluralize(relModelName) + " = @" + name + "." + WordUtil.pluralize(relModelName) + ".paginate(page: params[:page])");
                                break;

                            case ONE_TO_ONE:
                                showMethod.add("@" + relModel.getName() + " = @" + name + "." +relModelName);  // TODO: needed?
                                break;

                            case MANY_TO_MANY:
                                showMethod.add("@" + WordUtil.pluralize(relModelName) + " = @" + name +
                                        "." + WordUtil.pluralize(relModelName) + ".paginate(page: params[:page])");  // TODO: verify this
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
                        "flash[:success] = " + capName + " removed from system.\"",
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

                StringBuffer buf = new StringBuffer();
                StringUtils.addLine(buf, "class SessionsController < ApplicationController");

                addMethod(buf, "new", new String[] {});
                StringUtils.addLineBreak(buf);

                tabbed(buf, "def create");
                tabbed(buf, "email = params[:session][:email]", 2);
                tabbed(buf, "if email != nil", 2);
                tabbed(buf, "email = email.downcase ", 3);
                tabbed(buf, "end", 2);
                StringUtils.addLineBreak(buf);

                // TODO: don't hardcode user maybe?
                tabbed(buf, "user = User.find_by_email(email)", 2);
                tabbed(buf, "if user && user.authenticate(params[:session][:password])", 2);
                tabbed(buf, "sign_in user", 3);
                tabbed(buf, "redirect_back_or root_path ", 3);
                tabbed(buf, "else", 2);
                tabbed(buf, "flash[:error] = \"Invalid email/password combination\" ", 3);
                tabbed(buf, "render 'new'", 3);
                tabbed(buf, "end", 2);
                tabbed(buf, "end");
                StringUtils.addLineBreak(buf);

                addMethod(buf, "destroy", new String[] {"sign_out", "redirect_to root_path"});

                StringUtils.addLine(buf, "end");

                FileUtils.write(buf, app.getWebAppDir() + "/app/controllers/sessions_controller.rb", true);
            }
        }
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

                StringBuffer buf = new StringBuffer();

                String upperPluralName = WordUtil.pluralize(WordUtil.capitalize(name));
                String className = "Create" + upperPluralName;
                StringUtils.addLine(buf, "class " + className + " < ActiveRecord::Migration");

                tabbed(buf, "def create");
                tabbed(buf, "create_table :" + WordUtil.pluralize(name) + " do |t|", 2);

                if (fields != null) {
                    for (Field field : fields) {
                        tabbed(buf, "t." + getRailsType(field.getTheType()) + " :" + field.getName(), 3);
                    }
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

                String fileName = String.valueOf(System.currentTimeMillis()) + "_create_" + name + ".rb";
                FileUtils.write(buf, app.getWebAppDir() + "/db/migrate/" + fileName, true);
            }
        }
    }

    private void addGem (List<String> gemfileLines, String gemName) {
        String toAdd =  "gem '" + gemName + "'";
        gemfileLines.add(toAdd);
    }

    private void addGem (List<String> gemfileLines, String gemName, String version, boolean exact) {
        String toAdd =  "gem '" + gemName + "'" + (version == null ? "" : ", '" + (exact ? "" : "~> ") + version + "'");
        gemfileLines.add(toAdd);
    }

    public void generateGems () throws Exception {
        /**
         * retrieve file
         */
        List<String> gemfileLines = FileUtils.getLinesCreateIfEmpty(app.getWebAppDir() + "/Gemfile");


        // Heroku stuff:
         FileUtils.insertAfterOrAtEnd(gemfileLines, new String[] {"group :production do"},
                new String[] {"gem 'pg',     '0.17.1'", "gem 'rails12_factor',      '0.0.2'"}, true, true);


//        boolean didIt = FileUtils.insertAfter(gemfileLines, new String[] {"group :assets do"},
//                new String[] {"  gem 'sass-rails', '~> 3.2.3'"}, true, true);

        // if using AWS for images:
        addGem(gemfileLines, "fog");

        addGem(gemfileLines, "carrierwave");
        addGem(gemfileLines, "twitter-bootstrap-rails");
        addGem(gemfileLines, "bcrypt-ruby", "3.0.0", false);
        addGem(gemfileLines, "will-paginate", "3.0.3", true);
        addGem(gemfileLines, "bootstrap-will-paginate", "0.0.6", true);
        addGem(gemfileLines, "faker", "1.0.1", true);

        //String result = runCommand("dir.exe", app.getWebAppDir());

        /**
         * TODO:
         * test data support
         * date selector support
         */
        //String result = runCommand(app.getWebAppDir(), "bundle.exe", "install");
    }

    public void generateAppStructure () throws Exception {
        /**
         * run: rails new 'app name'
         */

        String result = runCommand(app.getRootDir(), "C:/RailsInstaller/Ruby2.1.0/bin/rails.bat", "new", app.getName());
        System.out.println(result);
    }


    public void generateDeploymentScript () {
        /**
         * rake assets precompile
         * git add .
         * git commit -m "more changes"
         * git push heroku master
         */
    }
}
