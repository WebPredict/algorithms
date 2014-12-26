package gen;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/26/14
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Blurb {

    private String title;
    private String content;

    private URL associatedImageLink;

    public Blurb(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public URL getAssociatedImageLink() {
        return associatedImageLink;
    }

    public void setAssociatedImageLink(URL associatedImageLink) {
        this.associatedImageLink = associatedImageLink;
    }
}
