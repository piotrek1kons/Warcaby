package com.example.checkers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
//klasa obsługująca okno ustawień
public class SettingsWindowController implements Initializable {
    @FXML
    private Button exitButton;

    //nowe dane:
    @FXML
    private Field newUsernameButton;
    @FXML
    private Field newPasswordButton;
    @FXML
    private Field newConfirmPasswordButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
            }
        });

        //trzeba dodać ustawianie nowych danych
    }
}
