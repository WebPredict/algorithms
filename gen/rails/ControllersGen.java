package gen.rails;

import alg.io.FileUtils;
import alg.strings.StringUtils;
import alg.words.WordUtils;
import gen.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/31/15
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class ControllersGen extends RailsGenBase {

    public ControllersGen(App app) {
        super(app);
    }

    public void generateControllers () throws Exception {
        ArrayList<Model> models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                String              name = model.getName();
                String              capName = WordUtils.capitalize(name);
                String              names = model.getPluralName();
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuilder buf = new StringBuilder();
                String  className = WordUtils.capitalizeAndJoin(names, "controller");
                StringUtils.addLine(buf, "class " + className + " < ApplicationController");
                Model parentModel = model.getParentModel();  // TODO this is not going to work if there are multiple parents
                if (model.isSecure()) {
                    tabbed(buf, "before_filter :logged_in_" + name + ", only: [:edit, :update, :destroy]");
                    tabbed(buf, "before_filter :correct_" + name + ", only: [:edit, :update]");
                }
                else if (parentModel != null) {
                    if (parentModel.isSecure())
                        tabbed(buf, "before_filter :logged_in_" + parentModel.getName() + ", only: [:edit, :update, :destroy]");
                    tabbed(buf, "before_filter :correct_" + parentModel.getName() + ", only: [:edit, :update]");
                }
                // TODO: also need to restrict from editing items that they don't own with correct_user on other controllers
                /**
                 * If it's a dependent relationship, restrict access
                 */

                tabbed(buf, "helper_method :sort_column, :sort_direction");

                ArrayList<String> createLines = new ArrayList<String>();
                if (parentModel != null) {
                    createLines.add("@" + parentModel.getName() + " = " + parentModel.getCapName() + ".find_by_id(params[:" + name + "][:" + parentModel.getName() + "_id])");
                    String modelDepName = model.getPluralName(); // TODO could be singular if relationship is one-to-one
                    createLines.add("@" + name + " = @" + parentModel.getName() + "." + modelDepName + ".build(" + name + "_params)");
                }
                else {
                    createLines.add("@" + name + " = " + capName + ".new(" + name + "_params)");
                }
                createLines.add("if @" + name + ".save");
                if (model.isSecure())
                    createLines.add("flash[:success] = \"Welcome to " + app.getTitle() + "!\"");
                else
                    createLines.add("flash[:success] = \"Created new " + model.getName() + ".\"");
                createLines.add("\tredirect_to root_path");
                createLines.add("else");
                createLines.add("\trender 'new'");
                createLines.add("end");

                addMethod(buf, "create", createLines);

                ArrayList<String> newLines = new ArrayList<String>();
                newLines.add("@" + name + " = " + capName + ".new");
                if (model.getRelationships() != null) {
                    for (Rel rel : model.getRelationships()) {
                        if (rel.getRelType().equals(RelType.MANY_TO_ONE)) {
                            newLines.add("@" + name + "." + rel.getModelName() + "_id = params[:" + rel.getModelName() + "_id]");
                        }
                    }
                }

                addMethod(buf, "new", newLines);

                addMethod(buf, "edit", new String[] {"@" + name + " = " + capName + ".find(params[:id])"});

                addMethod(buf, "update", new String[] {"@" + name + " = " + capName + ".find(params[:id])",
                        "if @" + name + ".update_attributes(" + name + "_params)",
                        "flash[:success] = \"" + capName + " updated.\"",
                        "redirect_to @" + name,
                        "else",
                        "render 'edit'",
                        "end"
                });

                addMethod(buf, "disable", new String[] {"@" + name + " = " + capName + ".find(params[:id])",
                        "@" + name + ".disabled = true",
                        "@" + name + ".save",
                        "flash[:success] = \"" + capName + " disabled.\"",
                        "redirect_to :back"
                });

                addMethod(buf, "enable", new String[] {"@" + name + " = " + capName + ".find(params[:id])",
                        "@" + name + ".disabled = false",
                        "@" + name + ".save",
                        "flash[:success] = \"" + capName + " enabled.\"",
                        "redirect_to :back"
                });

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
                //indexMethod.add("query = \"(disabled = 'f' or disabled is null)\"");
                //indexMethod.add("condarr = [query]");
                //indexMethod.add("@" + names + " = " + capName + ".where(\"disabled = 'f' or disabled is null\").order(sort_column + \" \" + sort_direction).paginate(:page => params[:page])");
                // TODO: debug what's up with this where clause not working
                indexMethod.add("@" + names + " = " + capName + ".order(sort_column + \" \" + sort_direction).paginate(:page => params[:page])");
                addMethod(buf, "index", indexMethod);

                addMethod(buf, "destroy", new String[] {capName + ".find(params[:id]).destroy",
                        "flash[:success] = \"" + capName + " removed from system.\"",
                        "redirect_to " + names + "_path"});

                StringUtils.addLineBreak(buf);
                tabbed(buf, "private");

                String fieldList = "";
                for (int i = 0; i < model.getFields().size(); i++) {
                    Field f = model.getFields().get(i);

                    fieldList += ":" + f.getName();
                    if (i < model.getFields().size() - 1)
                        fieldList += ", ";
                }
                addMethod(buf, name + "_params", new String[] {"params.require(:" + name + ").permit(" + fieldList + ")"});

                if (model.isSecure()) {
                    addMethod(buf, "correct_" + name, new String[] {"@" + name + " = " + capName + ".find(params[:id])",
                            "redirect_to(root_url) unless @" + name + " == current_" + name});

                    addMethod(buf, "logged_in_" + name, new String[] {"unless logged_in?", "store_location", "flash[:danger] = \"Please log in.\"", "redirect_to signin_url", "end"});
                }
                else if (parentModel != null) {
                    addMethod(buf, "correct_" + parentModel.getName(), new String[] {"@" + name + " = current_" + parentModel.getName() + "." + model.getPluralName() + ".find_by_id(params[:id])",
                            "redirect_to root_path if @" + name + ".nil?"});
                }

                addMethodTabbed(buf, "sort_column", new String[]{
                        capName + ".column_names.include?(params[:sort]) ? params[:sort] : \"" + model.getUserIndentifierFieldName() + "\" "});

                addMethodTabbed(buf, "sort_direction", new String[]{"%w[asc desc].include?(params[:direction]) ? params[:direction] : \"asc\" "});

                StringUtils.addLine(buf, "end");

                FileUtils.write(buf, app.getWebAppDir() + "/app/controllers/" + names + "_controller.rb", true);
            }

            if (app.getAppConfig().isNeedsAuth()) {

                StringBuilder buf = new StringBuilder();
                StringUtils.addLine(buf, "class SessionsController < ApplicationController");

                addMethod(buf, "new", new String[] {});
                StringUtils.addLineBreak(buf);

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
                            "@static_pages = " + frontListModel.getCapName() + ".paginate(page: params[:page])"
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
    }


}
