package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;
import org.junit.jupiter.api.Test;

class TripleDESTest {

    @Test
    void encryptAndDecryptTest() {
        BitSet originalMessage = new BitSet(64);
        originalMessage.set(0);
        originalMessage.set(3, 11);
        originalMessage.set(21, 26);
        originalMessage.set(55, 61);

        BitSet key1 = new BitSet(64);
        BitSet key2 = new BitSet(64);
        BitSet key3 = new BitSet(64);

        // generate keys
        for (int i = 0; i < 64; i++) {
            if ((i % 2 == 0) && (i % 5 != 0)) {
                key1.set(i);
            }
            if ((i % 3 == 0) && (i % 8 != 0)) {
                key2.set(i);
            }
            if (i % 5 == 0) {
                key3.set(i);
            }
        }

        TripleDES tripleDES = new TripleDES(key1, key2, key3);

        BitSet encrypted = tripleDES.encrypt(originalMessage);
        assertNotEquals(originalMessage, encrypted);
        assertTrue(encrypted.length() <= 64);

        BitSet decrypted = tripleDES.decrypt(encrypted);
        assertEquals(originalMessage, decrypted);
        assertTrue(decrypted.length() <= 64);
    }
}