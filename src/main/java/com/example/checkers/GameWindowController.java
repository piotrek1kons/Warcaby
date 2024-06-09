package com.example.checkers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
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
    private Label minuteLabel;
    @FXML
    private Label secondLabel;
    @FXML
    private Pane boardPane; //panel z planszą - będzie ciężko :(
    @FXML
    private RowConstraints A,B,C,D,E,F,G,H;
    @FXML
    private GridPane boardGridPane;
    @FXML
    private GridPane pawnsGridPane;

    private User user1;
    private User user2;

    public void setUser(User user1,User user2) {
        this.user1 = user1;
        this.user2 = user2;
        username1Label.setText(user1.getUsername());
        username2Label.setText(user2.getUsername());
    }

    private User user = new User("test",1,2,3);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
            }
        });


        //tutaj można dodawać
//
//        // komunikacja z serwerem
//        int port = 6666;
//        BufferedReader in = null;
//        BufferedWriter out = null;
//        Socket socket = null;
//
//        try {
//            socket = new Socket("127.0.0.1", port);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            String started = "NO";
//            boolean cont = true;
//            boolean timeStop = false;
//            String[] board = null;
//            String wybranyPionek = "";
//            String mozliweRuchy = "";
//            String wybranyRuch = "";
//            String czyKolejnyRuch = "";
//
//            // wysłanie danych użytkownika na serwer
//            out.write(user.toString());
//            out.newLine();
//            out.flush();
//
//            // TODO wątek uruchamiający zegar
//            // TODO zegar odlicza do zera
//            // TODO uruchamia się tuż przed przed pobraniem danych użytkownika
//            // TODO zatrzymuje się po wysłaniu ruchu do serwera
//            // TODO kiedy zegar dojdzie do zera wysyła info do serwera że gra zakończona
//            // TODO serwer powinien wyzerować użytkownikowi wszystkie punkty żeby na pewno przegrał
//
//            while (cont){
//                // oczekiwanie na swoją kolej
//                started = in.readLine();
//                while (started.equals("START")){
//
//                    // 1. odebranie tablicy od servera
//                    // oddzielone spacjami ; W->biały ; B->czarny ; WQ->biała królowa ; BQ->czarna królowa ; NULL->puste pole
//                    board = in.readLine().split(" ");
//                    wybranyPionek = "NULL;NULL";
//
//                    // TODO wyświetlanie tablicy (trzeba będzie wyczyścić poprzednie ustawienia przed żeby się nie nakładało)
//                    aktualizujBoarda(board);
//
//            /*
//                    TODO uruchomienie zegara
//                    TODO wybranie pionka tak żeby był zapisany jako String w formacie CH;Y
//             */
//
//                    // 2. wysłanie wybranego pionka na serwer (CH;Y) (jeżeli czas sie skończył to "END")
//                    if (timeStop){
//                        out.write("NULL;NULL");
//                        out.newLine();
//                        out.flush();
//
//                        cont = false;
//                        started = "NO";
//                        break;
//                    } else {
//                        out.write(wybranyRuch);
//                        out.newLine();
//                        out.flush();
//                    }
//
//                    // 3. pobranie tablicy możliwych ruchów
//                    mozliweRuchy = in.readLine();
//
//                    if (mozliweRuchy.contains("NULL")){
//                        started = "NO";
//                        break;
//                    } else {
//                        /*
//                            TODO tutaj będzie wyświetlanie możliwych ruchów w tablicy (zmiana koloru płytki na danej pozycji)
//                            TODO a potem wybranie pionka tak żeby był zapisany jako String w formacie CH;Y
//                     */
//                    }
//
//                    // 4. wysłanie wybranego ruchu na serwer (jeżeli czas sie skończył to "END")
//
//                    /*
//                            TODO tutaj będzie wybór kolejnego możliwego ruchu
//                     */
//
//
//                    if (timeStop){
//                        out.write("END");
//                        out.newLine();
//                        out.flush();
//
//                        cont = false;
//                        started = "NO";
//                        break;
//                    } else {
//                        out.write(wybranyPionek);
//                        out.newLine();
//                        out.flush();
//                    }
//
//                    // 5. odebranie aktualizacji tablicy z serwera
//                    board = in.readLine().split(" ");
//                    aktualizujBoarda(board);
//
//
//                    // 6. if (serwer == NEXT) -> ... else if (serwer == STOP) -> ...
//                    // 7. jeśli NEXT: wraca do pkt. 1
//                    // 8. w przeciwnym razie czeka na swoją kolej i też wraca do pkt 1
//                    czyKolejnyRuch = in.readLine();
//
//                    if (czyKolejnyRuch.equals("STOP")){
//                        // TODO zatrzymanie zegara
//                        started = "NO";
//                        break;
//                    }
//                }
//            }
//
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (in != null){
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (out != null){
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (socket != null){
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//


        new Thread(this::komunikacjaZServerem).start();
        Clock clock = new Clock(minuteLabel, secondLabel);
        ClockThread ct = new ClockThread(clock);
        FutureTask ft = new FutureTask<>(ct);
        new Thread(ft).start();

        Platform.runLater(() -> createBoard());

        //new Thread(this::komunikacjaZServerem).start();

        //minuteLabel.setLabelFor();
    }

    public void createBoard() {
        boolean white = true;
        //boardGridPane.setGridLinesVisible(true);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle rect = new Rectangle(50, 50, 50, 50);
                if (white) {
                    rect.setFill(Color.rgb(251,243,228));
                } else {
                    rect.setFill(Color.rgb(145,18,43));
                }

                white = !white;
                boardGridPane.add(rect, col, row);
            }
            white = !white;
        }
    }

    /*
    public void aktualizujBoarda(String[] board) {
        gridPane.getChildren().clear();
        createBoard();
        int index = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String piece = board[index++];
                if (!"NULL".equals(piece)) {
                    Rectangle rect = new Rectangle(60, 60);
                    switch (piece) {
                        case "W":
                            rect.setFill(Color.WHITE);
                            break;
                        case "B":
                            rect.setFill(Color.BLACK);
                            break;
                        case "WQ":
                            rect.setFill(Color.LIGHTGRAY);
                            break;
                        case "BQ":
                            rect.setFill(Color.DARKGRAY);
                            break;
                    }
                    gridPane.add(rect, col, row);
                }
            }
        }

     */

    public void komunikacjaZServerem(){
        //createBoard();

        // komunikacja z serwerem
        int port = 6666;
        BufferedReader in = null;
        BufferedWriter out = null;
        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String started = "NO";
            boolean cont = true;
            boolean timeStop = false;
            String[] board = null;
            String wybranyPionek = "";
            String mozliweRuchy = "";
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

            while (cont){
                // oczekiwanie na swoją kolej
                started = in.readLine();
                while (started.equals("START")){

                    // 1. odebranie tablicy od servera
                    // oddzielone spacjami ; W->biały ; B->czarny ; WQ->biała królowa ; BQ->czarna królowa ; NULL->puste pole
                    board = in.readLine().split(" ");
                    wybranyPionek = "NULL;NULL";

                    // TODO wyświetlanie tablicy (trzeba będzie wyczyścić poprzednie ustawienia przed żeby się nie nakładało)
                    aktualizujBoarda(board);

            /*
                    TODO uruchomienie zegara
                    TODO wybranie pionka tak żeby był zapisany jako String w formacie CH;Y
             */

                    // 2. wysłanie wybranego pionka na serwer (CH;Y) (jeżeli czas sie skończył to "END")
                    if (timeStop){
                        out.write("NULL;NULL");
                        out.newLine();
                        out.flush();

                        cont = false;
                        started = "NO";
                        break;
                    } else {
                        out.write(wybranyRuch);
                        out.newLine();
                        out.flush();
                    }

                    // 3. pobranie tablicy możliwych ruchów
                    mozliweRuchy = in.readLine();

                    if (mozliweRuchy.contains("NULL")){
                        started = "NO";
                        break;
                    } else {
                        /*
                            TODO tutaj będzie wyświetlanie możliwych ruchów w tablicy (zmiana koloru płytki na danej pozycji)
                            TODO a potem wybranie pionka tak żeby był zapisany jako String w formacie CH;Y
                     */
                    }

                    // 4. wysłanie wybranego ruchu na serwer (jeżeli czas sie skończył to "END")

                    /*
                            TODO tutaj będzie wybór kolejnego możliwego ruchu
                     */


                    if (timeStop){
                        out.write("END");
                        out.newLine();
                        out.flush();

                        cont = false;
                        started = "NO";
                        break;
                    } else {
                        out.write(wybranyPionek);
                        out.newLine();
                        out.flush();
                    }

                    // 5. odebranie aktualizacji tablicy z serwera
                    board = in.readLine().split(" ");
                    aktualizujBoarda(board);


                    // 6. if (serwer == NEXT) -> ... else if (serwer == STOP) -> ...
                    // 7. jeśli NEXT: wraca do pkt. 1
                    // 8. w przeciwnym razie czeka na swoją kolej i też wraca do pkt 1
                    czyKolejnyRuch = in.readLine();

                    if (czyKolejnyRuch.equals("STOP")){
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

    public void aktualizujBoarda(String[] board){
        int index = 0;
        for (Character ch='A'; ch<='H'; ch++){
            for (int y=1; y<=8; y++){
                switch (board[index]){
                    case "W":
                        // wyświetl białego pionka na pozycji (ch;y)
                        break;
                    case "B":
                        // wyświetl czarnego pionka na pozycji (ch;y)
                        break;
                    case "WQ":
                        // wyświetl białą królową na pozycji (ch;y)
                        break;
                    case "BQ":
                        // wyświetl białą królową na pozycji (ch;y)
                        break;
                    case "NULL":
                        // zostaw (ch;y) puste
                        break;
                }
                index++;
            }
        }
    }

    // TODO wstawić to do użytkownika
    public void setLogin(User user){
        this.user = user;
    }
}
