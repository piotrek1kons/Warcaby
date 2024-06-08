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
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;

    private Statement st;
    private Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInButton1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
//                DataBase db = openDb();
//                String login = null;
//                String password = null;
//                ResultSet rsLogin  = db.executeQuery(st,"SELECT login FROM user where login like \"" + userNameTextField.getText() + "\";");
//                ResultSet rsPassword  = db.executeQuery(st,"SELECT haslo FROM user where haslo like \"" + passwordField.getText() + "\";");
//
//                try {
//                    if(rsLogin.next()){
//                        login = rsLogin.getString(1);
//                    }
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    if(rsPassword.next()){
//                        password = rsPassword.getString(1);
//                    }
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                if(userNameTextField.getText().equals(login)) {
//                    if(passwordField.getText().equals(password)){
//                        warningLabel.setText("Logowanie udane!");
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//
                System.out.println(userNameTextField);
                        DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
//                    }else {
//                        warningLabel.setText("Błędne hasło!");
//                    }
//                }else{
//                    warningLabel.setText("Błędny Login lub hasło!");
//                }


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
