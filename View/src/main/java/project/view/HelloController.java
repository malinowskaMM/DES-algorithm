package project.view;
import java.io.*;

import javax.swing.JFileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    StringBuilder plainTextStringBuilder;
    String plainTextString;
    @FXML
    private TextField key;
    @FXML
    private TextArea plaintext;
    @FXML
    private TextArea cryptogram;

    @FXML
    private RadioButton file;
    @FXML
    private RadioButton window;

    @FXML
    private Button choosen;
    @FXML
    private Button keyGenerate;
    @FXML
    private Button keySave;
    @FXML
    private Button keyLoad;
    @FXML
    private Button encrypt;
    @FXML
    private Button decrypt;
    @FXML
    private Button plaintextLoad;
    @FXML
    private Button plaintextSave;
    @FXML
    private Button cryptogramLoad;
    @FXML
    private Button cryptogramSave;
    @FXML
    private Button clearPlaintext;
    @FXML
    private Button clearCryptogram;

    @FXML
    protected void onFileRadioButtonSelected() {
        file.setDisable(true);
        window.setDisable(true);
        plaintext.setDisable(true);
        cryptogram.setDisable(true);
    }

    @FXML
    protected void onWindowRadioButtonSelected() {
        file.setDisable(true);
        window.setDisable(true);
        plaintextLoad.setDisable(true);
        cryptogramLoad.setDisable(true);
    }

    @FXML
    protected void clearSelected(ActionEvent event) {
        file.setSelected(false);
        window.setSelected(false);
        file.setDisable(false);
        window.setDisable(false);
        plaintext.setDisable(false);
        cryptogram.setDisable(false);
        plaintextLoad.setDisable(false);
        cryptogramLoad.setDisable(false);
    }

    @FXML
    protected void loadTextFromFile(ActionEvent event) throws IOException {
        JFileChooser jfc = new JFileChooser();

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            FileReader fileReader = new FileReader(selectedFile);
            plainTextStringBuilder = new StringBuilder();
            int i;
            while( (i=fileReader.read()) != -1 ) {
                plainTextStringBuilder.append((char)i);
            }
            plainTextString=plainTextStringBuilder.toString();
            plaintext.setText(plainTextString);
        }
    }

    @FXML
    protected void saveTextFromPlaintextWindow(ActionEvent event) {
        File dir = null;
        JFileChooser fc = new JFileChooser();
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            dir = fc.getSelectedFile();
        }
        plainTextString= plaintext.getText();
        File file = new File(String.valueOf(dir));
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(plainTextString.toString());
            fw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if(fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    @FXML
    protected void setClearPlaintext(ActionEvent event) {
        plaintext.clear();
        plainTextStringBuilder.delete(0,plainTextStringBuilder.length()-1);
    }

    @FXML
    protected void setClearCryptogram(ActionEvent event) {
        cryptogram.clear();
    }

}