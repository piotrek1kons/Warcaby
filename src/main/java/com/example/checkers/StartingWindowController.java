package com.example.checkers;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class StartingWindowController implements Initializable
{
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "SignUpWindowGUI.fxml", "Sign Up!");
            }
        });
        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "LogInWindowGUI.fxml", "LogIn Window!");
            }
        });

    }
}