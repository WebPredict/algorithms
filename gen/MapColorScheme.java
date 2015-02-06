package gen;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/31/15
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapColorScheme {

    // Support a more explicit specific map of colors for items, like:
   // { "buttons" => "grey", "logo" => "white", "navbar" => "blue", "links" => "blue"}

    private HashMap<String, String> itemsToColorsMap;

    public MapColorScheme (HashMap<String, String> itemsToColorsMap) {
        this.itemsToColorsMap = itemsToColorsMap;
    }

    public HashMap<String, String> getItemsToColorsMap() {
        return itemsToColorsMap;
    }

    public void setItemsToColorsMap(HashMap<String, String> itemsToColorsMap) {
        this.itemsToColorsMap = itemsToColorsMap;
    }
}
