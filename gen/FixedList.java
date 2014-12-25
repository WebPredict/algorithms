package gen;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/22/14
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class FixedList extends Type {

    private String [] values;

    public String [] getValues() {
        return values;
    }

    public void setValues(String [] values) {
        this.values = values;
    }

    public FixedList (String raw) {
        super(Type.FIXED_LIST.getName());
        values = raw.substring(1, raw.length() - 1).split("\\|");
    }
}
