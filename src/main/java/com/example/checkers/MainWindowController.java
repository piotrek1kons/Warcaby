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
    private Button logoutButton;
    @FXML
    private Label usernameLabel;   //nazwa użytkownika musi być zmieniona
    @FXML
    private Label winsScoreLabel;   //liczba wygranych
    @FXML
    private Label defeatsScoreLabel;   //liczba przegranych

    private User user;

    public void setUser(User user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());
        winsScoreLabel.setText(String.valueOf(user.getWins()));
        defeatsScoreLabel.setText(String.valueOf(user.getLost()));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       startGameButton.setOnAction(new EventHandler<ActionEvent>(){     //obsługa przycisku rozpoczecia gry
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "GameWindowGUI.fxml", "Game!");
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
