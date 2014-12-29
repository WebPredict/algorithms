package alg.games;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/12/14
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Crossword {

    // desired size
    // dictionary

    private List<String> dictionary;
    private int     rows;
    private int     cols;

    public Crossword (List<String> dictionary, int rows, int cols) {
        this.dictionary = dictionary;
        this.rows = rows;
        this.cols = cols;
    }


}
