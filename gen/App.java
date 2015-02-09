package gen;

import alg.words.WordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class App {

    private AppConfig appConfig = new AppConfig();
    private TechnologyStack technologyStack;
    private String rootDir;
    private boolean generateCRUDByDefault = true;
    private boolean needsAddressModel = false;
    private boolean needsUSAddressModel = false;
    private boolean needsLocationModel = false;
    private Model addressType;
    private Model locationType;
    private Model usAddressType;
    private String name;
    private ArrayList<Model> models = new ArrayList<Model>();
    private ArrayList<Model> topLevelModels = new ArrayList<Model>();
    private String version;
    private String description;
    private String jumbotronText;
    private String jumbotronImageUrl;
    private String jumbotronImage; // should be put in assets
    private HashMap<String, Model> nameToModelMap = new HashMap<String, Model>();
    private Model userModel;
    private String  tagLine;
    private Model   frontPageSearchModel;
    private Model   frontPageListModel;
    private String  title;
    private SidebarContent  leftSidebarContent;
    private SidebarContent  rightSidebarContent;
    private ArrayList<Blurb>    newsBlurbs = new ArrayList<Blurb>();
    private boolean generatePlaceholderText; // lorem ipsum for empty sections
    private boolean windows;
    private boolean hasImages;
    private List<StaticPage> staticPages = new ArrayList<StaticPage>();
    private List<String> staticMenuItems = new ArrayList<String>();
    private boolean generateUpgrades;
    private boolean fullWidthJumbotron = true;

    private ColorScheme colorScheme;
    private MapColorScheme mapColorScheme;

    public Model getAddressType() {
        return addressType;
    }

    public void setAddressType(Model addressType) {
        this.addressType = addressType;
    }

    public Model getLocationType() {
        return locationType;
    }

    public void setLocationType(Model locationType) {
        this.locationType = locationType;
    }

    public Model getUsAddressType() {
        return usAddressType;
    }

    public void setUsAddressType(Model usAddressType) {
        this.usAddressType = usAddressType;
    }

    public MapColorScheme getMapColorScheme() {
        return mapColorScheme;
    }

    public void setMapColorScheme(MapColorScheme mapColorScheme) {
        this.mapColorScheme = mapColorScheme;
    }

    public boolean isFullWidthJumbotron() {
        return fullWidthJumbotron;
    }

    public void setFullWidthJumbotron(boolean fullWidthJumbotron) {
        this.fullWidthJumbotron = fullWidthJumbotron;
    }

    public ColorScheme getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    public boolean isGenerateUpgrades() {
        return generateUpgrades;
    }

    public void setGenerateUpgrades(boolean generateUpgrades) {
        this.generateUpgrades = generateUpgrades;
    }

    public void addPlaceholderPages (String [] names) {
        for (String name : names) {
            StaticPage sp = new StaticPage();
            sp.setName(name);
            sp.setTitle(WordUtils.capitalize(name));
            sp.setContent("This is the " + WordUtils.capitalize(name) + " that needs to be filled in with some content. " +
                    "This is just a placeholder to see how a bit of text looks.");
            staticPages.add(sp);
        }
    }

    public App () {
        newsBlurbs.add(new Blurb("News Item 1", "This is the first of many news items."));
        newsBlurbs.add(new Blurb("News Item 2", "This is the second of many news items."));
    }

    public ArrayList<Model> getTopLevelModels() {
        return topLevelModels;
    }

    public void setTopLevelModels(ArrayList<Model> topLevelModels) {
        this.topLevelModels = topLevelModels;
    }

    public void setJumbotronImageAsStockphoto (String name) {
        this.jumbotronImageUrl = Stockphoto.find(name).getPhotoName();
    }

    public void addStaticMenuItems (String [] names) {
        for (String name : names) {
            staticMenuItems.add(name);
        }
    }

    public void setSearch (boolean search) {
        appConfig.setSearch(search);
    }

    public void setNeedsAuth (boolean needs) {
        appConfig.setNeedsAuth(needs);
    }

    public String getJumbotronImage() {
        return jumbotronImage;
    }

    public void parseColorScheme (String scheme) {
        String [] maps = scheme.split(",");
        HashMap<String, String> itemsToColorsMap = new HashMap<String, String>();
        for (String map : maps) {
            String [] mapElements = map.split(":");
            itemsToColorsMap.put(mapElements [0].trim(), mapElements[1].trim());
        }
        mapColorScheme = new MapColorScheme(itemsToColorsMap);
    }

    public void setJumbotronImage(String jumbotronImage) {
        this.jumbotronImage = jumbotronImage;
    }

    public List<StaticPage> getStaticPages() {
        return staticPages;
    }

    public void setStaticPages(List<StaticPage> staticPages) {
        this.staticPages = staticPages;
    }

    public List<String> getStaticMenuItems() {
        return staticMenuItems;
    }

    public void setStaticMenuItems(List<String> staticMenuItems) {
        this.staticMenuItems = staticMenuItems;
    }

    public void setHasImages (boolean hasImages) {
        this.hasImages = hasImages;
    }

    public boolean hasImages () {
        return (hasImages);
    }

    public boolean isWindows() {
		return windows;
	}

	public void setWindows(boolean windows) {
		this.windows = windows;
	}

	public boolean isGeneratePlaceholderText() {
        return generatePlaceholderText;
    }

    public void setGeneratePlaceholderText(boolean generatePlaceholderText) {
        this.generatePlaceholderText = generatePlaceholderText;
    }

    public boolean isNeedsUSAddressModel() {
        return needsUSAddressModel;
    }

    public void setNeedsUSAddressModel(boolean needsUSAddressModel) {
        this.needsUSAddressModel = needsUSAddressModel;
    }

    public boolean isNeedsLocationModel() {
        return needsLocationModel;
    }

    public void setNeedsLocationModel(boolean needsLocationModel) {
        this.needsLocationModel = needsLocationModel;
    }

    public boolean isNeedsAddressModel() {
        return needsAddressModel;
    }

    public void setNeedsAddressModel(boolean needsAddressModel) {
        this.needsAddressModel = needsAddressModel;
    }

    public SidebarContent getLeftSidebarContent() {
        return leftSidebarContent;
    }

    public void setLeftSidebarContent(SidebarContent leftSidebarContent) {
        this.leftSidebarContent = leftSidebarContent;
    }

    public SidebarContent getRightSidebarContent() {
        return rightSidebarContent;
    }

    public void setRightSidebarContent(SidebarContent rightSidebarContent) {
        this.rightSidebarContent = rightSidebarContent;
    }

    public String getTitle() {
        return title == null ? name : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public Model getFrontPageSearchModel() {
        return frontPageSearchModel;
    }

    public void setFrontPageSearchModel(Model frontPageSearchModel) {
        this.frontPageSearchModel = frontPageSearchModel;
    }

    public Model getFrontPageListModel() {
        return frontPageListModel;
    }

    public void setFrontPageListModel(Model frontPageListModel) {
        this.frontPageListModel = frontPageListModel;
    }

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

        if (errors.isEmpty()) {
            if (needsAddressModel) {
                Model addressModel = Model.parseModel("address: line1, line2, line3, city, state fixed_list(" + getStateList() + "), zip, country fixed_list(" + getCountryList() + ")");
                //addressModel.setDependent(true);
                addressModel.setEmbedded(true);
                List<String> addressModelErrors =  addressModel.doPreprocessing(this);
                if (addressModelErrors != null)
                    errors.addAll(addressModelErrors);
                setAddressType(addressModel);
                nameToModelMap.put(addressModel.getName(), addressModel);
                models.add(addressModel);
            }
            else if (needsUSAddressModel) {
                Model addressModel = Model.parseModel("address: line1, line2, line3, city, state fixed_list(" + getStateList() + "), zip");
                //addressModel.setDependent(true);
                addressModel.setEmbedded(true);
                List<String> addressModelErrors =  addressModel.doPreprocessing(this);
                if (addressModelErrors != null)
                    errors.addAll(addressModelErrors);
                setUsAddressType(addressModel);
                nameToModelMap.put(addressModel.getName(), addressModel);
                models.add(addressModel);
            }

            if (needsLocationModel) {
                Model locationModel = Model.parseModel("location: name, line1, line2, line3, city, state fixed_list(" + getStateList() + "), zip, country fixed_list(" +
                        getCountryList() + ")");
                //addressModel.setDependent(true);
                locationModel.setEmbedded(true);
                List<String> locationModelErrors =  locationModel.doPreprocessing(this);
                if (locationModelErrors != null)
                    errors.addAll(locationModelErrors);
                setLocationType(locationModel);
                nameToModelMap.put(locationModel.getName(), locationModel);
                models.add(locationModel);
            }

        }

        for (Model model : models) {
            List<String> modelErrors = model.resolveReferences(this);
            if (modelErrors != null)
                errors.addAll(modelErrors);
        }

        return (errors);
    }

    private String getStateList () {
        String list = "Alabama|Alaska|Arizona|Arkansas|California|Colorado|Connecticut|Delaware|DistrictOfColumbia|Florida|Georgia|Hawaii|Idaho|Illinois|Indiana|Iowa|Kansas|Kentucky|Louisiana|Maine|Maryland|Massachusetts|Michigan|Minnesota|Mississippi|Missouri|Montana|Nebraska|Nevada|NewHampshire|NewJersey|NewMexico|NewYork|NorthCarolina|NorthDakota|Ohio|Oklahoma|Oregon|Palau|Pennsylvania|PuertoRico|RhodeIsland|SouthCarolina|SouthDakota|Tennessee|Texas|Utah|Vermont|Virginia|Washington|WestVirginia|Wisconsin|Wyoming";
	return (list);
    }

    private String getCountryList () {
        String list = "Algeria|UnitedStates";
        return (list);
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

    public void setModels (Model... modelList) {
        for (Model model : modelList) {
            models.add(model);
            nameToModelMap.put(model.getName(), model);
        }
    }

    public void setTopLevelModels (String... modelNameList) {
        for (String modelName : modelNameList) {
            Model found = nameToModelMap.get(modelName);
            topLevelModels.add(found);
        }
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

    public String getTopLevelSearchModelName() {
    	return ("search"); // TODO
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
