package alg.words;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/2/15
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Keyboard {

    public static final Character [] ROW1 = new Character [] {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'};
    public static final Character [] ROW2 = new Character [] {'a', 's', 'd', 'f', 'g', 'y', 'h', 'j', 'k', 'l', ';', '\''};
    public static final Character [] ROW3 = new Character [] {'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/'};

    public static HashMap<Character, Integer []> characterCoordinatesMap = new HashMap<Character, Integer []>();

    static {
        for (int i = 0; i < ROW1.length; i++) {
            characterCoordinatesMap.put(ROW1 [i], new Integer[] {0, i});
        }
        for (int i = 0; i < ROW2.length; i++) {
            characterCoordinatesMap.put(ROW2 [i], new Integer[] {1, i});
        }
        for (int i = 0; i < ROW3.length; i++) {
            characterCoordinatesMap.put(ROW3 [i], new Integer[] {2, i});
        }
    }

    public static Integer []    getCoordinates (Character c) {
        return (characterCoordinatesMap.get(c));
    }

    public static int       getDistanceBetween (char c1, char c2) {
        Integer [] c1Coord = characterCoordinatesMap.get(c1);

        Integer [] c2Coord = characterCoordinatesMap.get(c2);

        if (c1Coord == null || c2Coord == null)
            throw new RuntimeException("Unimplemented for characters " + c1 + " or " + c2);

        int total = (int)Math.sqrt(Math.pow(c1Coord [0] - c2Coord [0], 2) + Math.pow(c1Coord[1] - c2Coord [1], 2));

        return (total);
    }
}
