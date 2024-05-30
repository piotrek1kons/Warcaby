package com.example.checkers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private Button startGameButton;
    @FXML
    private Button logoutButton;

    @FXML
    public void setStartGameButtonOnAction(ActionEvent event){
        System.out.println("Start game button");
        DBUtils.changeScene(event, "GameWindowGUI.fxml", "Game!");
    }

    @FXML
    public void logoutButtonOnAction(ActionEvent event) {
        System.out.println("Logout button");
        DBUtils.changeScene(event, "hello-view.fxml", "Welcome!");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
