package alg.math.stats;

import alg.misc.InterestingAlgorithm;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/13/15
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Histogram {

    private double [] bins;

    @InterestingAlgorithm
    public Histogram (double [] data, int numBins, boolean normalize) {

        bins = new double[numBins];

        if (data == null || data.length == 0 || numBins == 0)
            return;

        Double min = null;
        Double max = null;

        for (int i = 0; i < data.length; i++) {
            if (min == null)
                min = data [i];
            else if (data [i] < min)
                min = data [i];

            if (max == null)
                max = data [i];
            else if (data [i] > max)
                max = data [i];

        }

        double binSize =  max - min / numBins;

        for (int i = 0; i < data.length; i++) {
            int binIdx = (int)(data [i] / binSize);

            bins [binIdx]++;
        }

        if (normalize) {
            double total = 0;
            for (int i = 0; i < bins.length; i++) {
                total += bins [i];
            }

            for (int i = 0; i < bins.length; i++) {
                bins [i] = bins [i] / total;           // total should never be zero at this point
            }

        }
    }

    enum HistogramCharacter {
        SKEWED_RIGHT,
        SKEWED_LEFT,
        MULTI_MODAL,
        SYMMETRIC,
        BIMODAL
    }
}
