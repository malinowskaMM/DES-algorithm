package project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DESTest {

    DES des;

    @BeforeEach
    public void initial() {
        BitSet initialKey = new BitSet(64);
        initialKey.set(2, 8);
        Keys k = new Keys();
        des = new DES(k.generate16keys(initialKey));
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

    @Test
    public void FFunctionTest() {
        BitSet subKey = new BitSet(48);
        subKey.set(0);
        subKey.set(3);
        subKey.set(7, 10);
        subKey.set(12);
        subKey.set(47);

        BitSet halfBlock = new BitSet(32);
        halfBlock.set(1);
        halfBlock.set(5, 13);

        BitSet result = des.FFunction(halfBlock, subKey);
        assertEquals(result.cardinality(), 11);
        assertTrue(result.get(0));
        assertTrue(result.get(3));
        assertTrue(result.get(5));
        assertTrue(result.get(9));
        assertTrue(result.get(11));
        assertTrue(result.get(19));
        assertTrue(result.get(20));
        assertTrue(result.get(22));
        assertTrue(result.get(24));
        assertTrue(result.get(25));
        assertTrue(result.get(28));
    }

    @Test
    public void encryption() {
        BitOperations bo = new BitOperations();

        // key
        BitSet key = new BitSet();
        for(int i = 0; i < 8; i++) {
            key.set(1 + i * 8);
        }
        System.out.println("KEY:");
        String keyStr = bo.bitSetToString(key);
        System.out.println(keyStr);

        // key reverted
        BitSet keyR = new BitSet();
        for(int i = 0; i < 8; i++) {
            keyR.set(6 + i * 8);
        }
        System.out.println("KEY reverted:");
        String keyStrR = bo.bitSetToString(keyR);
        System.out.println(keyStrR);

        // message
        BitSet message = new BitSet();
        for(int i = 0; i < 8; i++) {
            message.set(1 + i * 8);
        }
        System.out.println("MSG:");
        String msgStr = bo.bitSetToString(message);
        System.out.println(msgStr);


        // message reverted
        BitSet messageR = new BitSet();
        for(int i = 0; i < 8; i++) {
            messageR.set(6 + i * 8);
        }
        System.out.println("MSG reverted:");
        String msgStrR = bo.bitSetToString(messageR);
        System.out.println(msgStrR);

        Keys k = new Keys();
        List<BitSet> subKeys = k.generate16keys(key);
        List<BitSet> subKeysR = k.generate16keys(key);

        // encryption
        DES d = new DES(subKeys);
        BitSet encrypted = d.cypherOneBlock(message);
        String encStr = bo.bitSetToString(encrypted);
        System.out.println("ENC:");
        System.out.println(encStr);
        String hexString = new BigInteger(encStr, 2).toString(16);
        System.out.println(hexString);

        // encryption reverted
        DES dR = new DES(subKeysR);
        BitSet encryptedR = d.cypherOneBlock(messageR);
        String encStrR = bo.bitSetToString(encryptedR);
        System.out.println("ENC reverted:");
        System.out.println(encStrR);
        String hexStringR = new BigInteger(encStrR, 2).toString(16);
        System.out.println(hexStringR);

        String expectedOutput = "6d55ddbc8dea95ff";
        boolean isEncCorrect = hexString.equals(expectedOutput);
        boolean isEncRevertedCorrect = hexStringR.equals(expectedOutput);
        boolean isEitherCorrect = isEncCorrect || isEncRevertedCorrect;
        assertEquals(hexString, isEitherCorrect);
    }

    @Test
    public void encryptAndDecryptTest() {
        Keys k = new Keys();
        BitSet key = new BitSet(64);
        for (int i = 0; i < 64; i++) {
            if ((i % 2 == 0) && (i % 5 != 0)) {
                key.set(i);
            }
        }

        BitSet message = new BitSet(64);
        for (int i = 0; i < 64; i++) {
            if ((i % 3 == 0) && (i % 8 != 0)) {
                message.set(i);
            }
        }

        BitSet message1 = new BitSet(64);
        for (int i = 0; i < 64; i++) {
            if ((i % 2 == 0) && (i % 5 != 0)) {
                message1.set(i);
            }
        }


        List<BitSet> subKeys = k.generate16keys(key);
        DES desEncrypt = new DES(subKeys);
        BitSet encrypted = desEncrypt.cypherOneBlock(message);
        BitSet encrypted1 = desEncrypt.cypherOneBlock(message1);

        assertNotEquals(encrypted, encrypted1);

        assertEquals(encrypted.size(), message.size());
        assertNotEquals(encrypted, message);

        BitOperations bo = new BitOperations();
        List<BitSet> subKeyReversed = bo.reverseKeysOrder(subKeys);
        DES desDecrypt = new DES(subKeyReversed);
        BitSet decrypted = desDecrypt.cypherOneBlock(encrypted);

    }

    @Test
    public void getFromSBoxTest() {
        assertEquals(des.getFromSBox(0, 0, 0), 14);
        assertEquals(des.getFromSBox(0, 3, 4), 4);
        assertEquals(des.getFromSBox(2, 3, 12), 11);
        assertEquals(des.getFromSBox(7, 2, 15), 8);
    }

    @Test
    public void substitutionTest() {
        BitSet bs = new BitSet(48);
        bs.set(0);
        bs.set(2);
        bs.set(3);
        bs.set(7);
        bs.set(10);
        bs.set(11);
        bs.set(13, 20);
        bs.set(47);

        BitSet sub = des.substitution(bs);
        assertEquals(sub.cardinality(), 11);

        assertTrue(sub.get(2));
        assertTrue(sub.get(11));
        assertTrue(sub.get(12));
        assertTrue(sub.get(13));
        assertTrue(sub.get(14));
        assertTrue(sub.get(15));
        assertTrue(sub.get(18));
        assertTrue(sub.get(20));
        assertTrue(sub.get(21));
        assertTrue(sub.get(25));
        assertTrue(sub.get(31));
    }
}