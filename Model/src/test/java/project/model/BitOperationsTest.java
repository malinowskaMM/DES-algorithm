package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;
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
        BitSet subKey = new BitSet(6);
        subKey.set(0, 2);
        subKey.set(3);
        assertTrue(subKey.length() <= 6);
        assertEquals(subKey.cardinality(), 3);

        BitSet result = bo.leftShift(subKey, 6, 1);

        assertTrue(result.length() <= 6);
        assertEquals(result.cardinality(), 3);

        assertTrue(result.get(1));
        assertTrue(result.get(2));
        assertTrue(result.get(4));
    }

    @Test
    public void splitTest() {
        BitSet input = new BitSet(64);
        input.set(0);
        input.set(30, 34);
        BitSet[] split = bo.split(input, 64);

        assertTrue(split[0].get(0));
        assertTrue(split[0].get(1));
        assertTrue(split[1].get(0));
        assertTrue(split[1].get(30));
        assertTrue(split[1].get(31));
        assertTrue(split[0].length() <= 32);
        assertTrue(split[1].length() <= 32);
        assertEquals(split[0].cardinality(), 2);
        assertEquals(split[1].cardinality(), 3);
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
        int v = bo.bitSetToInt(bs);
        assertEquals(v, 17);

        bs.set(2);
        v = bo.bitSetToInt(bs);
        assertEquals(v, 21);
    }

    @Test
    public void intToBitSetTest() {
        int v = 3;
        BitSet bs = bo.intToBitSet(v);
        assertEquals(bs.cardinality(), 2);
        assertTrue(bs.get(0));
        assertTrue(bs.get(1));

        v = 25;
        bs = bo.intToBitSet(v);
        assertEquals(bs.cardinality(), 3);
        assertTrue(bs.get(0));
        assertTrue(bs.get(3));
        assertTrue(bs.get(4));
    }
}