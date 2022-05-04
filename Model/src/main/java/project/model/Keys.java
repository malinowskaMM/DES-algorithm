package project.model;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Keys {

    BitOperations bo = new BitOperations();

    public int[] keyLeftShiftTable = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    public int[] PC1KeyPermutationTable = {
            57, 49, 41, 33, 25, 17,  9,
            1,  58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7,  62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12,  4
    };

    public int[] PC2KeyPermutationTable = {
            14, 17, 11, 24,  1,  5,
            3,  28, 15,  6, 21, 10,
            23, 19, 12,  4, 26,  8,
            16,  7, 27, 20, 13,  2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    public BitSet removeParityBitsFromKey(BitSet key) {
        BitSet result = new BitSet(56);
        int j = 0;
        for (int i = 0; i < 64; i++) {
            if ((i + 1) % 8 == 0) {
                continue;
            }
            if (key.get(i)) {
                result.set(j);
            }
            j++;
        }
        return result;
    }

    public List<BitSet> generate16keys(BitSet key) {
        key = removeParityBitsFromKey(key);
        BitSet permutedKey = bo.permutation(key, PC1KeyPermutationTable);
        BitSet[] splitKey = bo.split(permutedKey, 56);
        BitSet leftSubKey = splitKey[0];
        BitSet rightSubKey = splitKey[1];

        leftSubKey = bo.leftShift(leftSubKey, 28, 1);
        rightSubKey = bo.leftShift(rightSubKey, 28, 1);
        BitSet[] firstKey = {leftSubKey, rightSubKey};


        List<BitSet[]> keys = new ArrayList<>();
        keys.add(firstKey);

        for (int i = 1; i < 16; i++) {
            BitSet nextKeyLeft = bo.leftShift(keys.get(i - 1)[0], 28, keyLeftShiftTable[i]);
            BitSet nextKeyRight = bo.leftShift(keys.get(i - 1)[1], 28, keyLeftShiftTable[i]);
            BitSet[] nextKey = {nextKeyLeft, nextKeyRight};
            keys.add(nextKey);
        }

        List<BitSet> keysConcat = new ArrayList<>();
        // concatenate each key right to left
        for(var subKey: keys) {
            BitSet concatenated = bo.concatenation(subKey[0], subKey[1], 28);
            keysConcat.add(concatenated);
        }

        List<BitSet> finalKeys = new ArrayList<>();
        for(BitSet k: keysConcat) {
            BitSet finalKey = bo.permutation(k, PC2KeyPermutationTable);
            finalKeys.add(finalKey);
        }

        return finalKeys;
    }
}
