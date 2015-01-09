package alg.io;

import alg.misc.InterestingAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/16/14
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {

    public static List<String> getLines (String file) throws Exception {
        ArrayList<String> lines = new ArrayList<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        finally {
            if (br != null)
                br.close();
        }
        return (lines);
    }

    public static List<String> getLinesCreateIfEmpty (String file) throws Exception {
        File f = new File(file);
        if (!f.exists()) {
            touch(file);
        }
        return (getLines(file));
    }

    public static void touch (String file) throws Exception {
        putLines(new ArrayList<String>(), file);
    }

    public static void putLines (List<String> lines, String file) throws Exception {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new File(file));

            for (int i = 0; i < lines.size(); i++) {
                pw.println(lines.get(i));
            }
            pw.flush();
        }
        finally {
            if (pw != null)
                pw.close();
        }
    }

    public static boolean insertAfterInFile (String filePath, String [] markerLines, String [] additionalLines, boolean onlyFirstInstance,
                                       boolean fromTheStart) throws Exception {

        List<String> fileLines = getLines(filePath);

        boolean didIt = insertAfter(fileLines, markerLines, additionalLines, onlyFirstInstance, fromTheStart);

        if (didIt)
            putLines(fileLines, filePath);

        return (didIt);
    }

    public static boolean insertAtInFile (String filePath, int lineNum, String [] additionalLines,
                                             boolean fromTheStart) throws Exception {

        List<String> fileLines = getLines(filePath);

        for (int i = 0; i < additionalLines.length; i++) {
            fileLines.add(lineNum + i - 1, additionalLines [i]);
        }

        putLines(fileLines, filePath);

        return (true);
    }

    /**
     *
     * @param fileLines
     * @param markerLines
     * @param additionalLines
     * @param onlyFirstInstance
     * @param fromTheStart
     * @return
     * @throws Exception
     */
    @InterestingAlgorithm
    public static boolean insertAfter (List<String> fileLines, String [] markerLines, String [] additionalLines, boolean onlyFirstInstance,
                                       boolean fromTheStart) throws Exception {

        boolean matched = false;

        if (markerLines.length <= fileLines.size()) {

            for (int i = 0; i < fileLines.size(); i++) {

                matched = true;
                for (int j = 0; j < markerLines.length; j++) {
                    if (i + j >= fileLines.size() || !markerLines [j].equals(fileLines.get(i))) {
                        matched = false;
                        break;
                    }
                }
                if (matched) {
                    for (int j = 0; j < additionalLines.length; j++) {
                        fileLines.add(i + markerLines.length, additionalLines [j]);
                    }
                    i += additionalLines.length + markerLines.length;
                    if (onlyFirstInstance)
                        break;
                }
            }
        }
        return (matched);
    }

    public static void insertAfterOrAtEnd (List<String> fileLines, String [] markerLines, String [] additionalLines, boolean onlyFirstInstance,
                                       boolean fromTheStart) throws Exception {
        boolean     didIt = insertAfter(fileLines, markerLines, additionalLines, onlyFirstInstance, fromTheStart);
        if (!didIt) {
            for (String additionalLine : additionalLines) {
                fileLines.add(additionalLine);
            }
        }

    }

    public static boolean insertBeforeInFile (String filePath, String [] markerLines, String [] additionalLines, boolean onlyFirstInstance,
                                             boolean fromTheStart) throws Exception {

        List<String> fileLines = getLines(filePath);

        boolean didIt = insertBefore(fileLines, markerLines, additionalLines, onlyFirstInstance, fromTheStart);

        if (didIt)
            putLines(fileLines, filePath);

        return (didIt);
    }

    /**
     *
     * @param fileLines
     * @param markerLines
     * @param additionalLines
     * @param onlyFirstInstance
     * @param fromTheStart
     * @return
     * @throws Exception
     */
    @InterestingAlgorithm
    public static boolean insertBefore (List<String> fileLines, String [] markerLines, String [] additionalLines, boolean onlyFirstInstance,
                                       boolean fromTheStart) throws Exception {
        boolean matched = false;

        if (markerLines.length <= fileLines.size()) {

            for (int i = 0; i < fileLines.size(); i++) {

                matched = true;
                for (int j = 0; j < markerLines.length; j++) {
                    if (i + j >= fileLines.size() || !markerLines [j].equals(fileLines.get(i))) {
                        matched = false;
                        break;
                    }
                }
                if (matched) {
                    for (int j = 0; j < additionalLines.length; j++) {
                        fileLines.add(i, additionalLines [j]);
                    }
                    i += additionalLines.length + markerLines.length;
                    if (onlyFirstInstance)
                        break;
                }
            }
        }
        return (matched);
    }

    public static boolean replaceInFile (String filePath, String [] markerLines, String [] additionalLines, boolean onlyFirstInstance,
                                              boolean fromTheStart) throws Exception {

        List<String> fileLines = getLines(filePath);

        boolean didIt = replace(fileLines, markerLines, additionalLines, onlyFirstInstance, fromTheStart);

        if (didIt)
            putLines(fileLines, filePath);

        return (didIt);
    }

    /**
     *
     * @param fileLines
     * @param markerLines
     * @param additionalLines
     * @param onlyFirstInstance
     * @param fromTheStart
     * @return
     * @throws Exception
     */
    @InterestingAlgorithm
    public static boolean replace (List<String> fileLines, String [] markerLines, String [] additionalLines, boolean onlyFirstInstance,
                                        boolean fromTheStart) throws Exception {
        boolean matched = false;

        if (markerLines.length <= fileLines.size()) {

            for (int i = 0; i < fileLines.size(); i++) {

                matched = true;
                for (int j = 0; j < markerLines.length; j++) {
                    if (i + j >= fileLines.size() || !markerLines [j].equals(fileLines.get(i))) {
                        matched = false;
                        break;
                    }
                }
                if (matched) {
                    for (int j = 0; j < additionalLines.length; j++) {
                        fileLines.add(i, additionalLines [j]);
                    }
                    for (int j = 0; j < markerLines.length; j++) {
                        fileLines.remove(i + additionalLines.length);
                    }
                    i += additionalLines.length - markerLines.length;
                    if (onlyFirstInstance)
                        break;
                }
            }
        }
        return (matched);
    }

    public static boolean replace (List<String> fileLines, String toReplace, String replaceWith, boolean onlyFirstInstance,
                                        int startSearchIdx, int endSearchIdx) throws Exception {
        return (false);
    }

    public static boolean replace (List<String> fileLines, String toReplace, String replaceWith, boolean onlyFirstInstance,
                                   String startMarkerRange, String endMarkerRange) throws Exception {
        return (false);
    }

    @InterestingAlgorithm
    public static int []    patternOccurances (List<String> fileLines, String [] lookingForLines, String startMarkerRange, String endMarkerRange) throws Exception
    {
         return (null);
    }

    @InterestingAlgorithm
    public static String    formatAsJson (String data, int indent) {
         return (data); // TODO  move somewhere else
    }

    @InterestingAlgorithm
    public static String    formatAsXML (String data, int indent) {
        return (data); // TODO   move somewhere else
    }

    public static boolean   dirExists (String dir) throws Exception {
        File f = new File(dir);

        return (f.exists() && f.isDirectory());
    }

    public static boolean   fileExists (String dir) throws Exception {
        File f = new File(dir);

        return (f.exists());
    }

    public static void mkdir (String dir) throws Exception {
       new File(dir).mkdir();
    }

    public static void mkdir (String root, String dir) throws Exception {
        new File(root.endsWith("/") || dir.startsWith("/") ? root + dir : root + "/" + dir).mkdir();
    }

    public static void mkdirs (String root, String [] dirs) throws Exception {
        if (dirs != null) {
            for (String dir : dirs) {
                mkdir(root, dir);
            }
        }
    }

    public static void      append (String file, String [] lines) throws Exception {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileOutputStream(new File(file), true));

            for (String line : lines) {
                pw.append(line);
                pw.append("\n");
            }

            pw.flush();
            pw.close();
        }
        finally {
            if (pw != null)
                pw.close();
        }
    }

    public static void      write (StringBuilder buf, String file, boolean createSubdirsIfNeeded) throws Exception {
        write(buf.toString(), file, createSubdirsIfNeeded);
    }

    /**
     *
     * @param buf
     * @param file
     * @param createSubdirsIfNeeded
     * @throws Exception
     */
    @InterestingAlgorithm
    public static void      write (String buf, String file, boolean createSubdirsIfNeeded) throws Exception {
        PrintWriter pw = null;

        try {

            if (createSubdirsIfNeeded) {
                int lastSlashIdx = file.lastIndexOf("/");
                if (lastSlashIdx != -1) {
                    String dir = file.substring(0, lastSlashIdx);
                    if (!dirExists(dir)) {
                        mkdir(dir);
                    }
                }
            }

             pw = new PrintWriter(new File(file));

            pw.print(buf.toString());
            pw.flush();
            pw.close();
        }
        finally {
            if (pw != null)
                pw.close();
        }
    }
}
