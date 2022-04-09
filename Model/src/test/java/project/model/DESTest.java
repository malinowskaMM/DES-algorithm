package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;
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
        Keys k = new Keys();
        int[] table = k.PC2KeyPermutationTable;
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
    public void substitutionTest() {
        BitSet bs = new BitSet(48);
        bs.set(0);
        bs.set(1);
        bs.set(7, 9);
        bs.set(10);
        bs.set(12);
        bs.set(18);
        bs.set(20);
        bs.set(23);
        bs.set(25, 28);
        bs.set(30, 32);
        bs.set(34, 38);
        bs.set(39, 42);
        bs.set(45);

        BitSet sub = des.substitution(bs);

        assertEquals(sub.cardinality(), 20);

        assertTrue(sub.get(0));
        assertTrue(sub.get(1));
        assertTrue(sub.get(2));
        assertTrue(sub.get(3));
        assertTrue(sub.get(4));
        assertTrue(sub.get(5));
        assertTrue(sub.get(6));
        assertTrue(sub.get(9));
        assertTrue(sub.get(11));
        assertTrue(sub.get(14));
        assertTrue(sub.get(15));
        assertTrue(sub.get(17));
        assertTrue(sub.get(19));
        assertTrue(sub.get(20));
        assertTrue(sub.get(21));
        assertTrue(sub.get(22));
        assertTrue(sub.get(23));
        assertTrue(sub.get(24));
        assertTrue(sub.get(26));
        assertTrue(sub.get(29));


        assertFalse(sub.get(7));
        assertFalse(sub.get(8));
        assertFalse(sub.get(10));
        assertFalse(sub.get(12));
        assertFalse(sub.get(13));
        assertFalse(sub.get(16));
        assertFalse(sub.get(18));
        assertFalse(sub.get(25));
        assertFalse(sub.get(27));
        assertFalse(sub.get(28));
        assertFalse(sub.get(30));
        assertFalse(sub.get(31));
    }







}