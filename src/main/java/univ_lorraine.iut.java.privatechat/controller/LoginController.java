package univ_lorraine.iut.java.privatechat.controller;

import java.io.*;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import univ_lorraine.iut.java.privatechat.App;
import univ_lorraine.iut.java.privatechat.Serveur;
import univ_lorraine.iut.java.privatechat.model.PasswordHasher;

public class LoginController {

    private final File dataDir = new File("data");

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwdField;
    private Thread serverThread;

    @FXML
    private Button signInButton;

    private static final String EXTENSION = ".pwd";

    private boolean validUsername(String user) {
        return user != null && !user.isEmpty();
    }

    private boolean validPassword(String passwd) {
        return passwd != null && passwd.length() >= 8;
    }

    // Vérifie si le mot de passe est correct pour un utilisateur donné
    private boolean verifyPassword(String login, String passwd) {
        try (BufferedReader read = new BufferedReader(new FileReader(login + EXTENSION))) {

            String readLn = read.readLine();
            String salt = "sSQD4sq45dSQDq2a";
            String requiredLn = "password=" + PasswordHasher.hashPassword(passwd, salt);
            if (requiredLn.equals(readLn)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // Gère la connexion et la création de compte
    @FXML
    private void login() throws Exception {
        String user = userField.getText();
        String passwd = passwdField.getText();
        String salt = "sSQD4sq45dSQDq2a";
        String hashedPasswd = PasswordHasher.hashPassword(passwd, salt);

        // Vérifie si le nom d'utilisateur est valide
        if (!validUsername(user)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid username");
            alert.showAndWait();
            return;
        }

        // Vérifie si le mot de passe est valide
        if (!validPassword(passwd)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Password must be at least 8 characters");
            alert.showAndWait();
            return;
        }

        // Vérifie si le compte existe déjà dans la base de données
        File passwdFile = new File(user + EXTENSION);
        if (passwdFile.exists()) {
            // Vérifie si le mot de passe est correct
            if (!verifyPassword(user, passwd)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Incorrect password");
                alert.showAndWait();
                return;
            }
        } else {
            // Crée le fichier de mot de passe avec BufferedWriter
            try (BufferedWriter write = new BufferedWriter(new FileWriter(passwdFile))) {
                write.write("password=" + hashedPasswd + "\rsalt=" + salt + "\riv=" + PasswordHasher.saveIv());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Lance le chat
        App.setUser(user);
        App.setWindowSize(1310, 760);

        // Lance le SERVEUR
        serverThread = new Thread(new Serveur());
        serverThread.start();

        App.setRoot("chat");
    }
}
