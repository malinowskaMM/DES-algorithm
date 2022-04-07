package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DESTest {
    DES des;

    @BeforeEach
    public void initial() {
        des = new DES();
    }

    // unimportant test just to see how class BitSet works
    @Test
    public void bitSetClassBehaviourTest() {
        // https://www.baeldung.com/java-bitset
        BitSet bitSet = new BitSet(64);
        assertEquals(bitSet.size(), 64);
        int index = 6;
        bitSet.set(6);
        assertTrue(bitSet.get(index));
        bitSet.clear(index);
        assertFalse(bitSet.get(index));
        bitSet.flip(index);
        assertTrue(bitSet.get(index));

        BitSet set1 = new BitSet();
        set1.set(0, 5); // arg (inclusive, exclusive)
        BitSet set2 = new BitSet();
        set2.set(3, 10);
        set1.and(set2); // logical AND
        assertTrue(set1.get(3));
        assertTrue(set1.get(4));
        assertFalse(set1.get(2));
        assertFalse(set1.get(5));
        //set2.stream().forEach(System.out::print);
    }

    @Test public void permuteAndSplitKey() {
        BitSet key = new BitSet(64);
        key.set(0, 64);
        BitSet[] newKey = des.permuteAndSplitKey(key);
        assertTrue(newKey[0].length() <= 28);
        assertTrue(newKey[1].length() <= 28);
    }

    @Test
    public void splitBlockTest() {
        BitSet input = new BitSet(64);
        input.set(0);
        input.set(30, 34);
        BitSet[] split = des.split(input, 64);

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
        BitSet output = des.permutation(input, table);

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