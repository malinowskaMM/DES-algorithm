package project.model;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class DES {
    public int[] initialPermutationTable = {
            58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9,  1, 59, 51, 43, 35, 27, 19, 11, 3,
            61, 33, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7
    };

    public int[] firstKeyPermutationTable = {
            57, 49, 41, 33, 25, 17,  9,
            1,  58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7,  62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13, 13, 28, 20, 12,  4
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

    public BitSet removeParityBitsFromKey(BitSet key) {
        BitSet result = new BitSet(54);
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


    public BitSet[] split(BitSet block, int size) {
        int splitIndex = size / 2;

        // left and right are swapped, because BitSet goes from left to right
        // but binary notation goes from right (the oldest bit) to left
        // so L0 in code relates to L0 in a book
        BitSet L0 = block.get(splitIndex, size);
        BitSet R0 = block.get(0, splitIndex);

        return new BitSet[] {L0, R0};
    }

    public int[] keyLeftShiftTable = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    public List<BitSet[]> generate16keys(BitSet key) {
        key = removeParityBitsFromKey(key);
        BitSet permutedKey = permutation(key, firstKeyPermutationTable);
        BitSet[] splitKey = split(permutedKey, 28);
        BitSet leftSubKey = splitKey[0];
        BitSet rightSubKey = splitKey[1];

        leftSubKey = leftShift(leftSubKey, 28, 1);
        rightSubKey = leftShift(rightSubKey, 28, 1);
        BitSet[] firstKey = {leftSubKey, rightSubKey};

        List<BitSet[]> keys = new ArrayList<>();
        keys.add(firstKey);

        for (int i = 1; i < 16; i++) {
            BitSet nextKeyLeft = leftShift(keys.get(i - 1)[0], 28, keyLeftShiftTable[i]);
            BitSet nextKeyRight = leftShift(keys.get(i - 1)[1], 28, keyLeftShiftTable[i]);
            BitSet[] nextKey = {nextKeyLeft, nextKeyRight};
            keys.add(nextKey);
        }

        return keys;
    }

    public BitSet leftShift(BitSet key, int size, int shift) {
        BitSet result = new BitSet(size);
        if (shift == 0) {
            result = key;
        }

        if (shift == 1) {
            if (key.get(size - 1)) {
                result.set(0);
            }

            for (int i = 0; i < size - 1; i++) {
                if (key.get(i)) {
                    result.set(i + 1);
                }
            }
        } else if (shift == 2) {
            if (key.get(size - 2)) {
                result.set(0);
            }
            if (key.get(size - 1)) {
                result.set(1);
            }

            for (int i = 0; i < size - 2; i++) {
                if (key.get(i)) {
                    result.set(i + 2);
                }
            }
        }

        return result;
    }
}
