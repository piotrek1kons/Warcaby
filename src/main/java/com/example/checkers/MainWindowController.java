package com.example.checkers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
//klasa obsługująca okno główne z menu
public class MainWindowController implements Initializable {
    @FXML
    private Button startGameButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label userNameLabel;   //nazwa użytkownika musi być zmieniona
    @FXML
    private Label winsScoreLabel;   //liczba wygranych
    @FXML
    private Label defeatsScoreLabel;   //liczba przegranych

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       startGameButton.setOnAction(new EventHandler<ActionEvent>(){     //obsługa przycisku rozpoczecia gry
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "GameWindowGUI.fxml", "Game!");
            }
       });

        settingsButton.setOnAction(new EventHandler<ActionEvent>(){     //obsługa przycisku ustawien
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "SettingsWindowGUI.fxml", "Settings!");
            }
        });

        logoutButton.setOnAction(new EventHandler<ActionEvent>(){     //obsługa przycisku ustawien
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Welcome!");
            }
        });

        //tutaj można dodawać
    }
}
