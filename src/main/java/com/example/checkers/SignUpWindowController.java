package com.example.checkers;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpWindowController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label warningLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField confirmPasswordTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DataBase db = new DataBase();
                if(usernameTextField.getText().isEmpty()|| passwordTextField.getText().isEmpty() || confirmPasswordTextField.getText().isEmpty()){
                    warningLabel.setText("Please fill all fields!");
                    return;
                }
                String login = db.getDataString("SELECT login FROM user where login like \"" + usernameTextField.getText() + "\";");
                String password = passwordTextField.getText();
                String confirmPassword = confirmPasswordTextField.getText();
                if (login != null) {
                    warningLabel.setText("User already exists!");
                }else if(password.equals(confirmPassword)){
                    db.executeUpdate(db.getSt(),"INSERT INTO user (login, haslo, ilosc_wygranych, ilosc_przegranych, ilosc_remisow) VALUES (\"" + usernameTextField.getText() + "\", \"" + passwordTextField.getText() + "\", 0, 0, 0);");
                    warningLabel.setText("User created!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    User user = new User(usernameTextField.getText(), 0,0,0);
                    db.closeConnection(db.getCon(), db.getSt());
                    DBUtils.changeSceneUser(event, "MainWindowGUI.fxml", "Main Window!", user);
                }else{
                    warningLabel.setText("Passwords do not match!");
                }
                }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Welcome!");
            }
        });

        //tutaj można dodawać
    }
}
