package univ_lorraine.iut.java.privatechat.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import univ_lorraine.iut.java.privatechat.App;
import univ_lorraine.iut.java.privatechat.model.Contact;

public class AddNewContactController {

    @FXML
    private TextField pseudoTextField;

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField portTextField;

    NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());

    @FXML
    private void navigateBack() throws IOException {
        App.setWindowSize(1310, 760);
        App.setRoot("chat");
    }

    @FXML
    private void processSubmit() throws IOException {
        String pseudo = pseudoTextField.getText();
        String ip = ipTextField.getText();
        String port = portTextField.getText();

        if (!pseudo.isEmpty() && !ip.isEmpty() && !port.isEmpty()) {
            App.setWindowSize(1310, 760);
            Contact newContact = new Contact(pseudo, ip, Integer.parseInt(port));
            App.setRoot("chat", newContact);
        }
    }
}
