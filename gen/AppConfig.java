package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppConfig {

    private Layout layout;
    private String title;
    private String color1;
    private String color2;
    private String color3;
    private boolean header = true;
    private boolean footer = true;
    private boolean menu;
    private boolean search;
    private boolean needsAuth;
    private boolean needsSignup;
    private boolean basicStaticPages = true;
    private boolean includeAuditInfo = false; //
    private boolean includeCreatedUpdatedBy = true;

    public boolean isIncludeAuditInfo() {
        return includeAuditInfo;
    }

    public void setIncludeAuditInfo(boolean includeAuditInfo) {
        this.includeAuditInfo = includeAuditInfo;
    }

    public boolean isIncludeCreatedUpdatedBy() {
        return includeCreatedUpdatedBy;
    }

    public void setIncludeCreatedUpdatedBy(boolean includeCreatedUpdatedBy) {
        this.includeCreatedUpdatedBy = includeCreatedUpdatedBy;
    }

    public boolean isNeedsSignup() {
        return needsSignup;
    }

    public void setNeedsSignup(boolean needsSignup) {
        this.needsSignup = needsSignup;
    }

    public boolean isBasicStaticPages() {
        return basicStaticPages;
    }

    public void setBasicStaticPages(boolean basicStaticPages) {
        this.basicStaticPages = basicStaticPages;
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public boolean isMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public boolean isNeedsAuth() {
        return needsAuth;
    }

    public void setNeedsAuth(boolean needsAuth) {
        this.needsAuth = needsAuth;
    }
}
