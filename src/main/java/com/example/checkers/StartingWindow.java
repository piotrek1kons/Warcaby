package com.example.checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartingWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartingWindow.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Welcome!");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        //Board b = new Board();
        //b.setBoard();
        //b.showBoard();


        launch();
    }
}