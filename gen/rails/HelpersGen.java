package gen.rails;

import alg.io.FileUtils;
import alg.strings.StringUtils;
import alg.words.WordUtils;
import gen.App;
import gen.Model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/31/15
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class HelpersGen extends RailsGenBase {

    public HelpersGen(App app) {
        super(app);
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

        addMethod(buf, "logged_in?", new String[] {"!current_" + name + ".nil?"});
        addMethod(buf, "log_in(" + name + ")", new String[] {"session[:" + name + "_id] = " + name + ".id"});
        addMethod(buf, "remember(" + name + ")", new String[] {name + ".remember", "cookies.permanent.signed[:" + name + "_id] = " + name + ".id",
                "cookies.permanent[:remember_token] = " + name + ".remember_token"});
        addMethod(buf, "forget(" + name + ")", new String[] {name + ".forget", "cookies.delete(:" + name + "_id)", "cookies.delete(:remember_token)"});
        addMethod(buf, "log_out", new String[] {"forget(current_" + name + ")", "session.delete(:" + name + "_id)", "@current_" + name + " = nil"});

        addMethod(buf, "current_" + name, new String[] {"@current_" + name + " ||= " + capName + ".find_by(id: session[:" + name + "_id])"});

        addMethod(buf, "current_" + name + "?(" + name + ")", new String[] {name + " == current_" + name});
//        addMethod(buf, "current_" + name + "=(" + name + ")", new String[] {"@current_" + name + " = " + name});
        addMethod(buf, "redirect_back_or(default)", new String[] {"redirect_to(session[:forwarding_url] || default)", "session.delete(:forwarding_url)"});
//        addMethod(buf, "sign_in(" + name + ")", new String[] {"cookies.permanent[:remember_token] = " + name + ".remember_token", "self.current_" + name + " = " + name});
//        addMethod(buf, "redirect_back_or(default)", new String[] {"redirect_to(session[:return_to] || default)", "session.delete(:return_to)"});
        addMethod(buf, "store_location", new String[] {"session[:forwarding_url] = request.url if request.get?"});
        addMethod(buf, "logged_in_" + name, new String[] {"unless logged_in?",
                "store_location",
                "flash[:danger] = \"Please log in.\"",
                "redirect_to signin_url",
                "end"});

        StringUtils.addLine(buf, "end ");

        FileUtils.write(buf, app.getWebAppDir() + "/app/helpers/sessions_helper.rb", true);
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
        tabbed(buf, "if imageholder.nil? or imageholder.url.nil?", 2);
        tabbed(buf, "image_tag(\"ImagePlaceholderSmall.png\", options)", 3);
        tabbed(buf, "else", 2);
        tabbed(buf, "image_tag(imageholder.url, options)", 3);
        tabbed(buf, "end", 2);
        tabbed(buf, "end");

        StringUtils.addLineBreak(buf);
        tabbed(buf, "def image_for(imageholder)");
        tabbed(buf, "if imageholder.nil? or imageholder.url.nil?", 2);
        tabbed(buf, "image_tag(\"ImagePlaceholderSmall.png\")", 3);
        tabbed(buf, "else", 2);
        tabbed(buf, "image_tag(imageholder.url)", 3);
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
        methodLines.addAll(generateIf("address.addressLine1 != nil", "addr = address.addressLine1"));

        methodLines.addAll(generateIf("address.city != nil", "addr != '' ? addr += ' ' + address.city : address.city"));
        methodLines.addAll(generateIf("address.state != nil", "addr != '' ? addr += ', ' + address.state : address.state"));
        methodLines.addAll(generateIf("address.postal != nil", "addr != '' ? addr += ', ' + address.postal : address.postal"));

        addMethod(buf, "render_address(address)", methodLines);

        StringUtils.addLine(buf, "end");

        FileUtils.write(buf, app.getWebAppDir() + "/app/helpers/application_helper.rb", true);
    }

}
