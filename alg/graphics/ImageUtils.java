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
     * Copied from: http://ostermiller.org/dilate_and_erode.html
     * @param imageMap
     * @param radiusOfSnow
     * @return
     */
    public static ImageMap correctSnow (ImageMap imageMap, int radiusOfSnow) {
        ImageMap blackAndWhiteImage = imageMap.clone().threshold(1);
        // Copy the threshold and dilate and erode the copy
        ImageMap dilatedAndEroded = blackAndWhiteImage.clone().dilate(radiusOfSnow).erode(radiusOfSnow);

        // Figure out which pixels were snow based on the difference
        // between the thresholded image and the dilated and eroded image
        ImageMap snowPixels = diff(blackAndWhiteImage, dilatedAndEroded);
        // Fix up any pixels in the original that were marked as snow by
        // filling them with color of surrounding pixels
        return blendSnowPixels(imageMap, snowPixels);
    }

    /**
     *
     * @param original
     * @param snowPixels
     * @return new ImageMap with pixels marked as snow with the color of surrounding pixels in original
     */
    public static ImageMap blendSnowPixels (ImageMap original, ImageMap snowPixels) {

        int rows = original.getRows();
        int cols = original.getCols();

        if (snowPixels.getRows() != rows || snowPixels.getCols() != cols)
            throw new RuntimeException("Incompatible image size in blendSnowPixels");

        ImageMap ret = new ImageMap(rows, cols, original.getMaxColors());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (snowPixels.getColorAt(i, j) != 0) {
                    int averageColor = original.getAverageColorAround(i, j);
                    ret.setColorAt(i, j, averageColor);
                }
                else
                    ret.setColorAt(i, j, original.getColorAt(i, j));
            }
        }

        return (ret);
    }

    public static ImageMap diff (ImageMap map1, ImageMap map2) {
        int rows = map1.getRows();
        int cols = map1.getCols();

        if (map2.getRows() != rows || map2.getCols() != cols)
            throw new RuntimeException("Incompatible image size in image diff");

        ImageMap ret = new ImageMap(rows, cols, map1.getMaxColors()); // hmm

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map1.getColorAt(i, j) != map2.getColorAt(i, j)) {
                    ret.setColorAt(i, j, 1);
                }
                else
                    ret.setColorAt(i, j, 0);
            }
        }

        return (ret);
    }

}
