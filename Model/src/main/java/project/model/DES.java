package project.model;

import java.util.BitSet;

public class DES {
    public int[] initialPermutationTable = {
            58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9,  1, 59, 51, 43, 35, 27, 19, 11, 3,
            61, 33, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7
    };

    public BitSet permutation(BitSet bits, int[] table) {
        int length = table.length;
        BitSet result = new BitSet(length);
        for (int i = 0; i < length; i++) {
            if (bits.get(table[i] - 1)) {
                result.set(i);
            }
        }
        return result;
    }


    public BitSet[] permuteAndSplitKey(BitSet key) {
        BitSet keyWithoutParityBits = new BitSet(54);
        int[] table = {
                   57, 49, 41, 33, 25, 17,  9,
                   1,  58, 50, 42, 34, 26, 18,
                   10,  2, 59, 51, 43, 35, 27,
                   19, 11,  3, 60, 52, 44, 36,
                   63, 55, 47, 39, 31, 23, 15,
                   7,  62, 54, 46, 38, 30, 22,
                   14,  6, 61, 53, 45, 37, 29,
                   21, 13, 13, 28, 20, 12,  4
        };
        int j = 0;
        for(int i = 0; i < 64; i++) {
            if((i + 1) % 8 == 0)
                continue;
            if(key.get(i))
                keyWithoutParityBits.set(j);
            j++;
        }

        BitSet permutedKey = permutation(keyWithoutParityBits, table);
        return split(permutedKey, 54);
    }

    public BitSet[] split(BitSet block, int size) {
        int splitIndex = size / 2;
        // BitSet is always size multiple of 64, default 64
        BitSet L0 = new BitSet();
        BitSet R0 = new BitSet();
        for (int i = 0; i < splitIndex; i++) {
            if(block.get(i))
                L0.set(i);
            if(block.get(i + splitIndex))
                R0.set(i);
        }
        return new BitSet[] {L0, R0};
    }

}
