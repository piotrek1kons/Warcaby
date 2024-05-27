package com.example.checkers;

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

    @FXML
    public void signUpButtonOnAction(ActionEvent event){
        System.out.println("Sign up button");
        DBUtils.changeScene(event, "signUpWindowGUI.fxml", "Sign Up!");
    }

    @FXML
    public void logInButtonOnAction(ActionEvent event) {
        System.out.println("Log in button");
        DBUtils.changeScene(event, "logInWindowGUI.fxml", "Log In!");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}