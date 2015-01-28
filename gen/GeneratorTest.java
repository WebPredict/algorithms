package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/16/14
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeneratorTest {

    public static void simpleTest () throws Exception {

        App app = new App();
        app.setTechnologyStack(TechnologyStack.rails);
        app.setRootDir("C:/tmp");
        app.setName("testing");

        AppConfig appConfig = new AppConfig();
        appConfig.setColor1("blue");
        appConfig.setColor2("white");
        appConfig.setColor3("orange");
        appConfig.setSearch(true);

        Model item = Model.parseModel("item: name MAX(255)");
        Model person = Model.parseModel("person: name string MIN(1), age int MIN(1) MAX(130), dob date REQUIRED, money float");
        person.hasMany(item);

        app.setAppConfig(appConfig);
        app.addTopLevelModel(person);
        app.setFrontPageListModel(item);
        app.setFrontPageSearchModel(person);

        Generator.createAndGen(app, true);
        /**
         * End result: simple CRUD app where a person can manage a list of possessions
         */
    }

    public static void main (String [] args) throws Exception {
    	boolean windows = true;
        //salesTest(windows);
        //interviewSite(windows);

        exampleWithAllTypes(windows);
    }

    public static void salesTest (boolean windows) throws Exception {

        App app = new App();
        app.setWindows(windows);
        app.setTechnologyStack(TechnologyStack.rails);
        app.setRootDir(windows ? "C:/Users/jsanchez/Downloads/apps" : "/Users/jeffreysanchez/rails_projects/generated");
        app.setName("sales");
        app.setTagLine("Sell Sell Sell");
        app.addPlaceholderPages(new String[] {"about", "help", "news", "contact"});
        app.addStaticMenuItems(new String[] {"about", "news", "help"});

        AppConfig appConfig = new AppConfig();
        appConfig.setColor1("blue");
        appConfig.setColor2("white");
        appConfig.setColor3("green");
        appConfig.setSearch(true);
        appConfig.setNeedsAuth(true);

        Model [] models = Model.parseModels(new String[] {
                "user: username string, email, password, premium boolean, avatar image, has_many contact, has_one company, has_many opportunity",
                "company: name, address, phone, billing_address address, mailing_address address, description string, has_many contact",
                "opportunity: name, address, phone, has_one company, website url, value currency, case_number integer, description long_string, priority fixed_list(low|medium|high)",
                "contact: first_name, last_name, photo list(image), email short_string REQUIRED, home phone, cell phone, sex fixed_list(male|female), contact_type fixed_list(personal|sales|presales)"
        });

        app.setModels(models);
        app.setAppConfig(appConfig);
        app.setTopLevelModels("user", "contact");
        app.setFrontPageListModel(models [1]);   // TODO more realistic example of good front page list content

        Generator.createAndGen(app, true);
        /**
         * End result: simple CRUD app with the following pages:
         * splash intro page with login / signup and footer with about us, etc. pages
         * sign up page
         * log in page
         * logged in page goes to: current user's contacts list, with edit/view/delete options, as well as create new
         * contact details page shows company link / details?
         *
         * Menu options should include sections for contacts and companies? How to configure?
         * Layout is???
         * What's in sidebar?
         */
    }

    public static App initialApp (String name, String tagline, boolean windows) throws Exception {

        App app = new App();
        app.setWindows(windows);
        app.setTechnologyStack(TechnologyStack.rails);
        app.setRootDir(windows ? "C:/Users/jsanchez/Downloads/apps" : "/Users/jeffreysanchez/rails_projects/generated");
        app.setName(name);
        app.setTagLine(tagline);
        //app.addPlaceholderPages(new String[] {"about", "help"});
        //app.addStaticMenuItems(new String[] {"about"});

        AppConfig appConfig = app.getAppConfig();
        appConfig.setColor1("blue");
        appConfig.setColor2("white");
        appConfig.setColor3("green");
        //appConfig.setSearch(true);
        //appConfig.setNeedsAuth(true);
        return (app);
    }

    public static void flylineSite () throws Exception {
        App app = initialApp("flyline", "Activity in the New England Paragliding Community", true);
        Model postModel = Model.parseModel("post: name required, email required, content long_string required, site fixed_list(mt_tom|plymouth|mt_washington|welfleet)");

        app.setModels(new Model[] {postModel});
        app.getAppConfig().setColor1("blue");
        app.getAppConfig().setColor2("white");
        app.setJumbotronImageUrl("http://flickr.com/something/MtTom.jpg");

        //app.setTopLevelModels("post"); -- should be unnecessary if there's only 1 model
        app.setFrontPageListModel(postModel);
        Generator.createAndGen(app, true);
    }

    public static void todoSite () throws Exception {
        App app = initialApp("todo", "Simple TODO List", true);
        Model [] models = Model.parseModels(new String[] {
                "user: username string, email, password, premium boolean, avatar image, has_many todo",
                "todo: name required, details long_string, priority fixed_list(low|medium|high)"});
        app.setSearch(true);
        app.setNeedsAuth(true);

        app.setModels(models);
        app.getAppConfig().setColor1("blue");
        app.getAppConfig().setColor2("white");
        app.setJumbotronImage("TODOBG.jpg");  // TODO: support a list of stockphoto images like "office" or "high-tech" or "mountains" or something

        app.setTopLevelModels("todo");
        //app.setFrontPageListModel(models [1]);
        Generator.createAndGen(app, true);
    }

    public static void interviewSite (boolean windows) throws Exception {
        App app = initialApp("TechReviewNow", "The Interview Q&A Repository", windows);
        app.setGenerateUpgrades(false);
        // TODO: need to have a way to indicate when collections are readonly
        Model [] models = Model.parseModels(new String[] {
                "user: username required, email required, password, premium boolean, has_many tests, has_many questions", // TODO fix, has_many test.results", // means pull it in for display
                "test: name required, description long_string, has_many questions, difficulty fixed_list(low|medium|high), has_many results",
                "employer: name required, description long_string, has_many users, owns_many tests",
                "result: has_one test, has_one user, score computed(float)", // means generate display but don't store it
                "question: name required, description long_string, answer long_strong, code_snippet code, difficulty fixed_list(low|medium|high)"});

        app.setSearch(true);
        app.setNeedsAuth(true);

        app.setModels(models);
        app.getAppConfig().setColor1("blue");
        app.getAppConfig().setColor2("white");
        app.setJumbotronImageAsStockphoto("office");
        app.addPlaceholderPages(new String[] {"about", "help", "news", "contact"});
        app.addStaticMenuItems(new String[] {"about", "news", "help"});

        app.setTopLevelModels("test", "question");
        app.setFrontPageListModel(models [1]);
        Generator.createAndGen(app, true);
    }

    public static void exampleWithAllTypes (boolean windows) throws Exception {


        /**
         *  public static final Type LONG_STRING = new Type("long_string");
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

         public static final Type COMPUTED = new Type("co
         */

        App app = initialApp("TestSiteAllTypes", "A Test Site With All Complex Types In Use", windows);
        app.setGenerateUpgrades(true);
        // TODO: need to have a way to indicate when collections are readonly
        Model [] models = Model.parseModels(new String[] {
                "user: dob date, work_hours range(time), username required, email required, password, tagline string, employed duration, premium boolean, " +
                        "has_many items, has_one home, owns_many blogs, avatar image, phone, address, website url, gender fixed_list(male|female), likes set(string)",
                "item: name required, description long_string, value currency, file file",
                "home: name required, address, age int, acreage float, home_phone phone",
                "blog: name required, video video, code_area code, theme list"});

        app.setSearch(true);
        app.setNeedsAuth(true);

        app.setModels(models);
        app.setColorScheme(ColorScheme.findByName("black-red-blue"));
        app.setJumbotronImageAsStockphoto("high-tech");
        app.addPlaceholderPages(new String[] {"about", "help", "news", "contact"});
        app.addStaticMenuItems(new String[] {"about", "news", "help"});

        app.setTopLevelModels("user", "blog");
        app.setFrontPageListModel(models [3]);
        Generator.createAndGen(app, true);
    }
}
