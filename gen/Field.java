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
    private boolean readOnly;
    private boolean adminOnly;

    public boolean isAuditField () {
        return (name.equals("created_at") || name.equals("updated_at") || name.equals("created_by") || name.equals("updated_by"));
    }

    public boolean isComputed() {
        return theType.isComputed();
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isAdminOnly() {
        return adminOnly;
    }

    public void setAdminOnly(boolean adminOnly) {
        this.adminOnly = adminOnly;
    }

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

    public Field (String name) {
        this(name, Type.SHORT_STRING.getName());
    }

    public Field (String name, String type) {
        this.name = name;
        this.theType = Type.findByName(type);
    }

    public Field (String name, Type theType) {
        this.name = name;
        this.theType = theType;

        if (theType.equals(Type.EMAIL))
            addValidation(new Validation(ValidationType.EMAIL));
        else if (theType.equals(Type.PHONE))
            addValidation(new Validation(ValidationType.PHONE));
        else if (theType.equals(Type.DATE))
            addValidation(new Validation(ValidationType.DATE));
        else if (theType.equals(Type.TIME))
            addValidation(new Validation(ValidationType.TIME));
        else if (theType.equals(Type.INT) || theType.equals(Type.FLOAT))
            addValidation(new Validation(ValidationType.NUMERIC));

    }

    public Field (String name, Type theType, boolean readOnly, boolean adminOnly) {
        this.name = name;
        this.theType = theType;
        this.readOnly = readOnly;
        this.adminOnly = adminOnly;
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
