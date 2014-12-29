package gen;

import gen.mean.MeanGen;
import gen.rails.RailsGen;

import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Generator {

    protected App app;

    protected Generator (App app) {
        this.app = app;
    }

    public static String runCommand (String startingDir, String... command) throws Exception {

        Process p = Runtime.getRuntime().exec(command, null, new File(startingDir));

        InputStream inputStream = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = br.readLine()) != null)
            System.out.println(line);

        br.close();
        return (String.valueOf(p.exitValue()));

    }

//    public static String runCommand (String startingDir, String... command) throws Exception {
//        ProcessBuilder  pb = new ProcessBuilder(command);
//        pb.directory(new File(startingDir));
//        Process process = pb.start();
//
//        InputStream is = process.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//        StringBuilder buf = new StringBuilder();
//
//        String line;
//        while ((line = br.readLine()) != null) {
//            buf.append(line);
//            buf.append("\\n");
//        }
//        return (buf.toString());
//    }

    public static void main (String [] args) throws Exception {

        /**
         *
         */
    }

    public static void createAndGen(App app, boolean clean) throws Exception {

        AppConfig appConfig = app.getAppConfig();
        ArrayList<Model> models = app.getModels();
        String name = app.getName();

        TechnologyStack technologyStack = app.getTechnologyStack();

        switch (technologyStack) {
            case rails:
                new RailsGen(app).generate();
                break;

            case mean:
                new MeanGen(app).generate();
                break;
        }

    }

    public abstract void generate () throws Exception;

}
