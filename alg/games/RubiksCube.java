package alg.games;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/12/14
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class RubiksCube {

    // hmm not sure this is the easiest to work with
    private int [][][] allFaces = new int[3][3][3];

    public RubiksCube () {

    }

    static enum Face {
        LEFT,
        RIGHT,
        FRONT,
        TOP,
        BOTTOM,
        BACK
    }

    public void rotate (Face face, int numTurnsClockwise) {

    }

    public int [][] getFaceValues (Face face) {

        return (null); // TODO
    }

}
