package alg.arrays;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/23/14
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Interval {

    public int start;
    public int end;

    public Interval (int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Should look like: "[1 2]"
     * @param s
     * @return
     */
    public static Interval parse (String s) {
        String [] vals = s.substring(1, s.length() - 1).split(" ");
        return (new Interval(Integer.parseInt(vals [0]), Integer.parseInt(vals [1])));
    }
}
