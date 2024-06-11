package com.example.checkers;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;

public class RuchGraczaThread implements Callable<Player> {
    private ServerCondition serverCondition;
    private Exchanger<String> exchanger;
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
        try{
            String player2nlogin = exchanger.exchange(daneUzytkownika[0]);

            out.write(player2nlogin);
            out.newLine();
            out.flush();
            String ask = in.readLine();

        } catch (InterruptedException e){
            e.printStackTrace();
        }

        player  = new Player(new User(daneUzytkownika[0], Integer.parseInt(daneUzytkownika[1]), Integer.parseInt(daneUzytkownika[2]), Integer.parseInt(daneUzytkownika[3])),isWhite);

        if (player.isWhite().equals("black")){
            Thread.sleep(100);
        }

        System.out.println("GRACZ DOSTAJE KOLOR" + player.isWhite());
        out.write(player.isWhite());
        out.newLine();
        out.flush();
        // TODO gra się zakończy gdy gracz otrzyma max punktów lub upłynie czas poświęcony na grę
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

    public void setExchanger(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    public void setBoard(Board b){
        this.board = b;
    }
    public void setIsWhite(Boolean isWhite){
        this.isWhite = isWhite;
    }
    public boolean getIsWhite(){
        return isWhite;
    }
    public  String getPlayer(){
        return player.getUser().getUsername();
    }
}
