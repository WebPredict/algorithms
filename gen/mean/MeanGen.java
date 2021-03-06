package gen.mean;

import alg.io.FileUtils;
import alg.strings.StringUtils;
import gen.App;
import gen.Generator;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MeanGen extends Generator {

    public MeanGen (App app) {
        super(app);
    }

    @Override
    public void generate() throws Exception {
        /**
         * Generate:
         * server.js
         * controllers
         * services
         * views
         * package.json
         * relevant directories
         */

//        ArrayList<Model> models = app.getModels();
//        AppConfig overall = app.getAppConfig();
//        String title = app.getName();

        String rootDir = app.getWebAppDir();
        generatePackageJson(rootDir);

        generateClient(rootDir);

        generateServer(rootDir);

    }

    public void generatePackageJson (String rootDir) throws Exception {
        StringBuilder buf = new StringBuilder();

        buf.append("{");
        StringUtils.addedQuoted(buf, "name", app.getName());
        StringUtils.addedQuoted(buf, "version", app.getVersion());
        StringUtils.addedQuoted(buf, "description", app.getDescription());
        StringUtils.addedQuoted(buf, "main", "server.js");
        StringUtils.addedQuoted(buf, "author", "generator");
        StringUtils.addedQuoted(buf, "dependencies", "{");
        StringUtils.addedQuoted(buf, "express", "~4.7.2");
        StringUtils.addedQuoted(buf, "mongoose", "~3.6.2");
        StringUtils.addedQuoted(buf, "body-parser", "~1.5.2");
        buf.append("}");
        buf.append("}");

        FileUtils.write(buf, rootDir + "/public/package.json", true);
    }

    public void generateClient (String rootDir) throws Exception {
        FileUtils.mkdir(rootDir, "public");
        FileUtils.mkdirs(rootDir + "/public", new String[]{"assets", "view", "services", "controllers", "config"});

        generateIndexFile(rootDir);

    }

    private void generateIndexFile(String rootDir) throws Exception {

    }

    public void generateServer (String rootDir) throws Exception {
        FileUtils.mkdir(rootDir, "public");
        FileUtils.mkdirs(rootDir + "/public", new String[]{"assets", "view", "services", "controllers", "config"});

    }
}
