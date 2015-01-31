package gen.rails;

import alg.io.FileUtils;
import alg.strings.StringUtils;
import alg.words.WordUtils;
import gen.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/31/15
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class ModelsGen extends RailsGenBase {

    public ModelsGen (App app) {
        super(app);
    }

    public void generateModels () throws Exception {
        ArrayList<Model> models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                String name = model.getName();
                String              capName = model.getCapName();
                ArrayList<Field>    fields = model.getFields();
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuilder buf = new StringBuilder();
                StringUtils.addLine(buf, "class " + capName + " < ActiveRecord::Base");
                String attrs = "attr_accessible ";

                ArrayList<Field> computedFields = new ArrayList<Field>();

                if (fields != null) {
                    for (int i = 0; i < fields.size(); i++) {
                        Field f = fields.get(i);
                        attrs += ":" + f.getName();

                        if (f.isComputed())
                            computedFields.add(f);

                        if (f.getTheType().equals(Type.SET_ONE_OR_MORE)) {
                            tabbed(buf, "serialize :" + f.getName() + ", Array");
                        }
                        if (i < fields.size() - 1) {
                            attrs += ", ";
                        }
                    }
                }

                if (rels != null) {
                    // Need to do the _ids
                    for (Rel rel : rels) {
                        if (rel.getRelType().equals(RelType.MANY_TO_ONE)) {
                            attrs += ", :" + rel.getModel().getName() + "_id";
                        }
                        else if (rel.getRelType().equals(RelType.ONE_TO_MANY)) {
                            attrs += ", :" + rel.getModel().getName() + "_ids";
                        }
                    }
                }

                if (model.isSecure()) {
                    tabbed(buf, "attr_accessor :remember_token");
                    tabbed(buf, "has_secure_password");
                }

                if (rels != null) {
                    for (int i = 0; i < rels.size(); i++) {
                        Rel rel = rels.get(i);

                        RelType relType = rel.getRelType();
                        Model   relModel = rel.getModel();
                        if (relType.equals(RelType.ONE_TO_MANY)) {
                            String  toAdd =  "has_many :" + relModel.getPluralName();
                            if (rel.isDependent())
                                toAdd += ", dependent: :destroy";

                            StringUtils.addTabbedLine(buf, toAdd);
                        }
                        else if (relType.equals(RelType.MANY_TO_ONE)) {
                            tabbed(buf, "belongs_to :" + relModel.getName());
                        }
                        else if (relType.equals(RelType.ONE_TO_ONE)) {
                            tabbed(buf, "has_one :" + relModel.getName());
                        }
                        else if (relType.equals(RelType.MANY_TO_MANY)) {
                            String  toAdd;
                            if (rel.getThrough() != null)
                                toAdd =  "has_many :" + relModel.getPluralName() + ", through: " + rel.getThrough().getPluralName();
                            else
                                toAdd = "has_and_belongs_to_many :" + relModel.getPluralName();
                            tabbed(buf, toAdd);
                        }
                    }
                }
                if (model.hasImages()) {
                    List<Field> imageFields = model.getImageFields();

                    for (Field imageField : imageFields) {
                        // TODO: this needs to change for a model with a set of images as opposed to just one:
                        tabbed(buf, "mount_uploader :" + imageField.getName() + ", " + WordUtils.capitalize(imageField.getName()) + "Uploader");

                        String imgGenCmd = getRailsCommand() + " generate uploader " + imageField.getName();
                        // generate uploader

                        // TODO: is this working?
                        //runCommandInApp(imgGenCmd);
                    }
                }

                StringUtils.addLineBreak(buf);

                if (model.hasEmail ()) {
                    tabbed(buf, "before_save { self.email = email.downcase }");
                }

                if (fields != null) {
                    if (model.hasEmail()) {
                        tabbed(buf, "VALID_EMAIL_REGEX = /\\A[\\w+\\-.]+@[a-z\\d\\-.]+\\.[a-z]+\\z/i");
                    }
                    if (model.hasURL()) {
                        tabbed(buf, "VALID_URL_REGEX = /\\A[\\w+\\-.]+[a-z\\d\\-.]+\\.[a-z]+\\z/i");
                    }
                    for (int i = 0; i < fields.size(); i++) {
                        Field f = fields.get(i);
                        List<Validation> validations = f.getValidations();
                        if (validations != null) {
                            for (Validation validation : validations) {
                                ValidationType validationType = validation.getValidationType();
                                switch (validationType) {
                                    case NOT_NULL:
                                        tabbed(buf, "validates :" + f.getName() + ", presence: true");
                                        break;
                                    case MIN_LENGTH:
                                        tabbed(buf, "validates :" + f.getName() + ", length: { minimum: " + validation.getDetailsAsInt().toString() + " }");
                                        break;
                                    case MAX_LENGTH:
                                        tabbed(buf, "validates :" + f.getName() + ", length: { maximum: " + validation.getDetailsAsInt().toString() + " }");
                                        break;
                                    case EMAIL:
                                        tabbed(buf, "validates :" + f.getName() + ", format: { with: VALID_EMAIL_REGEX }");
                                        break;
                                    case URL:
                                        tabbed(buf, "validates :" + f.getName() + ", format: { with: VALID_URL_REGEX }");
                                        break;
                                    case UNIQUE:
                                        tabbed(buf, "validates :" + f.getName() + ", uniqueness: { case_sensitive: false }");
                                        break;
                                }
                            }
                        }
                    }

                    if (rels != null) {
                        // Need to do the _ids
                        for (Rel rel : rels) {
                            if (rel.getRelType().equals(RelType.MANY_TO_ONE)) {
                                if (rel.isDependent())
                                    tabbed(buf, "validates :" + rel.getModel().getName() + "_id, presence: true");
                            }
                        }
                    }

                    if (model.isSecure()) {
                        tabbed(buf, "validates :password, presence: true, :if => :should_validate_password?, length: { minimum: 6 }");
                        tabbed(buf, "validates :password_confirmation, presence: true, :if => :should_validate_password?");
                        StringUtils.addLineBreak(buf);

                        tabbed(buf, "def should_validate_password?");
                        tabbed(buf, "new_record?", 2);
                        tabbed(buf, "end");
                        StringUtils.addLineBreak(buf);
                    }
                }

                StringUtils.addLineBreak(buf);

                for (Field f : computedFields) {
                    tabbed(buf, "def compute_" + f.getName());
                    tabbed(buf, "# TODO: fill in custom logic to compute this field!!!", 2);
                    tabbed(buf, "end");
                    StringUtils.addLineBreak(buf);
                }

                if (model.isSecure()) {
                    addMethod(buf, model.getCapName() + ".digest(string)", new String[] {"cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST :",
                            "                 BCrypt::Engine.cost",
                            "                 BCrypt::Password.create(string, cost: cost)"});

                    addMethod(buf, model.getCapName() + ".new_token", new String[] {"SecureRandom.urlsafe_base64"});
                    addMethod(buf, "remember", new String[] {"self.remember_token = " + model.getCapName() + ".new_token", "update_attribute(:remember_digest, User.digest(remember_token))"});
                    addMethod(buf, "authenticated?(remember_token)", new String[] {"return false if remember_digest.nil?",
                            "BCrypt::Password.new(remember_digest).is_password?(remember_token)"});
                    addMethod(buf, "forget", new String[] {"update_attribute(:remember_digest, nil)"});
                }

                StringUtils.addLine(buf, "end");

                FileUtils.write(buf, app.getWebAppDir() + "/app/models/" + capName + ".rb", true);
            }
        }
    }

}
