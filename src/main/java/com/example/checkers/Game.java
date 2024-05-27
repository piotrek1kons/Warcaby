package com.example.checkers;

import javafx.application.Application;

import java.util.concurrent.*;

public class Game implements Callable<Boolean>  {
    private RuchGraczaThread player1;
    private RuchGraczaThread player2;

    public Game(RuchGraczaThread player1, RuchGraczaThread player2){
        ServerCondition sc = new ServerCondition(new Board());
        this.player1 = player1;
        this.player2 = player2;

        player1.setServerCondition(sc);
        player2.setServerCondition(sc);

        player1.setIsWhite(true);
        player2.setIsWhite(false);
    }
    public Boolean call(){
        ExecutorService exec = Executors.newFixedThreadPool(2);

        final Player[] playersResult = new Player[2];

        try {
            player1.call();
            player2.call();
            exec.submit(new FutureTask<>(player1){
                protected void done(){
                    try{
                        playersResult[0] = get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            exec.submit(new FutureTask<>(player2){
                protected void done(){
                    try{
                        playersResult[1] = get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (playersResult == null){
            return false;

        } else if (playersResult[0].getPoints() > playersResult[1].getPoints()) {
            playersResult[0].getUser().setWins();
            playersResult[1].getUser().setLost();

        } else if (playersResult[0].getPoints() < playersResult[1].getPoints()){
            playersResult[1].getUser().setWins();
            playersResult[0].getUser().setLost();

        } else {
            playersResult[0].getUser().setDraws();
            playersResult[1].getUser().setDraws();
        }

        return true;
    }


    // TODO --------- GETTERY I SETTERY ---------



}
