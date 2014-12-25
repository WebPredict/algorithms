package gen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Field {

    private Type theType;
    private String name;
    private String defaultVal;
    private List<Validation> validations;

    public List<Validation> getValidations() {
        return validations;
    }

    public void setValidations(List<Validation> validations) {
        this.validations = validations;
    }

    public void addValidation (Validation validation) {
        if (validations == null)
            validations = new ArrayList<Validation>();

        validations.add(validation);
    }

    public Field () { }

    public Field (String name, String type) {
        this.name = name;
        this.theType = Type.findByName(type);
    }

    public Field (String name, Type theType) {
        this.name = name;
        this.theType = theType;
    }

    public Type getTheType() {
        return theType;
    }

    public void setTheType(Type theType) {
        this.theType = theType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String toString () {
        return (theType.toString() + " " + name);
    }

}