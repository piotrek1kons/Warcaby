package com.example.checkers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//klasa obsługująca zmiane sceny wszystkie controllery z niej korzystają
public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title){
        Parent root = null;
        try{
            root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
        }catch (IOException e){
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void changeSceneUser(ActionEvent event, String fxmlFile, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();

            MainWindowController mainWindowController = loader.getController();
            mainWindowController.setUser(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeSceneUser2(ActionEvent event, String fxmlFile, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();

            GameWindowController gameWindowController = loader.getController();
            gameWindowController.setUser2(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //public static void signUpUser(ActionEvent event, String username, String password, String )
}
