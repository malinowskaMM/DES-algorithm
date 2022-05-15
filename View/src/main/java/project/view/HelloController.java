package project.view;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.BitSet;
import javax.swing.JFileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.model.*;

public class HelloController {
    TripleDES tripleDES;
    Key key;
    StringBuilder plaintextStringBuilder;
    StringBuilder cryptogramStringBuilder;
    String plaintextString;
    String cryptogramString;
    byte[] byteArea;
    BitSet firstKey;
    BitSet secondKey;
    BitSet thirdKey;
    BitSet encrypted;
    BitSet decrypted;
    BitOperations bitOperations;
    @FXML private TextArea plaintextArea;
    @FXML private TextArea cryptogramArea;
    @FXML private  TextField key1Field;
    @FXML private  TextField key2Field;
    @FXML private  TextField key3Field;

    private byte[] getByteAreaFromFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        StringBuilder textBuilder = new StringBuilder();
        int i;
        while( (i=fileReader.read()) != -1 ) {
            textBuilder.append((char)i);
        }
        byteArea =textBuilder.toString().getBytes(StandardCharsets.UTF_8);
        //uważać na błedy z buforem - czy na pewno on jest pusty przy ponownym ładowaniu
        return byteArea;
    }

    private void saveToFile(byte[] byteArea, TextArea textArea) throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            File file = new File(String.valueOf(selectedFile));
            try (FileWriter fw = new FileWriter(file)) {
                byteArea = textArea.getText().getBytes(StandardCharsets.UTF_8);
                fw.write(new String(byteArea,StandardCharsets.UTF_8));
                fw.flush();
            }
        }
    }

    BitSet generateKey(TextField textField) {
        BitSet key = new BitSet();
        for (int j = 0; j < 64; j++) {
            if((int)Math.round(Math.random()) == 1) {
                key.set(j);
            }
        }
        String s = BitOperations.bitSetToHex(key);
        textField.setText(s);
        return key;
    }

    @FXML private void saveKeysToFile() {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            File file = new File(String.valueOf(selectedFile));
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(new String(key1Field.getText().getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8));
                fw.write(new String(key2Field.getText().getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8));
                fw.write(new String(key3Field.getText().getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8));
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML private void loadKeysFromFile() throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String fromFile = Files.readString(selectedFile.toPath());
            firstKey = BitOperations.stringASCIIToBitSet(fromFile.substring(0, 8));
            key1Field.setText(fromFile.substring(0, 8));
            secondKey = BitOperations.stringASCIIToBitSet(fromFile.substring(8, 16));
            key2Field.setText(fromFile.substring(8, 16));
            thirdKey = BitOperations.stringASCIIToBitSet(fromFile.substring(16, 24));
            key3Field.setText(fromFile.substring(16, 24));
            }
        }


    @FXML protected void initialize() {
        key = new Key();
        bitOperations  = new BitOperations();
    }

    @FXML protected void loadTextFromFile() throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            byteArea = getByteAreaFromFile(selectedFile);
            plaintextString = new String(byteArea,StandardCharsets.UTF_8);
            plaintextArea.setText(plaintextString);
        }
    }

    @FXML protected void loadCryptogramFromFile() throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            byteArea = getByteAreaFromFile(selectedFile);
            cryptogramString = new String(byteArea,StandardCharsets.UTF_8);
            cryptogramArea.setText(cryptogramString);
        }
    }

    @FXML protected void saveTextFromPlaintextWindow(ActionEvent event) throws IOException {
        saveToFile(byteArea, plaintextArea);
    }

    @FXML protected void saveTextFromCryptogramWindow(ActionEvent event) throws IOException {
        saveToFile(byteArea, cryptogramArea);
    }

    @FXML protected void setPlaintextClearButton(ActionEvent event) {
        plaintextArea.clear();
    }

    @FXML protected void setCryptogramClearButton(ActionEvent event) {
        cryptogramArea.clear();
    }

    @FXML protected void generateFirstKey(ActionEvent event) {
        firstKey = generateKey(key1Field);
    }

    @FXML protected void generateSecondKey(ActionEvent event) { secondKey = generateKey(key2Field); }

    @FXML protected void generateThirdKey(ActionEvent event) {
        thirdKey = generateKey(key3Field);
    }

    @FXML protected void generateAllKeys(ActionEvent event) {
        firstKey = generateKey(key1Field);
        secondKey = generateKey(key2Field);
        thirdKey = generateKey(key3Field);
    }

    private void openWarningDialog(String text) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Komunikat");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(text);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    @FXML protected int encryptMessage(ActionEvent event) {
        if(key1Field.getText().isEmpty()) {
            openWarningDialog("Brak pierwszego klucza");
            return -1; }
        if(key2Field.getText().isEmpty()) {
            openWarningDialog("Brak drugiego klucza");
            return -1;}
        if(key3Field.getText().isEmpty()) {
            openWarningDialog("Brak trzeciego klucza");
            return -1;}
        if(plaintextArea.toString().isEmpty()) {
            openWarningDialog("Pusta wiadomosc do zakodowania");
            return -1;}
        tripleDES = new TripleDES(BitOperations.stringASCIIToBitSet(key1Field.getText()), BitOperations.stringASCIIToBitSet(key2Field.getText()), BitOperations.stringASCIIToBitSet(key3Field.getText()));
        BitSet message = BitOperations.stringASCIIToBitSet(plaintextArea.getText());
        encrypted = tripleDES.encrypt(message);
        cryptogramArea.setText(BitOperations.bitSetToHex(encrypted));
        return 0;
    }

    @FXML protected int decryptMessage(ActionEvent event) {
        if(key1Field.getText().isEmpty()) {
            openWarningDialog("Brak pierwszego klucza");
            return -1; }
        if(key2Field.getText().isEmpty()) {
            openWarningDialog("Brak drugiego klucza");
            return -1;}
        if(key3Field.getText().isEmpty()) {
            openWarningDialog("Brak trzeciego klucza");
            return -1;}
        if(cryptogramArea.toString().isEmpty()) {
            openWarningDialog("Pusta wiadomosc do zakodowania");
            return -1;}
        tripleDES = new TripleDES(BitOperations.stringASCIIToBitSet(key1Field.getText()), BitOperations.stringASCIIToBitSet(key2Field.getText()), BitOperations.stringASCIIToBitSet(key3Field.getText()));
        String hex = cryptogramArea.getText();
        BitSet bs = BitOperations.hexToBitSet(hex);
        decrypted = tripleDES.decrypt(bs);
        plaintextArea.setText(BitOperations.bitSetToStringASCII(decrypted));
        return 0;
    }

}