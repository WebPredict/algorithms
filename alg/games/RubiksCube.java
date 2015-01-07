package alg.games;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/12/14
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class RubiksCube {

    private SquareGrid[] faces = new SquareGrid[6];

    public RubiksCube () {
        for (int i = 0; i < faces.length; i++) {
            faces [i] = new SquareGrid(3, i);
        }
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
        // This one's a doozy

        switch (face) {
            case FRONT: {
                SquareGrid top = getFaceValues(Face.TOP);
                SquareGrid left = getFaceValues(Face.LEFT);
                SquareGrid right = getFaceValues(Face.RIGHT);
                SquareGrid bottom = getFaceValues(Face.BOTTOM);

                // First, rotate the front face
                SquareGrid front = getFaceValues(Face.FRONT);
                front.rotate(numTurnsClockwise, true);

                // then rotate the parts of the other faces
                // TODO


            }
                break;
        }
    }

    public SquareGrid getFaceValues (Face face) {

        switch (face) {
            case LEFT:
                return (faces [0]);
            case RIGHT:
                return (faces [1]);
            case FRONT:
                return (faces [2]);
            case TOP:
                return (faces [3]);
            case BOTTOM:
                return (faces [4]);
            case BACK:
                return (faces [5]);
        }
        throw new RuntimeException("Invalid value for face: " + face);
    }

}
