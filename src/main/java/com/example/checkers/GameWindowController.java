package com.example.checkers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.FutureTask;

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
    private Label minuteLabel, secondLabel;
    @FXML
    private GridPane boardGridPane, pawnsGridPane;

    // komunikacja z serwerem
    private int port = 6666;
    private BufferedReader in = null;
    private BufferedWriter out = null;
    private Socket socket = null;
    String started = "NO";
    boolean cont = true, timeStop = false;
    String[] mozliweRuchy = null;

    // --------
    private String chosenPawn = "NULL", chosenField = "NULL";
    private Rectangle thisField;
    private int squareSize = 50, circleRadius = 18;


    private Color fieldColor = Color.rgb(145, 18, 43);
    private Color fieldColorHighligh = Color.rgb(239, 128, 150);
    private Color pickedPawn = Color.rgb(85, 156, 173);


    private User user = new User("test",1,2,3);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
            }
        });

        Clock clock = new Clock(minuteLabel, secondLabel);
        ClockThread ct = new ClockThread(clock);
        FutureTask ft = new FutureTask<>(ct);
        new Thread(ft).start();

        Platform.runLater(() -> createBoard());
        Platform.runLater(() -> ustawienieStartowe());

        new Thread(this::komunikacjaZServerem).start();
    }

    public String positionToString(int x, int y){
        x++;
        String msg = "";
        switch (y) {
            case 0 -> msg += "A;";
            case 1 -> msg += "B;";
            case 2 -> msg += "C;";
            case 3 -> msg += "D;";
            case 4 -> msg += "E;";
            case 5 -> msg += "F;";
            case 6 -> msg += "G;";
            case 7 -> msg += "H;";
        }

        msg += "" + x;

        return msg;
    }

    public void getChosenField(String id, Color color){

        if (color == fieldColor){
            chosenField = id;
        } else {
            chosenField = thisField.getId();
        }
    }

    // ONMOUSE EVENTS - RECTANGLE
    public void rectanglePressed(MouseEvent event, Rectangle rect){
        if (rect.getFill().equals(fieldColorHighligh)){
            chosenField = rect.getId();

            try {
                if (timeStop) {
                    out.write("NULL;NULL");
                    out.newLine();
                    out.flush();
                } else {
                    out.write(chosenField);
                    out.newLine();
                    out.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void createBoard() {
        boolean white = true;

        boardGridPane.setAlignment(Pos.CENTER);
        pawnsGridPane.setAlignment(Pos.CENTER);

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Rectangle rect = new Rectangle(squareSize, squareSize, squareSize, squareSize);

                    if (white) {
                        rect.setFill(Color.rgb(251, 243, 228));
                    } else {
                        rect.setFill(fieldColor);

                    }

                    white = !white;

                    String id = positionToString(col,row);
                    rect.setId(id);
                    rect.setOnMousePressed(event -> rectanglePressed(event, rect));
                    boardGridPane.add(rect, col, row);
                }
                white = !white;
            }
    }

    public Rectangle getRectangleAt(int x, int y) {
        String rectId = "#" + positionToString(x, y);
        return (Rectangle) boardGridPane.lookup(rectId);
    }

    // ONMOUSE EVENTS - CIRCLE
    public void circlePressed(MouseEvent event, int x, int y, Circle circle){

        try {
            if (timeStop) {
                out.write("NULL;NULL");
                out.newLine();
                out.flush();
            } else {
                thisField = getRectangleAt(x,y);
                chosenPawn = thisField.getId();
                circle.setFill(pickedPawn);

                out.write(chosenPawn);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public StackPane kolo(Color color, boolean isQueen, int x, int y){
        Circle circle = new Circle(circleRadius);
        StackPane stackPane = new StackPane();
        Label queenLabel = new Label("Q");

        circle.setFill(color);

        circle.setOnMousePressed(event -> circlePressed(event, x,y, circle));

        stackPane.getChildren().add(circle);

        if (isQueen){
            stackPane.getChildren().add(queenLabel);
        }

        return stackPane;
    }

    public void aktualizujBoarda(String[] board) {
        Platform.runLater(() -> {
            pawnsGridPane.getChildren().clear();

            int index = 0;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    String piece = board[index++];
                    if (!"NULL".equals(piece)) {

                        switch (piece) {
                            case "W":
                                pawnsGridPane.add(kolo(Color.WHITE, false, col, row), col, row);
                                break;
                            case "B":
                                pawnsGridPane.add(kolo(Color.BLACK, false, col, row), col, row);
                                break;
                            case "WQ":
                                pawnsGridPane.add(kolo(Color.LIGHTGRAY, true, col, row), col, row);
                                break;
                            case "BQ":
                                pawnsGridPane.add(kolo(Color.DARKGRAY, true, col, row), col, row);
                                break;
                        }
                    }
                }
            }
        });
    }

    public void ustawienieStartowe(){
        boolean pawn = false;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if (pawn){
                    pawnsGridPane.add(kolo(Color.BLACK, false, col, row), col, row);
                }
                pawn = !pawn;
            }
            pawn = !pawn;
        }

        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (pawn){
                    pawnsGridPane.add(kolo(Color.WHITE, false, col, row), col, row);
                }
                pawn = !pawn;
            }
            pawn = !pawn;
        }
    }

    public void komunikacjaZServerem(){
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


            String[] board = null;
            String wybranyPionek = "";
            String wybranyRuch = "";
            String czyKolejnyRuch = "";

            // wysłanie danych użytkownika na serwer
            out.write(user.toString());
            out.newLine();
            out.flush();

            // TODO wątek uruchamiający zegar
            // TODO zegar odlicza do zera
            // TODO uruchamia się tuż przed przed pobraniem danych użytkownika
            // TODO zatrzymuje się po wysłaniu ruchu do serwera
            // TODO kiedy zegar dojdzie do zera wysyła info do serwera że gra zakończona
            // TODO serwer powinien wyzerować użytkownikowi wszystkie punkty żeby na pewno przegrał

            while (cont) {
                // oczekiwanie na swoją kolej
                started = in.readLine();
                System.out.println("Odebrano START");

                while (started.equals("START")) {
                    // 1. odebranie tablicy od servera
                    // oddzielone spacjami ; W->biały ; B->czarny ; WQ->biała królowa ; BQ->czarna królowa ; NULL->puste pole
                    board = in.readLine().split(" ");
                    System.out.println("Odebrano aktualizację tablicy");

                    if (board != null) {
                        aktualizujBoarda(board);
                    }

            /*
                    TODO uruchomienie zegara
             */

                    // 2. wysłanie wybranego pionka na serwer (CH;Y) (jeżeli czas sie skończył to "END")
                    // circlePressed
                    if (timeStop) {
                        cont = false;
                        started = "NO";
                        break;
                    }
                        // 3. pobranie tablicy możliwych ruchów
                        mozliweRuchy = in.readLine().split(" ");

                        if (mozliweRuchy[0].contains("NULL")) {
                            started = "NO";
                            break;
                        } else {
                            for (String id : mozliweRuchy){
                                String rectId = "#" + id;
                                Rectangle rec = (Rectangle) boardGridPane.lookup(rectId);
                                rec.setFill(fieldColorHighligh);
                            }
                        }

                        // 4. wysłanie wybranego ruchu na serwer (jeżeli czas sie skończył to "END")
                        // rectanglePressed
                        if (timeStop) {
                            cont = false;
                            started = "NO";
                            break;
                        }

                        // 5. odebranie aktualizacji tablicy z serwera
                        board = in.readLine().split(" ");
                        if (board != null) {
                            aktualizujBoarda(board);
                        }


                        // 6. if (serwer == NEXT) -> ... else if (serwer == STOP) -> ...
                        // 7. jeśli NEXT: wraca do pkt. 1
                        // 8. w przeciwnym razie czeka na swoją kolej i też wraca do pkt 1
                        czyKolejnyRuch = in.readLine();

                        if (czyKolejnyRuch.equals("STOP")) {
                            // TODO zatrzymanie zegara
                            started = "NO";
                            break;
                        }
                    }
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    // TODO wstawić to do użytkownika
    public void setUser(User user){
        this.user = user;
    }
}
