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


        Generator.createAndGen(app);
        /**
         * End result: simple CRUD app where a person can manage a list of possessions
         */
    }

    public static void main (String [] args) throws Exception {
        salesTest();
    }

    public static void salesTest () throws Exception {

        App app = new App();
        app.setTechnologyStack(TechnologyStack.rails);
        app.setRootDir("C:/Users/jsanchez/Downloads/apps");
        app.setName("sales");

        AppConfig appConfig = new AppConfig();
        appConfig.setColor1("blue");
        appConfig.setColor2("white");
        appConfig.setColor3("green");
        appConfig.setSearch(true);
        appConfig.setNeedsAuth(true);

        Model [] models = Model.parseModels(new String[] {
                "user: username string, email, password, premium boolean, avatar image",
                "company: name, address, phone, billingAddress address",
                "contact: firstName, lastName, photo list(image), email short_string REQUIRED, home phone, cell phone, sex fixed_list(male|female), contactType fixed_list(personal|sales|presales)"
        });

        Model user = models [0];
        Model company = models [1];
        Model contact = models [2];

        user.hasMany(contact);
        contact.belongsTo(company);

        app.setAppConfig(appConfig);
        app.addTopLevelModel(user);
        app.addTopLevelModel(contact);
        app.addModel(company);

        Generator.createAndGen(app);
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
}
