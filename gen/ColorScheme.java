package gen;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/26/15
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ColorScheme {

    private String bgPrimary;
    private String bgSecondary;
    private String firstAccent;
    private String secondAccent;
    private String lettering;

    public static HashMap<String, ColorScheme> niceColorSchemes = new HashMap<String, ColorScheme>();

    static {
        niceColorSchemes.put("black-red-blue", new ColorScheme("000000", "7F909A", "ED6639", "385B9F", "FFFFFF"));
        niceColorSchemes.put("sandy-red", new ColorScheme("EDDAB6", "E8D1A4", "DDA185", "AB3334", "331919"));
        niceColorSchemes.put("pink-cyan", new ColorScheme("57102C", "4CA8A1", "7EC2AA", "BCC747", "A92159"));
        niceColorSchemes.put("beige-red", new ColorScheme("CDCAB9", "D6D3C4", "DFDED4", "432F21", "CA171B"));
        niceColorSchemes.put("green-red", new ColorScheme("9BCE7D", "72AC93", "699E87", "BD0102", "98002F"));
        niceColorSchemes.put("finance", new ColorScheme("C1E1A6", "FFFFFF", "118C4E", "FF9009", "585858"));

    }

    public static ColorScheme findByName (String name) {
        return (niceColorSchemes.get(name));
    }

    public ColorScheme(String bgPrimary, String bgSecondary, String firstAccent, String secondAccent) {
        this(bgPrimary, bgSecondary, firstAccent, secondAccent, "000000");
    }

    public ColorScheme(String bgPrimary, String bgSecondary, String firstAccent, String secondAccent, String lettering) {
        this.bgPrimary = bgPrimary;
        this.bgSecondary = bgSecondary;
        this.firstAccent = firstAccent;
        this.secondAccent = secondAccent;
        this.lettering = lettering;
    }

    public String getBgPrimary() {
        return bgPrimary;
    }

    public String getBgSecondary() {
        return bgSecondary;
    }

    public String getFirstAccent() {
        return firstAccent;
    }

    public String getSecondAccent() {
        return secondAccent;
    }

    public String getLettering() {
        return lettering;
    }
}
