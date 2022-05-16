package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.junit.jupiter.api.Test;

class TripleDESTest {

    @Test
    public void encryptionTest() {
        BitOperations bo = new BitOperations();

        BitSet key1 = new BitSet(); // (string "@@@@@@@@")
        BitSet key2 = new BitSet(); // (string "AAAAAAAA")
        BitSet key3 = new BitSet(); // (string "DDDDDDDD")
        for(int i = 0; i < 8; i++) {
            key1.set(1 + i * 8);
            key2.set(1 + i * 8);
            key2.set(7 + i * 8);
            key3.set(1 + i * 8);
            key3.set(5 + i * 8);
        }

        // message (string "FFFFFFFF")
        BitSet message = new BitSet();
        for(int i = 0; i < 8; i++) {
            message.set(1 + i * 8);
            message.set(5 + i * 8);
            message.set(6 + i * 8);
        }

        // encryption
        TripleDES d = new TripleDES(key1, key2, key3);
        BitSet encrypted = d.encrypt(message);
        String encStr = bo.bitSetToString(encrypted);

        String hexString = new BigInteger(encStr, 2).toString(16);
        assertEquals(hexString, "8bd6c4f0bdc50e43");
    }

    @Test
    public void encryptAndDecryptTest_II() {
        List<BitSet> keys = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            BitSet key = new BitSet();
            for (int j = 0; j < 64; j++) {
                if((int)Math.round(Math.random()) == 1) {
                    key.set(j);
                }
            }
            keys.add(key);
        }
        TripleDES tripleDES = new TripleDES(keys.get(0), keys.get(1), keys.get(2));

        String[] msg = {"Michal", "Magda", "kOpeRnik", "7e2_z6!", "b", "@@@oKp", "   [    ", "nabuchodonozor"};
        ArrayList<String> decrypted = new ArrayList<>();
        for(String s : msg) {
            BitSet bs = BitOperations.stringASCIIToBitSet(s);
            BitSet encryptedBS = tripleDES.encrypt(bs);
            BitSet decryptedBS = tripleDES.decrypt(encryptedBS);
            decrypted.add(BitOperations.bitSetToStringASCII(decryptedBS));
        }

        for(int i = 0; i < msg.length; i++) {
            assertEquals(msg[i], decrypted.get(i));
        }
    }

    @Test
    public void encryptAndDecryptTest() {
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