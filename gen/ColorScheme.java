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

    private String color1;
    private String color2;
    private String color3;
    private String color4;
    private String color5;

    public static HashMap<String, ColorScheme> niceColorSchemes = new HashMap<String, ColorScheme>();

    static {
        niceColorSchemes.put("black-red-blue", new ColorScheme("000000", "7F909A", "ED6639", "385B9F", "FFFFFF"));
        niceColorSchemes.put("sandy-red", new ColorScheme("EDDAB6", "E8D1A4", "DDA185", "AB3334", "331919"));
        niceColorSchemes.put("pink-cyan", new ColorScheme("57102C", "4CA8A1", "7EC2AA", "BCC747", "A92159"));
        niceColorSchemes.put("beige-red", new ColorScheme("CDCAB9", "D6D3C4", "DFDED4", "432F21", "CA171B"));
        niceColorSchemes.put("green-red", new ColorScheme("9BCE7D", "72AC93", "699E87", "BD0102", "98002F"));

    }

    public static ColorScheme findByName (String name) {
        return (niceColorSchemes.get(name));
    }

    public ColorScheme(String color1, String color2, String color3, String color4) {
        this(color1, color2, color3, color4, "000000");
    }

    public ColorScheme(String color1, String color2, String color3, String color4, String color5) {
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.color5 = color5;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getColor3() {
        return color3;
    }

    public String getColor4() {
        return color4;
    }

    public String getColor5() {
        return color5;
    }
}
