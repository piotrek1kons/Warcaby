package com.example.checkers;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                String login = db.getData("SELECT login FROM user where login like \"" + usernameTextField.getText() + "\";");

                if (login == null) {
                    warningLabel.setText("User does not exist!");
                }else{
                    String password = db.getData("SELECT haslo FROM user where login like \"" + usernameTextField.getText() + "\";");
                    if(password.equals(passwordTextField.getText())){
                        warningLabel.setText("Logged in!");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Integer wins = Integer.parseInt(db.getData("SELECT ilosc_wygranych FROM user where login like \"" + usernameTextField.getText() + "\";"));
                        Integer losses = Integer.parseInt(db.getData("SELECT ilosc_przegranych FROM user where login like \"" + usernameTextField.getText() + "\";"));
                        Integer draws = Integer.parseInt(db.getData("SELECT ilosc_remisow FROM user where login like \"" + usernameTextField.getText() + "\";"));
                        User user = new User(usernameTextField.getText(), wins, losses, draws);
                        db.closeConnection(db.getCon(), db.getSt());
                        DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
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
