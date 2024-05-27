package com.example.checkers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
public class LogInWindowController {

    @FXML
    private Button cancelButton;

    public void cancelButtonOnAction(ActionEvent event){
        System.out.println("cancel button");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
