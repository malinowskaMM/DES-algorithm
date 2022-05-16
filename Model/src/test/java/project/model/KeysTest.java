package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KeysTest {

    Keys k;

    @BeforeEach
    public void initial() {
        k = new Keys();
    }


    @Test
    public void generate16keysTest() {
        BitSet key = new BitSet(64);
        for (int i = 0; i < 64; i++) {
            if ((i % 2 == 0) && (i % 5 != 0)) {
                key.set(i);
            }
        }

        List<BitSet> keys = k.generate16keys(key);

        for (int i = 0; i < 15; i++) {
            assertTrue(keys.get(i).length() <= 48);
        }
    }

}