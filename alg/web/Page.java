package alg.web;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/23/14
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Page {

    private String title;
    private List<String> sections;
    private List<Link> links;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSections() {
        return sections;
    }

    public void setSections(List<String> sections) {
        this.sections = sections;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
