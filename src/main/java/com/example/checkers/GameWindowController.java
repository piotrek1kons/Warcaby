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
//klasa obsuguje okno z grą
public class GameWindowController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Label username1Label;   //nazwa urzytkownika - trzeba będzie ustawić nazwe
    @FXML
    private Label username2Label;   //nazwa urzytkownika - trzeba będzie ustawić nazwe
    @FXML
    private Label whichPlayerLabel; //nazwa urzytkownika którego teraz jest tura - trzeba będzie zmieniac nazwe przy kazdej kolejce
    @FXML
    private Pane boardPane; //panel z planszą - będzie ciężko :(

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
            }
        });

        //tutaj można dodawać
    }
}
