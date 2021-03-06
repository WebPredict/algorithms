package alg.web;

import alg.misc.InterestingAlgorithm;
import alg.strings.StringUtils;
import alg.words.WordUtils;

import java.util.Stack;

/**
 *
 * At the moment, uses Bootstrap
 */
public class HTMLUtils {

    public static void    addParagraph (StringBuilder buf, String content) {
          buf.append("<p>" + content + "</p>\n");
    }

    public static void    addParagraph (StringBuilder buf, String content, String className) {
        buf.append("<p class=\"" + className + "\">" + content + "</p>\n");
    }

    public static void      addRuby (StringBuilder buf, String content) {
        buf.append("<% " + content + " %>\n");
    }

    public static void      addLineBreak (StringBuilder buf) {
        buf.append("</br>\n");
    }

    public static void      addRubyOutput (StringBuilder buf, String content) {
        buf.append("<%= " + content + " %>\n");
    }

    public static void      addH1 (StringBuilder buf, String content) {
        buf.append("<h1>" + content + "</h1>\n");
    }

    public static void      addH2 (StringBuilder buf, String content) {
        buf.append("<h2>" + content + "</h2>\n");
    }

    public static void      addH3 (StringBuilder buf, String content) {
        buf.append("<h3>" + content + "</h3>\n");
    }

    public static void      addH1 (StringBuilder buf, String content, String style) {
        buf.append("<h1 style=\"" + style + "\">" + content + "</h1>\n");
    }

    public static void      addH2 (StringBuilder buf, String content, String style) {
        buf.append("<h2 style=\"" + style + "\">" + content + "</h2>\n");
    }

    public static void      addFormElement (StringBuilder buf, String fieldName, String fieldType) {
        addFormElement(buf, fieldName, fieldType, null);
    }

    public static void      addSubmitButton (StringBuilder buf, String fieldName, String fieldLabel) {
        addButton(buf, fieldName, fieldLabel, "submit");
    }

    public static void      addButton (StringBuilder buf, String fieldName, String fieldLabel, String buttonType) {
        buf.append("<input class=\"btn btn-lg btn-primary\" name=\"" + fieldName + "\" type=\"" + buttonType + "\" value=\"" + fieldLabel + "\" />\n");
    }

    public static void      addSmallButton (StringBuilder buf, String fieldName, String fieldLabel, String buttonType) {
        buf.append("<input class=\"btn btn-sm btn-default\" name=\"" + fieldName + "\" type=\"" + buttonType + "\" value=\"" + fieldLabel + "\" />\n");
    }

    public static void      addForm (StringBuilder buf, String action) {
        buf.append("<form action=\"/" + action + "\" method=\"post\">\n");
    }

    public static void      addForm (StringBuilder buf, String action, String className) {
        buf.append("<form action=\"/" + action + "\" method=\"post\" class=\"" + className + "\">\n");
    }

    // These are bootstrap specific:
    public static void      addRow (StringBuilder buf, String span, String offset) {
        buf.append("<div class=\"row\">\n");
        buf.append("\t<div class=\"" + span + "\" " + offset + "\">\n");
    }

    public static void      closeRow (StringBuilder buf) {
        buf.append("\t</div>\n");
        buf.append("</div>\n");
    }

    public static void		addDiv (StringBuilder buf, String divClass) {
    	buf.append("<div class=\"" + divClass + "\">\n");
    }

    public static void		addDivWithContent (StringBuilder buf, String divClass, String content) {
        buf.append("<div class=\"" + divClass + "\">" + content + "</div>\n");
    }

    public static void		addDivWithId (StringBuilder buf, String divClass, String id) {
        buf.append("<div class=\"" + divClass + "\" id=\"" + id + "\">");
    }

    public static void		addSpan (StringBuilder buf, String content) {
        buf.append("<span>" + content + "</span>\n");
    }

    public static void		addDivId (StringBuilder buf, String divClass) {
        buf.append("<div id=\"" + divClass + "\">\n");
    }

    public static void		closeDiv (StringBuilder buf) {
    	buf.append("</div>\n");
    }

    public static void		close (StringBuilder buf, String elementName) {
        buf.append("</" + elementName + ">\n");
    }

    public static void      addFormElement (StringBuilder buf, String fieldName, String fieldType, String fieldLabel) {
        HTMLUtils.addDiv(buf, "form-group");

        buf.append("<label class=\"col-sm-2 control-label\" for=\"" + fieldName + "\">");
        if (fieldLabel != null)
            buf.append(fieldLabel);
        else
            buf.append(WordUtils.capitalizeAndSpace(fieldName));
         buf.append("</label>\n");

        HTMLUtils.addDiv(buf, "col-sm-8");
        if (fieldType.equals("string")) {
            buf.append("<input class=\"form-control\" type=\"text\" size=\"30\" id=\"" + fieldName + "\" name=\"" + fieldName + "\" />\n");
        }
        else if (fieldType.equals("long_string")) {
            buf.append("<textarea class=\"form-control\" rows=\"5\" id=\"" + fieldName + "\" name=\"" + fieldName + "\" /> </textarea>\n");
        }
        else if (fieldType.equals("boolean")) {
            // TODO
        }
        else if (fieldType.equals("integer")) {
            // TODO
        }
        HTMLUtils.closeDiv(buf);
        HTMLUtils.closeDiv(buf);
    }

    public static void      addFormElementDropdown (StringBuilder buf, String fieldName, String [] fieldValues, String fieldLabel) {
        buf.append("<label for=\"" + fieldName + "\">");
        if (fieldLabel != null)
            buf.append(fieldLabel);
        else
            buf.append(WordUtils.capitalizeAndSpace(fieldName));
        buf.append("</label>\n");

        buf.append("<input type=\"select\" size=\"30\" id=\"" + fieldName + "\" name=\"" + fieldName + "\" />\n");
        // TODO
    }

    // renders a table where cell values are computed with a formula
    public static void      addTable (StringBuilder buf, String fieldName, String [] colNames, String [] colValueFormulas) {
        // TODO
    }

    @InterestingAlgorithm
    public static String    formatHTML (String content, int tabSize) {

        String []   lines = content.split("\n");

        if (lines != null) {
            Stack<String>   tags = new Stack<String>();
            int             indent = 0;
            for (int i = 0; i < lines.length; i++) {
                String line = lines [i];

                int unbalanced = unbalancedTags(line);
                if (unbalanced > 0) {
                    lines [i] = StringUtils.repeat(' ', indent) + lines [i];
                    indent += tabSize;
                }
                else if (unbalanced < 0) {
                    if (indent >= tabSize)
                        indent -= tabSize;
                    lines [i] = StringUtils.repeat(' ', indent) + lines [i];
                }
                else
                    lines [i] = StringUtils.repeat(' ', indent) + lines [i];

            }
        }

        return (StringUtils.join(lines));
    }

    @InterestingAlgorithm
    public static int   unbalancedTags (String line) {
        if (line == null || line.trim().equals(""))
            return (0);

        int startTags = 0;
        int endTags = 0;
        boolean startTag = false;
        boolean startedEndTag = false;

        // TODO: finish, as this doesn't actually verify the start + end tag names match
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '<') {
                if (i < line.length() - 1 && line.charAt(i + 1) != '%') {
                    if (i >= line.length() - 1 || line.charAt(i + 1) != '/')
                        startTag = true;
                    else
                        startedEndTag = true;
                }
            }
            else if (c == '>') {
                if (i > 0 && line.charAt(i - 1) != '%') {
                    if (startTag) {
                        startTag = false;
                        startTags++;
                    }
                    else if (startedEndTag) {
                        startedEndTag = false;
                        endTags++;
                    }
                }
            }
        }

        return (startTags - endTags);
    }

}
