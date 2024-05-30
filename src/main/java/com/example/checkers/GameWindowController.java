package com.example.checkers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameWindowController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Label username1Label;
    @FXML
    private Label username2Label;
    @FXML
    private Label whichPlayerLabel;
    @FXML
    private Pane boardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
            }
        });
    }
}
