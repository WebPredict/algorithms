package alg.web;

import alg.strings.StringUtils;
import alg.words.WordUtil;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/23/14
 * Time: 8:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class HTMLUtil {

    public static void    addParagraph (StringBuffer buf, String content) {
          buf.append("<p>" + content + "</p>\n");
    }

    public static void      addRuby (StringBuffer buf, String content) {
        buf.append("<% " + content + " %>\n");
    }

    public static void      addRubyOutput (StringBuffer buf, String content) {
        buf.append("<%= " + content + " %>\n");
    }

    public static void      addH1 (StringBuffer buf, String content) {
        buf.append("<h1>" + content + "</h1>\n");
    }

    public static void      addFormElement (StringBuffer buf, String fieldName, String fieldType) {
        addFormElement(buf, fieldName, fieldType, null);
    }

    public static void      addSubmitButton (StringBuffer buf, String fieldName, String fieldLabel) {

        buf.append("<input class=\"btn btn-large btn-primary\" name=\"commit\" type=\"submit\" value=\"" + fieldLabel + "\" />\n");
    }

    public static void      addForm (StringBuffer buf, String action) {

        buf.append("<form action=\"/" + action + "\" method=\"post\">\n");
    }

    public static void      addRow (StringBuffer buf, String span, String offset) {
        buf.append("<div class=\"row\">\n");
        buf.append("<div class=\"" + span + "\" " + offset + "\">\n");
    }

    public static void      closeRow (StringBuffer buf) {
        buf.append("</div>\n");
        buf.append("</div>\n");
    }

    public static void      addFormElement (StringBuffer buf, String fieldName, String fieldType, String fieldLabel) {
        buf.append("<label for=\"" + fieldName + "\">");
        if (fieldLabel != null)
            buf.append(fieldLabel);
        else
            buf.append(WordUtil.capitalizeAndSpace(fieldName));
         buf.append("</label>\n");

        if (fieldType.equals("string")) {
            buf.append("<input type=\"text\" size=\"30\" id=\"" + fieldName + "\" name=\"" + fieldName + "\" />\n");
        }
        else if (fieldType.equals("long_string")) {
            buf.append("<textarea cols=\"40\" rows=\"5\" id=\"" + fieldName + "\" name=\"" + fieldName + "\" /> </textarea>\n");
        }
        else if (fieldType.equals("boolean")) {
            // TODO
        }
        else if (fieldType.equals("integer")) {
            // TODO
        }
    }

    public static void      addFormElementDropdown (StringBuffer buf, String fieldName, String [] fieldValues, String fieldLabel) {
        buf.append("<label for=\"" + fieldName + "\">");
        if (fieldLabel != null)
            buf.append(fieldLabel);
        else
            buf.append(WordUtil.capitalizeAndSpace(fieldName));
        buf.append("</label>\n");

        buf.append("<input type=\"select\" size=\"30\" id=\"" + fieldName + "\" name=\"" + fieldName + "\" />\n");
        // TODO
    }

    // renders a table where cell values are computed with a formula
    public static void      addTable (StringBuffer buf, String fieldName, String [] colNames, String [] colValueFormulas) {
        // TODO
    }

    public static String    formatHTML (String content, int tabSize) {

        String []   lines = content.split("\n");

        if (lines != null) {
            Stack<String>   tags = new Stack<String>();
            int             indent = 0;
            for (int i = 0; i < lines.length; i++) {
                String line = lines [i];

                int unbalanced = unbalancedTags(line);
                if (unbalanced > 0) {
                    indent += tabSize;
                }
                else if (unbalanced < 0) {
                    if (indent >= tabSize)
                        indent -= tabSize;
                }

                if (indent > 0)
                    lines [i] = String.copyValueOf(new char [] {' '}, 0, indent) + lines [i];

            }
        }
        return (content);
    }

    public static int   unbalancedTags (String line) {
        if (line == null || line.trim().equals(""))
            return (0);

        boolean startTag = false;
        boolean startedEndTag = false;
        boolean needEndTag = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '<') {
                if (i >= line.length() - 1 || line.charAt(i + 1) != '/')
                    startTag = true;
                else
                    startedEndTag = true;
            }
            else if (c == ' ') {
                if (startTag) {

                    startTag = false;
                    needEndTag = true;
                }
            }
            else if (c == '>') {
                if (startTag) {
                    startTag = false;
                    needEndTag = true;
                }
                else if (needEndTag && startedEndTag) {
                    startedEndTag = false;
                    needEndTag = false;
                }
            }
        }
        if (!startTag && !startedEndTag)
            return (0);
        else if (needEndTag) {
             return (1);
        }
        else
            return (-1);
    }

}
