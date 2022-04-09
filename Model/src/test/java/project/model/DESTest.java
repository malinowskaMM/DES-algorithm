package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class DESTest {




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








}