package project.model;

import java.util.BitSet;
import java.util.List;

public class TripleDES {

    List<BitSet> subKeysOne;
    List<BitSet> subKeysTwo;
    List<BitSet> subKeysThree;

    public TripleDES(BitSet firstKey, BitSet secondKey, BitSet thirdKey) {
        Keys k = new Keys();
        subKeysOne = k.generate16keys(firstKey);
        subKeysTwo = k.generate16keys(secondKey);
        subKeysThree = k.generate16keys(thirdKey);
    }

    public BitSet encrypt(BitSet message) {
        BitOperations bo = new BitOperations();
        DES d1 = new DES(subKeysOne);
        BitSet m1 = d1.cypherOneBlock(message);
        DES d2 = new DES(bo.reverseKeysOrder(subKeysTwo));
        BitSet m2 = d2.cypherOneBlock(m1);
        DES d3 = new DES(subKeysThree);
        BitSet m3 = d1.cypherOneBlock(m2);

        return m3;
    }

    public BitSet decrypt(BitSet message) {
        BitOperations bo = new BitOperations();
        DES d1 = new DES(bo.reverseKeysOrder(subKeysOne));
        BitSet m1 = d1.cypherOneBlock(message);
        DES d2 = new DES(subKeysTwo);
        BitSet m2 = d2.cypherOneBlock(m1);
        DES d3 = new DES(bo.reverseKeysOrder(subKeysThree));
        BitSet m3 = d1.cypherOneBlock(m2);

        return m3;
    }
}
