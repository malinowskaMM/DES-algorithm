package project.view;
import java.io.*;
import javax.swing.JFileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    StringBuilder plaintextStringBuilder;
    StringBuilder cryptogramStringBuilder;
    String plaintextString;
    String cryptogramString;



    @FXML
    private TextField keyField;
    @FXML
    private TextArea plaintextArea;
    @FXML
    private TextArea cryptogramArea;


    @FXML
    private Button keyGenerateButton;
    @FXML
    private Button keySaveButton;
    @FXML
    private Button keyLoadButton;
    @FXML
    private Button encryptButton;
    @FXML
    private Button decryptButton;
    @FXML
    private Button plaintextLoadButton;
    @FXML
    private Button plaintextSaveButton;
    @FXML
    private Button cryptogramLoadButton;
    @FXML
    private Button cryptogramSaveButton;
    @FXML
    private Button plaintextClearButton;
    @FXML
    private Button cryptogramClearButton;

    private StringBuilder getTextFromFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        StringBuilder textBuilder = new StringBuilder();
        int i;
        while( (i=fileReader.read()) != -1 ) {
            textBuilder.append((char)i);
        }
        return textBuilder;
    }

    private void saveToFile(TextArea textArea) throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            File file = new File(String.valueOf(selectedFile));
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(textArea.getText());
                fw.flush();
            }
        }
    }

//    @FXML
//    public void initialize() {
//        JFileChooser jfc = new JFileChooser();
//    }


    @FXML
    protected void loadTextFromFile(ActionEvent event) throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            plaintextStringBuilder = getTextFromFile(selectedFile);
            plaintextString = plaintextStringBuilder.toString();
            plaintextArea.setText(plaintextString);
        }
    }

    @FXML
    protected void loadCryptogramFromFile(ActionEvent event) throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            cryptogramStringBuilder = getTextFromFile(selectedFile);
            cryptogramString = cryptogramStringBuilder.toString();
            cryptogramArea.setText(cryptogramString);
        }
    }

    @FXML
    protected void saveTextFromPlaintextWindow(ActionEvent event) throws IOException {
        saveToFile(plaintextArea);
    }

    @FXML
    protected void saveTextFromCryptogramWindow(ActionEvent event) throws IOException {
        saveToFile(cryptogramArea);
    }

    @FXML
    protected void setPlaintextClearButton(ActionEvent event) {
        plaintextArea.clear();
    }

    @FXML
    protected void setCryptogramClearButton(ActionEvent event) {
        cryptogramArea.clear();
    }

}