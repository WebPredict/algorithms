package gen.rails;

import alg.strings.StringUtils;
import alg.web.HTMLUtils;
import gen.App;
import gen.Generator;
import gen.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/31/15
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class RailsGenBase {

    protected App app;

    protected RailsGenBase(App app) {
       this.app = app;
    }

    protected void tabbed (StringBuilder buf, String content) {
        StringUtils.addTabbedLine(buf, content);
    }

    protected void tabbed (StringBuilder buf, String content, int tabs) {
        StringUtils.addTabbedLine(buf, content, tabs);
    }

    public String getRailsType (Type type) {
        if (type.equals(Type.STRING))
            return ("string");
        else  if (type.equals(Type.BOOLEAN))
            return ("boolean");
        else  if (type.equals(Type.EMAIL))
            return ("string");
        else  if (type.equals(Type.PASSWORD))
            return ("string");
        else  if (type.equals(Type.CODE))
            return ("string");
        else  if (type.equals(Type.SHORT_STRING))
            return ("string");
        else  if (type.equals(Type.PHONE))
            return ("string");
        else  if (type.equals(Type.URL))
            return ("string");
        else  if (type.equals(Type.DATE))
            return ("date");
        else  if (type.equals(Type.IMAGE))
            return ("string");
        else  if (type.equals(Type.TIME))
            return ("time");
        else  if (type.equals(Type.DATETIME))
            return ("datetime");
        else  if (type.equals(Type.LONG_STRING))
            return ("text");
        else  if (type.equals(Type.INT))
            return ("integer");
        else  if (type.equals(Type.RANGE))
            return ("string");
        else  if (type.equals(Type.FLOAT))
            return ("float");
        else if (type.getName().equals(Type.SET_ONE_OR_MORE.getName()))
            return ("text");
        else if (type.getName().equals(Type.SET_PICK_ONE.getName()))
            return ("text");
        else if (type.getName().equals(Type.FIXED_LIST.getName()))
            return ("string");
        else  {
            return ("integer"); // assume it's a model type for now?
        }
//        else
//            throw new RuntimeException("Unsupported type: " + type);
    }

    protected String  getRailsCommand () {
        return (app.isWindows() ? "C:/RailsInstaller/Ruby2.1.0/bin/rails.bat" : "rails");
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

    protected void div (StringBuilder builder, String className) {
        HTMLUtils.addDiv(builder, className);
    }

    protected void closeDiv (StringBuilder builder) {
        HTMLUtils.closeDiv(builder);
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

}
