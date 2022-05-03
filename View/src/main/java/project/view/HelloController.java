package project.view;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import javax.swing.JFileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    private BitSet fromString(String in) {
        if (in.isEmpty()) {
            return new BitSet();
        }
        StringBuilder inBuilder = new StringBuilder(in);
        String reversedIn = inBuilder.reverse().toString();

        BitSet bits = new BitSet(in.length());

        for (int i = 0; i < in.length(); i++) {
            if (reversedIn.charAt(i) == '1') {
                bits.set(i);
            }
        }

        return bits;
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
        String keyString = key.getKey();
        textField.setText(keyString);
        return fromString(keyString);
    }

    @FXML protected void initialize() {
        key = new Key();
    }

    @FXML protected void loadTextFromFile() throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            byteArea = getByteAreaFromFile(selectedFile);
            plaintextString = new String(byteArea,StandardCharsets.UTF_8);
            plaintextArea.setText(plaintextString);
            //dodać zapis String.ToByteArea
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
            //dodać zapis String.ToByteArea
            //szyfrujemy wszystko jako pliki binarne - nieważne co to jest pdf, etc
            //ostatni blok uzupełnić zerami (bloki muszą być pełne)
            //po szufrowaniu musi miec taką długość jak przed - odciąć wyzerowany blok (dane w nagłóowku musz sie zgadzać z fiz reprezentacją pliku)
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

    @FXML protected void generateSecondKey(ActionEvent event) {
        secondKey = generateKey(key2Field);
    }

    @FXML protected void generateThirdKey(ActionEvent event) {
        thirdKey = generateKey(key3Field);
    }

}