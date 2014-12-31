package gen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    public List<String> doPreprocessing (App app) {

        // TODO: set MANY_TO_ONE from ONE_TO_MANYs

        boolean sawPWConf = false;
        boolean sawDisabled = false;
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            String fieldName = f.getName();
            if (fieldName.equals("password")) {
                secure = true;
            }
            else if (f.getTheType().getName().equals("image")) {
                imageFields.add(f);
            }
            else if (fieldName.equals("disabled")) {
                sawDisabled = true;
            }
            else if (fieldName.equals("password_confirmation")) {
                sawPWConf = true;
            }
            else if (fieldName.equals("address")) {
                app.setNeedsAddressModel(true);
            }
        }
        if (secure && !sawPWConf) {
             fields.add(new Field("password_confirmation", Type.SHORT_STRING));
        }
        if (!sawDisabled) {
            fields.add(new Field("disabled", Type.BOOLEAN));
        }

        if (app.getAppConfig().isIncludeCreatedUpdatedBy()) {
            fields.add(new Field("createdBy", Type.SHORT_STRING));
            fields.add(new Field("created", Type.DATETIME));
            fields.add(new Field("updatedBy", Type.SHORT_STRING));
            fields.add(new Field("updated", Type.DATETIME));
        }

        // TODO: make sure all references to models are existing models
        return (null);
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
            String next = tok.nextToken().trim();
            String [] details = next.split(" ");
            Field f;
            if (details.length == 1) {
                if (details [0].equals("email"))
                    f = new Field(details [0], Type.EMAIL);
                else if (details [0].equals("phone"))
                    f = new Field(details [0], Type.PHONE);
                else if (details [0].equals("url"))
                    f = new Field(details [0], Type.URL);
                else if (details [0].equals("address"))
                    f = new Field(details [0], Type.ADDRESS);
                else
                    f = new Field(details [0], Type.STRING);
            }
            else {
                f = new Field(details [0], details [1]);
            }
            if (details.length > 2) {
                String validation = details [2];
                f.addValidation(Validation.parseValidation(validation));
            }

            ret.addField(f);

        }
        return (ret);
    }

    public String toString () {
        return (name + (subtype == null ? "" : "(" + subtype + ")"));
    }
}
