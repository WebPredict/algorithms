package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/22/14
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Collection extends Type {

    private boolean ordered;

    public Collection (String name, Type subtype, boolean ordered) {
        super(name);
        this.subtype = subtype;
        this.ordered = ordered;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }
}
