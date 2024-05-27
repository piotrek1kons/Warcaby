package com.example.checkers;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class StartingWindowController
{
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;

    @FXML
    public void signUpButtonOnAction(ActionEvent event){
        System.out.println("Sign up button");
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();
    }



    @FXML
    public void logInButtonOnAction(ActionEvent event) {
        System.out.println("Log in button");
        Stage stage = (Stage) logInButton.getScene().getWindow();
        stage.close();
    }

}