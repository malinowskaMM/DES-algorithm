package project.model;

import java.util.BitSet;

public class BitOperations {

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

    public BitSet[] split(BitSet block, int size) {
        int splitIndex = size / 2;

        // left and right are swapped, because BitSet goes from left to right
        // but binary notation goes from right (the oldest bit) to left
        // so L0 in code relates to L0 in a book
        BitSet L0 = block.get(splitIndex, size);
        BitSet R0 = block.get(0, splitIndex);

        return new BitSet[] {L0, R0};
    }

    // Shift = 1 or 2
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
