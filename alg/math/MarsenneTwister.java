package alg.math;

import alg.bits.BitUtils;
import alg.bits.BitmapUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/7/15
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class MarsenneTwister {

    private int []  generatorState = new int[624];
    private int index = 0;

    public MarsenneTwister () {
        this((int)System.currentTimeMillis());    // Hmm
    }

    public MarsenneTwister (int seed) {
        generatorState [0] = seed;
        for (int i = 1; i < 624; i++) {
            generatorState [i] = 1812433253 * (generatorState[i - 1] ^ (generatorState[i - 1] >> 30)) + i;
        }
    }

    public int getNextRandom () {
        if (index == 0)
            generateNumbers();

        int ret = generatorState [index];

        ret ^= ret >> 11;
        ret ^= ret << 7 & 2636928640l; // TODO validate this
        ret ^= ret << 15 & 4022730752l;
        ret ^= ret >> 18;

        index = (index + 1) % 624;
        return (ret);
    }

    private void generateNumbers() {
        for (int i = 0; i < 624; i++) {
            int y = generatorState [i] & 0x80000000 +
                    generatorState [(i + 1) % 624] & 0x7fffffff;
             generatorState [i] = generatorState [(i + 397) % 624] ^ (y >> 1);
            if (y % 2 != 0) {
                generatorState [i] = (int)(generatorState [i] ^ 2567483615l);  // TODO not sure about this cast
            }

        }
    }
}
