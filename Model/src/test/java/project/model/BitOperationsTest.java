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
    public void bitSetToIntTest() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(4);
        int v = bo.bitSetToInt(bs, 5);
        assertEquals(v, 17);

        bs.set(1);
        v = bo.bitSetToInt(bs, 5);
        assertEquals(v, 25);

        bs.clear();
    }

    @Test
    public void intToBitSetTest() {
        int v = 3;
        BitSet bs = bo.intToBitSet(v, 4);
        assertEquals(bs.cardinality(), 2);
        assertTrue(bs.get(2));
        assertTrue(bs.get(3));

        v = 8;
        bs = bo.intToBitSet(v, 4);
        assertEquals(bs.cardinality(), 1);
        assertTrue(bs.get(0));

        v = 1;
        bs = bo.intToBitSet(v, 4);
        assertEquals(bs.cardinality(), 1);
        assertTrue(bs.get(3));
    }

    @Test
    public void stringToBitSetTest() {
        BitSet bs = new BitSet();
        String testString = "ok"; //1101111 1101011
        bs = bo.bitSetFromStringASCII(testString);

        String result="11011111101011";
        StringBuilder stringBuilder = new StringBuilder(result);
        stringBuilder = stringBuilder.reverse();
        BitSet bsResult = new BitSet();
        for(int i = 0; i < result.length(); i++) {
            if(stringBuilder.toString().charAt(i) == '1') {
                bsResult.set(i);
            }
        }
        assertEquals(BitOperations.bitSetToStringASCII(bsResult), "ok");
        assertEquals(bs, bsResult);
    }
}