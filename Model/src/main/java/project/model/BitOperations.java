package project.model;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

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

    public List<BitSet> reverseKeysOrder(List<BitSet> keyList) {
        List<BitSet> reversed = new ArrayList<>();
        int size = keyList.size();
        for (int i = 0; i < size; i++) {
            reversed.add(keyList.get(size - i - 1));
        }
        return reversed;
    }

    public BitSet reverseBitOrder(BitSet bits, int length) {
        BitSet result = new BitSet();
        for (int i = 0; i < length; i ++) {
            if(bits.get(i)) {
                result.set(length - i - 1);
            }
        }
        return result;
    }

    public BitSet concatenation(BitSet left, BitSet right, int sizeOfHalf) {
        for(int i = 0; i < sizeOfHalf; i++) {
            if(right.get(i)) {
                left.set(i + sizeOfHalf);
            }
        }
        return left;
    }

    public BitSet[] split(BitSet block, int size) {
        int splitIndex = size / 2;
        BitSet l = block.get(0, splitIndex);
        BitSet r = block.get(splitIndex, size);

        return new BitSet[] {l, r};
    }

    // Shift = 1 or 2
    public BitSet leftShift(BitSet key, int size, int shift) {
        BitSet result = new BitSet(size);
        if (shift == 0) {
            result = key;
        }

        if (shift == 1) {
            if (key.get(0)) {
                result.set(size - 1);
            }

            for (int i = 1; i < size ; i++) {
                if (key.get(i)) {
                    result.set(i - 1);
                }
            }
        } else if (shift == 2) {
            if (key.get(0)) {
                result.set(size - 2);
            }
            if (key.get(1)) {
                result.set(size - 1);
            }

            for (int i = 2; i < size; i++) {
                if (key.get(i)) {
                    result.set(i - 2);
                }
            }
        }

        return result;
    }

    public int bitSetToInt(BitSet bs, int len) {
        BitSet rev = reverseBitOrder(bs, len);
        long[] l = rev.toLongArray();
        return (int)l[0];
    }

    public String bitSetToString(BitSet bs) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < bs.size(); i++) {
            if(bs.get(i)) {
                result.append("1");
            } else {
                result.append("0");
            }
        }
        return result.toString();
    }

    public BitSet intToBitSet(int value, int len) {
        BitSet result = new BitSet();
        for(int i = 0; i < len; i++) {
            int v = (((value % 2) == 0) ? 0 : 1);
            if(v == 1) {
                result.set(len - i - 1);
            }
            value /= 2;
        }

        return result;
    }

    public BitSet bitSetFromStringASCII(String in) {
        if (in.isEmpty()) {
            return new BitSet();
        }
        String[] strings = new String[in.length()];
        for (int i = 0; i < in.length(); i++) {
            strings[i] = Integer.toBinaryString(in.charAt(i));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            stringBuilder.append(strings[i]);
        }

        stringBuilder = stringBuilder.reverse();

        BitSet bits = new BitSet();

        for (int i = 0; i < stringBuilder.toString().length(); i++) {
                if(stringBuilder.toString().charAt(i) == '1') {
                    bits.set(i);
                }
        }
        return bits;
    }


    public static String bitSetToStringASCII(BitSet bits) {
        if (bits.isEmpty()) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = bits.length() - 1; i >= 0; i--) {
            if (bits.get(i)) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }

        StringBuilder resultASCII = new StringBuilder();
        int div = sb.toString().length()/7;
        String[] strings = new String[div];
        int[] intsFromStrings =  new int[div];

        for(int i = 0; i < div; i++) {
            String string = "";
            StringBuilder stringBuilder = new StringBuilder(string);
            int end = (i+1) * 7;
            for(int j = i * 7; j < end; j++) {
                stringBuilder.append(sb.toString().charAt(j));
            }
            strings[i] = stringBuilder.toString();
            intsFromStrings[i] = Integer.parseInt(strings[i], 2);
            resultASCII.append((char)intsFromStrings[i]);
        }

        return resultASCII.toString();
    }

}
