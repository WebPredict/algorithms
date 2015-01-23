package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/23/15
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaticPage {
    private String content;
    private String name;
    private String title;
    private Layout layout;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
