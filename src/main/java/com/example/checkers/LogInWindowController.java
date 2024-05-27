package com.example.checkers;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInWindowController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button logInButton1;
    @FXML
    private Label warningLabel;

    public void cancelButtonOnAction(ActionEvent event){
        System.out.println("cancel button");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Welcome!");
            }
        });
    }
}
