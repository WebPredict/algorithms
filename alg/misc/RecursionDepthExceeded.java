package alg.misc;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/29/14
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class RecursionDepthExceeded extends Exception {

    public RecursionDepthExceeded (int maxDepth) {
        super ("Depth exceeded: " + maxDepth);
    }
}
