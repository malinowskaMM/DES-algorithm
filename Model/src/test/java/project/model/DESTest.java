package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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

    // for debugging
    @Test void checkTableForDuplicates() {
        int[] table = des.PC2KeyPermutationTable;
        Set a = new TreeSet();
        int tableSize = table.length;
        for(int element: table) {
            a.add(element);
        }
        int setSize = a.size();

//        System.out.printf("table size: %d, set size: %d\n", tableSize, setSize);
//        Arrays.sort(table);
//        a.forEach(System.out::println);
//        for(var x: table) {
//            System.out.println(x);
//        }
//        assertEquals(tableSize, setSize);
    }

    @Test
    public void generate16keysTest() {
        BitSet key = new BitSet(64);
        for (int i = 0; i < 64; i++) {
            if ((i % 2 == 0) && (i % 5 != 0)) {
                key.set(i);
            }
        }

        List<BitSet> keys = des.generate16keys(key);

        for (int i = 0; i < 15; i++) {
            assertTrue(keys.get(i).length() <= 48);
        }
    }

    @Test
    public void leftShiftTest() {
        BitSet subKey = new BitSet(28);
        subKey.set(0, 2);
        subKey.set(26, 28);
        assertTrue(subKey.length() <= 28);
        BitSet result = des.leftShift(subKey, 28, 1);
        assertTrue(result.length() <= 28);
        assertEquals(subKey.cardinality(), result.cardinality());
    }

    @Test
    public void splitBlockTest() {
        BitSet input = new BitSet(64);
        input.set(0);
        input.set(30, 34);
        BitSet[] split = des.split(input, 64);

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