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
import java.net.UnknownHostException;
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
    Thread th;
    ClockThread ct;

    String playerColor;

    // --------
    private String chosenPawn = "NULL", chosenField = "NULL";
    private Rectangle thisField;
    private int squareSize = 50, circleRadius = 18;


    private Color fieldColor = Color.rgb(145, 18, 43);
    private Color fieldColorHighligh = Color.rgb(239, 128, 150);
    private Color pickedPawn = Color.rgb(85, 156, 173);


    private User user;

    public void setUser2(User user) {
        this.user = user;
        username1Label.setText(user.getUsername());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "MainWindowGUI.fxml", "Main Window!");
            }
        });

        Clock clock = new Clock(minuteLabel, secondLabel);
        this.ct = new ClockThread(clock);
        FutureTask ft = new FutureTask<>(ct);
        th = new Thread(ft);
        th.start();


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

    // ONMOUSE EVENTS - RECTANGLE
    public void rectanglePressed(MouseEvent event, Rectangle rect){
        if (rect.getFill().equals(fieldColorHighligh)){
            this.chosenField = rect.getId();
            try {
                if (timeStop) {
                    out.write("NULL;NULL");
                    out.newLine();
                    out.flush();
                } else {
                    out.write(this.chosenField);
                    out.newLine();
                    out.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public Rectangle prostokat(boolean white, int x, int y){
        Rectangle rect = new Rectangle(squareSize, squareSize, squareSize, squareSize);

        if (white) {
            rect.setFill(Color.rgb(251, 243, 228));
        } else {
            rect.setFill(fieldColor);

        }

        String id = positionToString(x,y);
        rect.setId(id);
        rect.setOnMouseClicked(event -> rectanglePressed(event, rect));
        return rect;
    }
    public void createBoard() {
        boolean white = true;

        this.whichPlayerLabel.setText("Wait");

        boardGridPane.setAlignment(Pos.CENTER);
        pawnsGridPane.setAlignment(Pos.CENTER);

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    boardGridPane.add(prostokat(white,col,row), col, row);
                    white = !white;

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
        System.out.print("\n-->" + this.chosenPawn);
        System.out.println(playerColor);

        Color c;
        if (playerColor.equals("white")) {
            c = Color.WHITE;
        }else{
            c = Color.BLACK;
        }

        System.out.println(circle.getFill());
       if(circle.getFill() ==  c && (this.started.equals("START"))){
           try {
               if (timeStop) {
                   out.write("NULL;NULL");
                   out.newLine();
                   out.flush();
               } else {
                   thisField = getRectangleAt(x,y);
                   this.chosenPawn = thisField.getId();
                   System.out.print(this.chosenPawn);
                   circle.setFill(pickedPawn);
                   out.write(this.chosenPawn);
                   out.newLine();
                   out.flush();
               }
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
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
            boolean white = true;

            for (int row = 0; row < 8; row++) {
                String[] arr = board[row].split(" ");
                for (int col = 0; col < 8; col++) {
                    String piece = arr[col];
                        switch (piece) {
                            case "W":
                                pawnsGridPane.add(kolo(Color.WHITE, false, col, row), col, row);
                                break;
                            case "C":
                                pawnsGridPane.add(kolo(Color.BLACK, false, col, row), col, row);
                                break;
                            case "WQ":
                                pawnsGridPane.add(kolo(Color.LIGHTGRAY, true, col, row), col, row);
                                break;
                            case "CQ":
                                pawnsGridPane.add(kolo(Color.DARKGRAY, true, col, row), col, row);
                                break;
                        }

                        white = !white;
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


            String player2 = in.readLine();
            Platform.runLater(() -> this.username2Label.setText(player2));

            out.write("OK");
            out.newLine();
            out.flush();

            playerColor = in.readLine();
            while (cont) {
                // oczekiwanie na swoją kolej
                started = in.readLine();
                System.out.println("Odebrano START");
                System.out.println(started);


                while (started.equals("START")) {

                    Platform.runLater(() -> this.whichPlayerLabel.setText("Your turn"));

                    // 1. odebranie tablicy od servera
                    // oddzielone spacjami ; W->biały ; B->czarny ; WQ->biała królowa ; BQ->czarna królowa ; NULL->puste pole

                    String dlugoscTablicy = in.readLine();
                    System.out.println("dlugosc tablicy: " + dlugoscTablicy);
                    // tu sie odbiera giga gowno przy podwojnym biciu
                    board = odebranieTablicy(in, Integer.parseInt(dlugoscTablicy));
                    System.out.println("Odebrano aktualizację tablicy");

                    if (board != null) {
                        aktualizujBoarda(board);
                    }

                    this.ct.resume();

                    // 2. wysłanie wybranego pionka na serwer (CH;Y) (jeżeli czas sie skończył to "END")
                    // circlePressed
                    while (this.chosenPawn.equals("NULL")) {
                        Thread.sleep(500);
                    }
                    this.chosenPawn = "NULL";

                    if (timeStop) {
                        cont = false;
                        started = "NO";
                        Platform.runLater(() -> this.whichPlayerLabel.setText("You lose!!!"));
                        break;
                    }

                    // 3. pobranie tablicy możliwych ruchów
                    dlugoscTablicy = in.readLine();
                    System.out.println("3. odebrano dlugoscTablicy" + dlugoscTablicy);

                    if (dlugoscTablicy.equals("NULL") || dlugoscTablicy == null) {
                        started = "NO";
                        break;
                    } else {
                        mozliweRuchy = odebranieTablicy(in, Integer.parseInt(dlugoscTablicy));
                        for (String id : mozliweRuchy) {
                            String rectId = "#" + id;
                            Rectangle rec = (Rectangle) boardGridPane.lookup(rectId);
                            rec.setFill(fieldColorHighligh);
                        }
                    }

                    pawnsGridPane.setMouseTransparent(true);

                    // 4. wysłanie wybranego ruchu na serwer (jeżeli czas sie skończył to "END")
                    // rectanglePressed
                    while (this.chosenField.equals("NULL")) {
                        Thread.sleep(500);
                    }
                    this.chosenField = "NULL";

                    if (timeStop) {
                        cont = false;
                        started = "NO";
                        break;
                    }

                    for (String id : mozliweRuchy) {
                        String rectId = "#" + id;
                        Rectangle rec = (Rectangle) boardGridPane.lookup(rectId);
                        rec.setFill(fieldColor);
                    }
                    pawnsGridPane.setMouseTransparent(false);

                    // 5. odebranie aktualizacji tablicy z serwera
                    dlugoscTablicy = in.readLine();
                    System.out.println("5. dlugosc tablicy: " + dlugoscTablicy);
                    board = odebranieTablicy(in, Integer.parseInt(dlugoscTablicy));
                    System.out.println("Odebrano aktualizacje tablicy");
                    if (board != null) {
                        aktualizujBoarda(board);
                    }

                    // 6. if (serwer == NEXT) -> ... else if (serwer == STOP) -> ...
                    // 7. jeśli NEXT: wraca do pkt.
                    // 8. w przeciwnym razie czeka na swoją kolej i też wraca do pkt 1
                    boolean initial = false;
                    czyKolejnyRuch = in.readLine();
                    System.out.println("Sprawdzono czy kolejny ruch jest możliwy -> " + czyKolejnyRuch);
                    while(czyKolejnyRuch.equals("NEXT")){
                        if(initial) {
                            czyKolejnyRuch = in.readLine();
                            System.out.println("Sprawdzono czy kolejny ruch jest możliwy 2+ -> " + czyKolejnyRuch);
                            if(czyKolejnyRuch.equals("STOP")) {
                                break;
                            }
                        }
                        initial = true;
                        // 3. pobranie tablicy możliwych ruchów
                        dlugoscTablicy = in.readLine();
                        System.out.println("3. odebrano dlugoscTablicy" + dlugoscTablicy);

                        if (dlugoscTablicy.equals("NULL") || dlugoscTablicy == null) {
                            started = "NO";
                            break;
                        } else {
                            mozliweRuchy = odebranieTablicy(in, Integer.parseInt(dlugoscTablicy));
                            for (String id : mozliweRuchy) {
                                String rectId = "#" + id;
                                Rectangle rec = (Rectangle) boardGridPane.lookup(rectId);
                                rec.setFill(fieldColorHighligh);
                            }
                        }

                        pawnsGridPane.setMouseTransparent(true);

                        // 4. wysłanie wybranego ruchu na serwer (jeżeli czas sie skończył to "END")
                        // rectanglePressed
                        while (this.chosenField.equals("NULL")) {
                            Thread.sleep(500);
                        }
                        this.chosenField = "NULL";

                        if (timeStop) {
                            cont = false;
                            started = "NO";
                            break;
                        }

                        for (String id : mozliweRuchy) {
                            String rectId = "#" + id;
                            Rectangle rec = (Rectangle) boardGridPane.lookup(rectId);
                            rec.setFill(fieldColor);
                        }
                        pawnsGridPane.setMouseTransparent(false);

                        // 5. odebranie aktualizacji tablicy z serwera
                        dlugoscTablicy = in.readLine();
                        System.out.println("5. dlugosc tablicy: " + dlugoscTablicy);
                        board = odebranieTablicy(in, Integer.parseInt(dlugoscTablicy));
                        System.out.println("Odebrano aktualizacje tablicy");

                        if (board != null) {
                            aktualizujBoarda(board);
                        }

                    }

                    if (czyKolejnyRuch.equals("STOP") || czyKolejnyRuch.equals("NULL")) {
                        // TODO zatrzymanie zegara
                        this.ct.pause();
                        started = "NO";
                        System.out.println("Zatrzymano:  " + started);
                        break;
                    }
                    mozliweRuchy = null;
                }

                Platform.runLater(() -> this.whichPlayerLabel.setText("Wait"));
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
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

    public String[] odebranieTablicy(BufferedReader in, int dlugoscTablicy) throws IOException {
        String[] arr = new String[dlugoscTablicy];
        for (int i=0; i<dlugoscTablicy; i++){
            arr[i] = in.readLine();
            out.write("OK");
            out.newLine();
            out.flush();
            System.out.println("odebranieTablicy:  " + arr[i]);
        }

        return arr;
    }

    // TODO wstawić to do użytkownika
    public void setUser(User user){
        this.user = user;
    }
}
