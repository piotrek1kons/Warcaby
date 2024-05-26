package com.example.checkers;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;

public class RuchGraczaThread implements Callable<Boolean> {
    private ServerCondition serverCondition;
    private Player player;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Boolean isTurn;
    private Board board;

    public RuchGraczaThread(Socket socket){
        //this.serverCondition = sc;
        this.socket = socket;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Boolean call() throws Exception {

        // TODO pobranie od klienta loginu i hasła gracza

        // TODO pobranie danych gracza z bazy i utworzenie obiektu użytkownika

        // TODO można zobaczyć czy nie lepszym od Conditiona nie byłby Exchanger bo on 2 wątki obsługuje, ale
        // TODO nie wiem co by miały wymieniać


        return null;
    }

    // TODO --------- GETTERY I SETTERY ---------
    public void setServerCondition(ServerCondition sc){
        this.serverCondition = sc;
    }
    public void setBoard(Board b){
        this.board = b;
    }
}
