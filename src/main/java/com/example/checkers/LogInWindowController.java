package com.example.checkers;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInWindowController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button logInButton1;
    @FXML
    private Label warningLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    private DataBase db = new DataBase();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()){
                    warningLabel.setText("Please fill all fields!");
                    return;
                }
                String login = db.getDataString("SELECT login FROM user where login like \"" + usernameTextField.getText() + "\";");

                if (login == null) {
                    warningLabel.setText("User does not exist!");
                }else{
                    String password = db.getDataString("SELECT haslo FROM user where login like \"" + usernameTextField.getText() + "\";");
                    if(password.equals(passwordTextField.getText())){
                        warningLabel.setText("Logged in!");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Integer wins = db.getDataInt("SELECT ilosc_wygranych FROM user where login like \"" + usernameTextField.getText() + "\";");
                        Integer losses = db.getDataInt("SELECT ilosc_przegranych FROM user where login like \"" + usernameTextField.getText() + "\";");
                        Integer draws = db.getDataInt("SELECT ilosc_remisow FROM user where login like \"" + usernameTextField.getText() + "\";");
                        User user = new User(usernameTextField.getText(), wins, losses, draws);
                        DBUtils.changeSceneUser(event, "MainWindowGUI.fxml", "Main Window!", user);
                        db.closeConnection(db.getCon(), db.getSt());
                        //DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    warningLabel.setText("Wrong password!");
                }
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
