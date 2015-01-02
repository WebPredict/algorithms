package alg.words;

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

    /**
     * TODO: probably want a map from chacter to coordinate on keyboard
     */

    public static int []    getCoordinates (Character c) {
        return (new int[] {0, 0}); // TODO
    }
}
