package alg.bits;

public class BitSet {

	private int []	data; 

    public BitSet (int size) {
        data = new int[size >> 5]; // div by 32
    }

    public boolean  get (int idx) {
        int dataIdx = idx >> 5;
        int remainder = idx % 32;

        return ((data [dataIdx] & (1 << remainder)) == 1);
    }

    public void     set (int idx, boolean value) {
        int dataIdx = idx >> 5;
        int remainder = idx % 32;
        data [dataIdx] |= (1 << remainder);
    }
}
