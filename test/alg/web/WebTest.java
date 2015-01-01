package test.alg.web;

import alg.web.HTMLUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/1/15
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebTest {

    public static void main (String [] args) {

        int unbalanced = HTMLUtils.unbalancedTags(" <select name=\"contact[contactType]\" id=\"contact_contactType\" class=\"input-medium\">");
        assert(unbalanced == 1);

        unbalanced = HTMLUtils.unbalancedTags("<select>");
        assert(unbalanced == 1);
        unbalanced = HTMLUtils.unbalancedTags("<select name=\"contact[contactType]\" id=\"contact_contactType\" class=\"input-medium\">blah</select>");
        assert(unbalanced == 0);
        unbalanced = HTMLUtils.unbalancedTags(" </select>");
        assert(unbalanced == -1);

        unbalanced = HTMLUtils.unbalancedTags("</something></select>");
        assert(unbalanced == -2);

        unbalanced = HTMLUtils.unbalancedTags("<something><select>");
        assert(unbalanced == 2);

        unbalanced = HTMLUtils.unbalancedTags("<something>asdf <select id='blah'> ");
        assert(unbalanced == 2);

        unbalanced = HTMLUtils.unbalancedTags("");
        assert(unbalanced == 0);

        unbalanced = HTMLUtils.unbalancedTags("this is a test");
        assert(unbalanced == 0);

    }
}
