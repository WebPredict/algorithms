package gen.rails;

import alg.io.FileUtils;
import alg.strings.StringUtils;
import alg.words.WordUtils;
import gen.App;
import gen.Field;
import gen.Model;
import gen.Rel;

import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/31/15
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBGen extends RailsGenBase {

    public DBGen (App app) {
        super(app);
    }

    public void generateUpgrades () throws Exception {
        ArrayList<Model> models = app.getModels();
        if (models != null) {
            for (Model model : models) {
                if (model.isEmbedded())
                    continue;
                String              name = model.getName();
                ArrayList<Field>    fields = model.getFields();
                ArrayList<Rel>      rels = model.getRelationships();

                StringBuilder       buf = new StringBuilder();

                String              upperPluralName = WordUtils.pluralize(WordUtils.capitalize(name));
                String              className = "Create" + upperPluralName;
                StringUtils.addLine(buf, "class " + className + " < ActiveRecord::Migration");

                tabbed(buf, "def change");
                tabbed(buf, "create_table :" + model.getPluralName() + " do |t|", 2);

                if (fields != null) {
                    for (Field field : fields) {
                        if (field.isComputed() || field.getName().equals("created_at") || field.getName().equals("updated_at"))
                            continue;

                        if (field.getTheType() instanceof Model) {
                            Model mType = (Model)field.getTheType();
                            if (mType.isEmbedded()) {

                                ArrayList<Field> typeFields = mType.getFields();
                                for (Field typeField : typeFields) {
                                    tabbed(buf, "t." + getRailsType(typeField.getTheType()) + " :" + mType.getName() + "_" + field.getName(), 3);
                                }
                                continue;
                            }
                        }
                        tabbed(buf, "t." + getRailsType(field.getTheType()) + " :" + field.getName(), 3);
                    }
                }

                if (model.isSecure()) {
                    tabbed(buf, "t.string :remember_digest", 3);
                    tabbed(buf, "t.string :password_digest", 3);
                }

                if (rels != null) {
                    for (Rel rel : rels) {
                        // TODO: do we need to generate belongs_to here instead in some cases?
                        tabbed(buf, "t.integer :" + rel.getModel().getName() + "_id", 3);
                    }
                }
                tabbed(buf, "t.timestamps null: false", 3);
                tabbed(buf, "end", 2);
                tabbed(buf, "end");
                StringUtils.addLine(buf, "end");

                String endFileName = "_create_" + WordUtils.pluralize(name) + ".rb";

                File f = FileUtils.fileEndingIn(app.getWebAppDir() + "/db/migrate/", endFileName);

                if (f != null) {
                    if (FileUtils.textContentSame(buf.toString(), f))
                        continue;
                    else
                        f.delete();
                }

                String fileName = String.valueOf(System.nanoTime()) + endFileName;
                FileUtils.write(buf, app.getWebAppDir() + "/db/migrate/" + fileName, true);
            }
        }

        String 	railsCmd = app.isWindows() ? "C:/RailsInstaller/Ruby2.1.0/bin/bundle.bat" : "bundle";

        //runCommandInApp(railsCmd + " exec rake db:migrate");
    }

}
