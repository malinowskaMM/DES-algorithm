package project.view;
import java.io.*;
import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.BitSet;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.swing.JFileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.model.*;

public class HelloController extends Window {
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
    private FileChooser fileChooser = new FileChooser();
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
        byteArea = textBuilder.toString().getBytes(StandardCharsets.UTF_8);
        //uważać na błedy z buforem - czy na pewno on jest pusty przy ponownym ładowaniu
        return byteArea;
    }

    private void saveToFile(byte[] byteArray, TextArea textArea) throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            File file = new File(String.valueOf(selectedFile));
            try (FileWriter fw = new FileWriter(file)) {
                byteArray = textArea.getText().getBytes(StandardCharsets.UTF_8);
                fw.write(new String(byteArray,StandardCharsets.UTF_8));
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

    @FXML public void encryptFile(ActionEvent actionEvent) {
        //AES -> Anon
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(this);
        try {
            byte[] fileBytes = FileUtils.readFileToByteArray(selectedFile);

            tripleDES = new TripleDES(BitOperations.stringASCIIToBitSet(key1Field.getText()), BitOperations.stringASCIIToBitSet(key2Field.getText()), BitOperations.stringASCIIToBitSet(key3Field.getText()));
            BitSet encryptedBS = tripleDES.encrypt(BitSet.valueOf(fileBytes));
            byte[] encodedBytes = encryptedBS.toByteArray();

            File destination = fileChooser.showSaveDialog(this);
            FileUtils.writeByteArrayToFile(destination, encodedBytes);
//            notificationEncryptionLabel.setTextFill(Color.web("#00FF00"));
//            notificationEncryptionLabel.setText("File encrypted successfully");
        } catch (IOException e) {
            e.printStackTrace();
//            notificationEncryptionLabel.setTextFill(Color.web("#FF0000"));
//            notificationEncryptionLabel.setText("Error occured :(");
        }
    }

    @FXML public void decryptFile(ActionEvent actionEvent) {
        // DES->Anon
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(this);
        try {
            byte[] fileBytes = FileUtils.readFileToByteArray(selectedFile);

            tripleDES = new TripleDES(BitOperations.stringASCIIToBitSet(key1Field.getText()), BitOperations.stringASCIIToBitSet(key2Field.getText()), BitOperations.stringASCIIToBitSet(key3Field.getText()));
            BitSet decryptedBS = tripleDES.decrypt(BitSet.valueOf(fileBytes));
            byte[] decodedBytes = decryptedBS.toByteArray();

            File destination = fileChooser.showSaveDialog(this);
            FileUtils.writeByteArrayToFile(destination, decodedBytes);
//            notificationDecryptionLabel.setTextFill(Color.web("#00FF00"));
//            notificationDecryptionLabel.setText("File decrypted successfully");
        } catch (IOException e) {
            e.printStackTrace();
//            notificationDecryptionLabel.setTextFill(Color.web("#FF0000"));
//            notificationDecryptionLabel.setText("Error occured :(");
        }
    }
    @FXML private void encryptFileAnon() throws IOException {
        // DES->Anon
        try {
            File toOpenFile = fileChooser.showOpenDialog(this);
//            if (valueKeyText.getText().equals("")) {
//                valueKeyText.setText(DES.generateKeys());
//            }
            if(key1Field.getText().isEmpty() || key2Field.getText().isEmpty() || key3Field.getText().isEmpty())
                generateAllKeys(null);
            tripleDES = new TripleDES(BitOperations.stringASCIIToBitSet(key1Field.getText()), BitOperations.stringASCIIToBitSet(key2Field.getText()), BitOperations.stringASCIIToBitSet(key3Field.getText()));
            File newFile = fileChooser.showSaveDialog(this);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] b =  Files.readAllBytes(toOpenFile.toPath());
             BitSet bitSetEnc = tripleDES.encrypt(BitSet.valueOf(b));
            fos.write(bitSetEnc.toByteArray());
            fos.close();
//            nameOfFileWithoutEncryption.setText(toOpenFile.getAbsolutePath());
//            nameOfFileEncryptedFile.setText(newFile.getAbsolutePath());
//            komunikat2.setText("Zaszyfrowany plik zapisano do: " + newFile.getName());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML private void decryptFileAnon() {
        // DES->Anon
        try {
            File openFile = fileChooser.showOpenDialog(this);
//            if (valueKeyText.getText().equals("")) {
//                valueKeyText.setText(DES.generateKeys());
//            }
            File newFile = fileChooser.showSaveDialog(this);
            FileOutputStream fos = new FileOutputStream(newFile);

            tripleDES = new TripleDES(BitOperations.stringASCIIToBitSet(key1Field.getText()), BitOperations.stringASCIIToBitSet(key2Field.getText()), BitOperations.stringASCIIToBitSet(key3Field.getText()));
            byte[] b =  Files.readAllBytes(openFile.toPath());
            BitSet bitSetEc = tripleDES.encrypt(BitSet.valueOf(b));

            fos.write(bitSetEc.toByteArray());
            fos.close();
//            nameOfFileWithoutEncryption1.setText(openFile.getAbsolutePath());
//            nameOfFileEncryptedFile1.setText(newFile.getAbsolutePath());
//            komunikat1.setText("Odszyfrowany plik zapisano do: " + newFile.getName());
        } catch (NullPointerException | IOException e) {
//            komunikat2.setText("Nie można odczytać pliku");
        }
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
            firstKey = BitOperations.stringASCIIToBitSet(fromFile.substring(0, 16));
            key1Field.setText(fromFile.substring(0, 16));
            secondKey = BitOperations.stringASCIIToBitSet(fromFile.substring(16, 32));
            key2Field.setText(fromFile.substring(16, 32));
            thirdKey = BitOperations.stringASCIIToBitSet(fromFile.substring(32, 48));
            key3Field.setText(fromFile.substring(32, 48));
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

            BitSet bs = BitSet.valueOf(byteArea);
            String s = BitOperations.bitSetToStringASCII(bs);
            plaintextArea.setText(s);
//            plaintextString = new String(byteArea,StandardCharsets.UTF_8);
//            plaintextArea.setText(plaintextString);
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