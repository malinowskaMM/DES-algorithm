package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BitOperationsTest {

    BitOperations bo;

    @BeforeEach
    public void initial() {
        bo = new BitOperations();
    }

    @Test
    public void leftShiftTest() {
        BitSet subKey = new BitSet(64);
        subKey.set(0, 2);
        subKey.set(3);
        System.out.println(bo.bitSetToString(subKey));
        BitSet result = bo.leftShift(subKey, 64, 1);
        System.out.println(bo.bitSetToString(result));

        assertEquals(result.cardinality(), 3);
        assertTrue(result.get(0));
        assertTrue(result.get(63));
        assertTrue(result.get(2));
    }

    @Test
    public void leftShiftTest_II() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(63);
        bs.set(3);
        System.out.println(bo.bitSetToString(bs));
        BitSet result = bo.leftShift(bs, 64, 1);
        System.out.println(bo.bitSetToString(result));
    }

    @Test
    public void leftShiftTest_III() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        bs.set(34);
        bs.set(63);
        System.out.println(bo.bitSetToString(bs));
        BitSet result = bo.leftShift(bs, 64, 2);
        System.out.println(bo.bitSetToString(result));

        assertEquals(result.cardinality(), 4);
        assertTrue(result.get(63));
        assertTrue(result.get(62));
        assertTrue(result.get(32));
        assertTrue(result.get(61));
    }

    @Test
    public void concatenationTest() {
        BitSet bitSet1 = new BitSet(6);
        BitSet bitSet2 = new BitSet(6);

        bitSet1.set(0);
        bitSet1.set(3);
        bitSet1.set(5);

        bitSet2.set(0);
        bitSet2.set(5);

        BitSet bitSet3 = new BitSet(12);
        bitSet3.set(0);
        bitSet3.set(3);
        bitSet3.set(5);
        bitSet3.set(6);
        bitSet3.set(11);

        assertEquals(bo.concatenation(bitSet1, bitSet2, 6), bitSet3);
    }

    @Test
    public void reverseKeysOrderTest() {
        Keys k = new Keys();
        BitSet key = new BitSet(64);
        for (int i = 0; i < 64; i++) {
            if ((i % 2 == 0) && (i % 5 != 0)) {
                key.set(i);
            }
        }
        List<BitSet> keyList = k.generate16keys(key);

        BitOperations bo = new BitOperations();
        List<BitSet> reversed = bo.reverseKeysOrder(keyList);
        for(int i = 0; i < 16; i ++) {
            assertEquals(keyList.get(i), reversed.get(15 - i));
        }
    }

    @Test
    public void reverseBitOrderTest() {
        BitSet bs = new BitSet();
        BitSet rev = BitOperations.reverseBitOrder(bs, 4);

        assertEquals(rev.size(), 64);
        assertTrue(rev.isEmpty());
    }

    @Test
    public void bitSetToStringTest() {
        BitSet bs = new BitSet(64);
        assertEquals(bo.bitSetToString(bs),
                "0000000000000000000000000000000000000000000000000000000000000000");

        bs.set(0);
        bs.set(3);
        bs.set(11);
        bs.set(15);
        assertEquals(bo.bitSetToString(bs),
                "1001000000010001000000000000000000000000000000000000000000000000");
    }

    @Test
    public void splitTest() {
        BitSet input = new BitSet(64);
        input.set(0);
        input.set(30, 34);
        assertEquals(input.cardinality(), 5);
        BitSet[] split = bo.split(input, 64);

        assertTrue(split[0].get(0));
        assertTrue(split[0].get(30));
        assertTrue(split[0].get(31));
        assertTrue(split[1].get(0));
        assertTrue(split[1].get(1));
        assertTrue(split[0].length() <= 32);
        assertTrue(split[1].length() <= 32);
        assertEquals(split[0].cardinality(), 3);
        assertEquals(split[1].cardinality(), 2);
    }

    @Test
    public void splitTestII() {
        BitSet input = new BitSet(6);
        input.set(0);
        input.set(2);
        input.set(5);

        BitSet result1 = new BitSet(3);
        BitSet result2 = new BitSet(3);
        result1.set(0);
        result1.set(2);
        result2.set(2);

        assertEquals(bo.split(input, 6)[0], result1);
        assertEquals(bo.split(input, 6)[1], result2);
    }

    @Test
    public void permutationTest_II() {
        BitSet bs = new BitSet();
        for(int i = 0; i < 8; i++) {
            bs.set(1 + i * 8);
        }
        Keys k = new Keys();
        BitSet permuted = bo.permutation(bs, k.PC1KeyPermutationTable);

        assertEquals(permuted.cardinality(), 8);
        assertTrue(permuted.get(8));
        assertTrue(permuted.get(9));
        assertTrue(permuted.get(10));
        assertTrue(permuted.get(11));
        assertTrue(permuted.get(12));
        assertTrue(permuted.get(13));
        assertTrue(permuted.get(14));
        assertTrue(permuted.get(15));
    }

    @Test
    public void permutationTest() {
        BitSet input = new BitSet(64);
        int[] table = {2, 5, 3, 4, 1, 9, 6, 7, 8, 10};
        input.set(0);
        input.set(2);
        input.set(3);
        input.set(6);
        input.set(9);
        assertEquals(input.cardinality(), 5);
        BitSet output = bo.permutation(input, table);

        assertFalse(output.get(0));
        assertFalse(output.get(1));
        assertTrue(output.get(2));
        assertTrue(output.get(3));
        assertTrue(output.get(4));
        assertFalse(output.get(5));
        assertFalse(output.get(6));
        assertTrue(output.get(7));
        assertFalse(output.get(8));
        assertTrue(output.get(9));
        assertTrue(input.length() <= 10);
        assertTrue(output.length() <= 10);
        assertEquals(output.cardinality(), 5);
    }

    @Test
    public void permutationTestII() {
        BitSet input = new BitSet(10);
        input.set(0); //ok
        input.set(3); //
        input.set(5); //ok
        input.set(7);
        input.set(9); //ok

        int[] table = {10, 6, 9, 5, 3, 2, 8, 7, 4, 1};
        BitSet result = new BitSet(10);
        result.set(0);
        result.set(1);
        result.set(6);
        result.set(8);
        result.set(9);

        assertEquals(bo.permutation(input, table), result);
    }

    @Test
    public void bitSetToIntTest() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(4);
        int v = BitOperations.bitSetToInt(bs, 5);
        assertEquals(v, 17);

        bs.clear();
        bs.set(0);
        v = BitOperations.bitSetToInt(bs, 8);
        assertEquals(v, 128);

    }

    @Test
    public void intToBitSetTest() {
        int v = 3;
        BitSet bs = BitOperations.intToBitSet(v, 4);
        assertEquals(bs.cardinality(), 2);
        assertTrue(bs.get(2));
        assertTrue(bs.get(3));

        v = 8;
        bs = BitOperations.intToBitSet(v, 4);
        assertEquals(bs.cardinality(), 1);
        assertTrue(bs.get(0));

        v = 1;
        bs = BitOperations.intToBitSet(v, 4);
        assertEquals(bs.cardinality(), 1);
        assertTrue(bs.get(3));
    }

    @Test
    public void bitSetToStringASCIITest() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 14; i++) {
                bs.set(1 + i * 8);
            if(i % 2 == 0) {
                bs.set(5 + i * 8);
            } else {
                bs.set(7 + i * 8);
            }
        }

        assertEquals(BitOperations.bitSetToStringASCII(bs), "DADADADADADADA");
    }

    @Test
    public void bitSetFromStringASCIITest() {
        String testString = "ok"; //01101111 01101011
        BitSet bs = BitOperations.stringASCIIFromBitSet(testString);

        assertEquals(bs.cardinality(), 11);
        assertFalse(bs.get(0));
        assertFalse(bs.get(3));
        assertFalse(bs.get(8));
        assertFalse(bs.get(11));
        assertFalse(bs.get(13));

        String ts = "PPPPPPBBBBB"; // 01010000 01010000 01010000 01010000 01010000 01010000 01000010 01000010 01000010 01000010 01000010
        bs.clear();
        bs = BitOperations.stringASCIIFromBitSet(ts);
        assertEquals(bs.cardinality(), 22);
    }
}