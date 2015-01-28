package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/28/15
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Range extends Type {

    public Range (String subtype) {
        super(RANGE.getName());
        this.subtype = Type.findByName(subtype.substring(1, subtype.length() - 1));
    }

}
