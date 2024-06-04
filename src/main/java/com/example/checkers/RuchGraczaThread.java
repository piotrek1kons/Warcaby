package com.example.checkers;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;

public class RuchGraczaThread implements Callable<Player> {
    private ServerCondition serverCondition;
    private Player player;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Boolean isTurn;
    private Boolean isWhite;
    private Board board;

    public RuchGraczaThread(Socket socket){
        this.socket = socket;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Player call() throws Exception {

        // TODO pobranie od klienta loginu i hasła gracza i utworzenie obiektu użytkownika
        String[] daneUzytkownika = in.readLine().split(";");
        player  = new Player(new User(daneUzytkownika[0], Integer.parseInt(daneUzytkownika[1]), Integer.parseInt(daneUzytkownika[2]), Integer.parseInt(daneUzytkownika[3])),isWhite);

        board.showBoard();

        // TODO gra się zakończy gdy gracz otrzyma max punktów lub upłynie czas poświęcony na grę
        // TODO (np 30 minut - maybe jakieś odliczanie po stronie klienta ?? )
        // TODO Wątek z odliczaniem na kliencie

        boolean isGameOn = true;

        while (isGameOn){
            isGameOn = serverCondition.wykonajRuch(in, out, player);
        }

        return player;
    }

    // TODO --------- GETTERY I SETTERY ---------
    public void setServerCondition(ServerCondition sc){
        this.serverCondition = sc;
    }
    public void setBoard(Board b){
        this.board = b;
    }
    public void setIsWhite(Boolean isWhite){
        this.isWhite = isWhite;
    }
}
