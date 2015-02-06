package gen;

import alg.words.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Model extends Type {

    private ArrayList<Field> fields = new ArrayList<Field>();
    private ArrayList<Rel> relationships = new ArrayList<Rel>();

    private boolean secure;  // can only be viewed when logged in when true, otherwise publicly viewable?
    private boolean email;
    private boolean URL;
    private boolean dependent;
    private boolean embedded;
    private String  userIndentifierFieldName = "name";
    private Model parentModel;

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isEmbedded() {
        return embedded;
    }

    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }

    public Model getParentModel() {
        return parentModel;
    }

    public void setParentModel(Model parentModel) {
        this.parentModel = parentModel;
    }

    public String getUserIndentifierFieldName() {
        return userIndentifierFieldName;
    }

    public void setUserIndentifierFieldName(String userIndentifierFieldName) {
        this.userIndentifierFieldName = userIndentifierFieldName;
    }

    public boolean isDependent() {
        return dependent;
    }

    public void setDependent(boolean dependent) {
        this.dependent = dependent;
    }

    private ArrayList<Field> imageFields = new ArrayList<Field>();

    public ArrayList<Field> getImageFields () {
        return (imageFields);
    }

    public boolean hasURL() {
        return URL;
    }

    public void setURL(boolean URL) {
        this.URL = URL;
    }

    public boolean hasEmail () {
        return (email);
    }

    public void setHasEmail (boolean email) {
        this.email = email;
    }

    public boolean hasImages() {
        return imageFields.size() > 0;
    }

    public String   getPluralName() {
        return WordUtils.pluralize(name);
    }

    public List<String> doPreprocessing (App app) {

        // TODO: set MANY_TO_ONE from ONE_TO_MANYs

        boolean sawPWConf = false;
        boolean sawDisabled = false;
        boolean sawAdmin = false;
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            String fieldName = f.getName();
            if (fieldName.equals("username"))
                userIndentifierFieldName = fieldName;
            else if (fieldName.equals("name"))
                userIndentifierFieldName = fieldName;
            // TODO: improve this

            if (fieldName.equals("password")) {
                secure = true;
            }
            else if (f.getTheType().getName().equals("image")) {
                imageFields.add(f);
                app.setHasImages(true);
            }
            else if (fieldName.equals("disabled")) {
                sawDisabled = true;
            }
            else if (fieldName.equals("admin")) {
                sawAdmin = true;
            }
            else if (fieldName.equals("email")) {
                email = true;
            }
            else if (fieldName.equals("password_confirmation")) {
                sawPWConf = true;
            }
            else if (f.getTheType().getName().equals("address")) {
                app.setNeedsAddressModel(true);
            }
        }
        if (secure && !sawPWConf) {
             fields.add(new Field("password_confirmation", Type.PASSWORD));
        }
        if (secure && !sawAdmin) {
            fields.add(new Field("admin", Type.BOOLEAN, false, true));
        }
        if (!sawDisabled) {
            fields.add(new Field("disabled", Type.BOOLEAN, false, true));
        }

        if (app.getAppConfig().isIncludeCreatedUpdatedBy()) {
            fields.add(new Field("created_by", Type.SHORT_STRING, true, false));
            fields.add(new Field("created_at", Type.DATETIME, true, false));
            fields.add(new Field("updated_by", Type.SHORT_STRING, true, false));
            fields.add(new Field("updated_at", Type.DATETIME, true, false));
        }

        // TODO: make sure all references to models are existing models
        return (null);
    }

    public List<String> resolveReferences (App app) {

        // TODO: the relationships need to become fields
        ArrayList<String> errors = new ArrayList<String>();
        if (relationships != null) {
            for (Rel rel : relationships) {
                if (rel.getModel() == null) {
                    Model found = app.getNameToModelMap().get(rel.getModelName());
                    if (found == null)
                        errors.add("Could not resolve model by name: " + rel.getModelName());
                    else {
                        rel.setModel(found);
                        if (rel.getRelType().equals(RelType.ONE_TO_MANY)) {
                            found.addRel(this, RelType.MANY_TO_ONE);
                            found.setParentModel(this);
                        }
                        else if (rel.getRelType().equals(RelType.ONE_TO_ONE)) {
                            found.addRel(this, RelType.ONE_TO_ONE);
                        }
                    }
                }
            }
        }
        return (errors);
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public ArrayList<Rel> getRelationships() {
        return relationships;
    }

    public void setRelationships(ArrayList<Rel> relationships) {
        this.relationships = relationships;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public Model addField (Field f) {
        fields.add(f);
        return (this);
    }

    public Model addRel (Rel r) {
        relationships.add(r);
        return (this);
    }

    public void addRel (Model m, RelType relType) {
        for (Rel rel : relationships) {
            if (rel.getModel() != null && rel.getModel().getName().equals(m.getName()))
                return; // already there
        }
        relationships.add(new Rel(relType, m));
    }

    public void hasMany (Model m) {
        relationships.add(new Rel(RelType.ONE_TO_MANY, m));
    }

    public void belongsTo (Model m) {
        relationships.add(new Rel(RelType.MANY_TO_ONE, m));
        // TODO: the other side?
        m.hasMany(this);
    }

    public static Model [] parseModels (String [] modelText) {
         Model []   models = new Model[modelText.length];

        for (int i = 0; i < modelText.length; i++) {
            models [i] = parseModel(modelText[i]);
        }
        return (models);
    }

    public static Model parseModel (String modelText) {
        Model ret = new Model();
        String [] parts = modelText.split(":");
        ret.setName(parts [0].trim());
        String rest = parts [1].trim();
        StringTokenizer tok = new StringTokenizer(rest, ",");
        while (tok.hasMoreTokens()) {
            String      next = tok.nextToken().trim();
            String []   details = next.split(" ");
            Field       f = null;
            if (details.length == 1) {
                Type theType = getType(details[0]);
                //if (theType.getName().equals(details [0]))
                //    theType = Type.SHORT_STRING;
                if (theType == null)
                    theType = Type.SHORT_STRING; // default
                f = new Field(details [0], theType);
            }
            else if (details.length > 1) {
                if (details [0].equals("has_many")) {
                    Rel r = new Rel(RelType.ONE_TO_MANY, WordUtils.depluralize(details[1]));
                    ret.addRel(r);
                }
                else if (details [0].equals("owns_many")) {
                    Rel r = new Rel(RelType.ONE_TO_MANY, WordUtils.depluralize(details[1]));
                    r.setDependent(true);
                    ret.addRel(r);
                }
                else if (details [0].equals("has_one")) {
                    Rel r = new Rel(RelType.ONE_TO_ONE, details[1]);
                    ret.addRel(r);
                }
                else {
                    Validation v = Validation.parseValidation(details[1]);
                    if (v == null)
                        f = new Field(details [0], getType(details[1]));   // assume it's a type name
                    else {
                        f = new Field(details [0]);
                        f.addValidation(v);
                    }
                }
            }

            if (f != null) {
                if (details.length > 2) {
                    String validation = details [2];
                    f.addValidation(Validation.parseValidation(validation));
                }

                ret.addField(f);
            }

        }
        return (ret);
    }

    // TODO this needs to be filled out
    private static Type getType (String typeName) {
        return (Type.findByName(typeName));

//        if (typeName.equals("email"))
//            return (Type.EMAIL);
//        else if (typeName.equals("phone"))
//            return (Type.PHONE);
//        else if (typeName.equals("password"))
//            return (Type.PASSWORD);
//        else if (typeName.equals("url"))
//            return (Type.URL);
//        else if (typeName.equals("address"))
//            return (Type.ADDRESS);
//        else
//            return (Type.SHORT_STRING);
    }

    public String toString () {
        return (name + (subtype == null ? "" : "(" + subtype + ")"));
    }
}
