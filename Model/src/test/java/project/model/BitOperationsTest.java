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
        BitSet subKey = new BitSet(28);
        subKey.set(0, 2);
        subKey.set(26, 28);
        assertTrue(subKey.length() <= 28);
        BitSet result = bo.leftShift(subKey, 28, 1);
        assertTrue(result.length() <= 28);
        assertEquals(subKey.cardinality(), result.cardinality());
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
        assertEquals(output.cardinality(), input.cardinality());
    }
}