package com.example.checkers;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Button signUpButton;
    private Button logInButton;

    public void signUpButtonOnAction(ActionEvent event){
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();
    }



    @FXML
    protected void onHelloButtonClick() {
        //welcomeText.setText("Welcome to JavaFX Application!");
    }
}