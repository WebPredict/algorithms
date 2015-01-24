package gen;

import alg.words.WordUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Type {

    protected String name;
    protected Type subtype; // for parametrized things like Set<Integer>

    public static final Type LONG_STRING = new Type("long_string");
    public static final Type STRING = new Type("string");
    public static final Type SHORT_STRING = new Type("short_string");
    public static final Type BOOLEAN = new Type("boolean");
    public static final Type INT = new Type("int");
    public static final Type FLOAT = new Type("float");
    public static final Type DATE = new Type("date");
    public static final Type TIME = new Type("time");
    public static final Type DATETIME = new Type("datetime");
    public static final Type CURRENCY = new Type("currency");
    public static final Type EMAIL = new Type("email");
    public static final Type PHONE = new Type("phone");
    public static final Type ADDRESS = new Type("address");
    public static final Type IMAGE = new Type("image");
    public static final Type FILE = new Type("file");
    public static final Type URL = new Type("url");
    public static final Type SET_ONE_OR_MORE = new Type("set");
    public static final Type SET_PICK_ONE = new Type("setpickone");
    public static final Type PASSWORD = new Type("password");
    public static final Type RANGE = new Type("range");
    public static final Type LIST = new Type("list");
    public static final Type FIXED_LIST = new Type("fixed_list");
    public static final Type CODE = new Type("code");
    public static final Type VIDEO = new Type("video");

    public static final Type COMPUTED = new Type("computed"); // means don't store in DB


    public boolean isPrimitive () {
        return (name.equals(Type.BOOLEAN.getName()) ||
                name.equals(Type.EMAIL.getName()) ||
                name.equals(Type.PHONE.getName()) ||
                name.equals(Type.DATETIME.getName()) ||
                name.equals(Type.TIME.getName()) ||
                name.equals(Type.DATE.getName()) ||
                name.equals(Type.STRING.getName()) ||
                name.equals(Type.INT.getName()) ||
                name.equals(Type.FLOAT.getName()) ||
                name.equals(Type.LONG_STRING.getName()) ||
                name.equals(Type.SHORT_STRING.getName()));
    }

    public boolean isComputed () {
        return (name.equals(Type.COMPUTED.getName()));
    }

    public static Type findByName (String name) {

        if (name.equals(LONG_STRING.name))
            return (LONG_STRING);
        else if (name.equals(STRING.name))
            return (STRING);
        else if (name.equals(BOOLEAN.name))
            return (BOOLEAN);
        else if (name.equals(SHORT_STRING.name))
            return (SHORT_STRING);
        else if (name.equals(INT.name))
            return (INT);
        else if (name.equals(FLOAT.name))
            return (FLOAT);
        else if (name.equals(DATE.name))
            return (DATE);
        else if (name.equals(DATETIME.name))
            return (DATETIME);
        else if (name.equals(CURRENCY.name))
            return (CURRENCY);
        else if (name.equals(EMAIL.name))
            return (EMAIL);
        else if (name.equals(PASSWORD.name))
            return (PASSWORD);
        else if (name.equals(PHONE.name))
            return (PHONE);
        else if (name.equals(TIME.name))
            return (TIME);
        else if (name.equals(ADDRESS.name))
            return (ADDRESS);
        else if (name.equals(IMAGE.name))
            return (IMAGE);
        else if (name.equals(FILE.name))
            return (FILE);
        else if (name.equals(URL.name))
            return (URL);
        else if (name.startsWith(LIST.name)) {
            Collection type = new Collection(LIST.name, findByName(LIST.name.substring(LIST.getName().length())), true);
            return (type);
        }
        else if (name.startsWith(FIXED_LIST.name)) {
            FixedList type = new FixedList(name.substring(FIXED_LIST.getName().length()));
            return (type);
        }
        else if (name.startsWith(COMPUTED.name)) {
            Type type = new Type(COMPUTED.name);
            type.subtype = new Type(name.substring(COMPUTED.name.length() + 1, name.length() - 2));
            return (type);
        }
        else if (name.startsWith(SET_ONE_OR_MORE.name)) {
            Collection type = new Collection(SET_ONE_OR_MORE.name, findByName(SET_ONE_OR_MORE.name.substring(SET_ONE_OR_MORE.getName().length())), false);
            return (type);
        }
        else if (name.startsWith(SET_PICK_ONE.name)) {
            Collection type = new Collection(SET_PICK_ONE.name, findByName(SET_PICK_ONE.name.substring(SET_PICK_ONE.getName().length())), false);
            return (type);
        }
        else if (name.equals(CODE.name))
            return (CODE);
        else if (name.equals(VIDEO.name))
            return (VIDEO);
        else
            return (new Type(name));
    }

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCapName () {
    	return (WordUtils.capitalize(name));
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String toString () {
        return (name);
    }

    public Type     getSubtype () {
        return (subtype);
    }
}
