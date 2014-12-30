package alg.graphics;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtils {
    /**
     * Erode
     * Dilate
     *
     * image effects? (e.g. reduce snow with erode/dilate)
     *
     */

    public static ImageMap correctSnow (ImageMap imageMap, int radius) {
        ImageMap blackAndWhiteImage = threshold(imageMap.clone(), THRESHOLD_VALUE);
        // Copy the threshold and dilate and erode the copy
        ImageMap dilatedAndEroded = imageMap.erode(
                blackAndWhiteImage.clone().dilate(radius),
                radius
        );

        // Figure out which pixels were snow based on the difference
        // between the thresholded image and the dilated and eroded image
        ImageMap snowPixels = diff(blackAndWhiteImage, dilatedAndEroded);
        // Fix up any pixels in the original that were marked as snow by
        // filling them with color of surrounding pixels
        return blendSnowPixels(imageMap, snowPixels);
    }

//    public static ImageMap  manhattanDistance (ImageMap imageMap) {
//        int rows = imageMap.getRows();
//        int cols = imageMap.getCols();
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//
//                if (imageMap.getColorAt(i, j) != 0) {
//                    imageMap.setColorAt(i, j, 0);
//                }
//                else {
//                    imageMap.setColorAt(i, j, rows + cols);
//                    if (i > 0)
//                        imageMap.setColorAt(i, j, Math.min(imageMap.getColorAt(i, j), imageMap.getColorAt(i - 1, j) + 1));
//                    if (j > 0)
//                        imageMap.setColorAt(i, j, Math.min(imageMap.getColorAt(i, j), imageMap.getColorAt(i, j - 1) + 1));
//                }
//            }
//        }
//
//        for (int i = rows - 1; i >= 0; i--) {
//            for (int j = cols - 1; j >= 0; j--) {
//                  if (i + 1 < rows)
//                      imageMap.setColorAt(i, j, Math.min(imageMap.getColorAt(i, j), imageMap.getColorAt(i + 1, j) + 1));
//                if (j + 1 < cols)
//                    imageMap.setColorAt(i, j, Math.min(imageMap.getColorAt(i, j), imageMap.getColorAt(i, j + 1) + 1));
//            }
//        }
//        return (imageMap);
//    }
}
