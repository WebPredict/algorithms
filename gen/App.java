package gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class App {

    private AppConfig appConfig;
    private TechnologyStack technologyStack;
    private String rootDir;
    private boolean generateCRUDByDefault = true;
    private String name;
    private ArrayList<Model> models = new ArrayList<Model>();
    private ArrayList<Model> topLevelModels = new ArrayList<Model>();
    private String version;
    private String description;
    private String jumbotronText;
    private String jumbotronImageUrl;
    private HashMap<String, Model> nameToModelMap = new HashMap<String, Model>();
    private Model userModel;

    private ArrayList<Blurb>    newsBlurbs = new ArrayList<Blurb>();

    public ArrayList<Blurb> getNewsBlurbs() {
        return newsBlurbs;
    }

    public void setNewsBlurbs(ArrayList<Blurb> newsBlurbs) {
        this.newsBlurbs = newsBlurbs;
    }

    public List<String> doPreprocessing () {
        ArrayList<String> errors = new ArrayList<String>();

        for (Model model : models) {
            nameToModelMap.put(model.getName(), model);
            if (model.getName().equals("user")) {    // TODO don't hardcode this
                userModel = model;
            }
        }

        for (Model model : models) {
            List<String> modelErrors = model.doPreprocessing(this);
            if (modelErrors != null)
                errors.addAll(modelErrors);
        }
        return (errors);
    }

    public Model    getUserModel () {
        return (userModel);
    }

    public HashMap<String, Model>   getNameToModelMap () {
        return (nameToModelMap);
    }

    public void addTopLevelModel (Model m) {
        topLevelModels.add(m);
        models.add(m);
    }

    public void addNewsBlurb (Blurb blurb) {
        newsBlurbs.add(blurb);
    }

    public String getJumbotronText() {
        return jumbotronText;
    }

    public void setJumbotronText(String jumbotronText) {
        this.jumbotronText = jumbotronText;
    }

    public String getJumbotronImageUrl() {
        return jumbotronImageUrl;
    }

    public void setJumbotronImageUrl(String jumbotronImageUrl) {
        this.jumbotronImageUrl = jumbotronImageUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public App addModel(Model model) {
        models.add(model);
        return (this);
    }

    public ArrayList<Model> getModels() {
        return models;
    }

    public void setModels(ArrayList<Model> models) {
        this.models = models;
    }

    public boolean isGenerateCRUDByDefault() {
        return generateCRUDByDefault;
    }

    public void setGenerateCRUDByDefault(boolean generateCRUDByDefault) {
        this.generateCRUDByDefault = generateCRUDByDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRootDir () {
        return (rootDir);
    }

    public String getWebAppDir() {
        return rootDir + "/" + name;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public TechnologyStack getTechnologyStack() {
        return technologyStack;
    }

    public void setTechnologyStack(TechnologyStack technologyStack) {
        this.technologyStack = technologyStack;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
}
