package com.example.checkers;

import javafx.application.Application;

import java.util.concurrent.Callable;

public class Game implements Callable<Boolean>  {
    private RuchGraczaThread player1;
    private RuchGraczaThread player2;
    private Board board;
/*
    public Game(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
    }
*/

    public Game(RuchGraczaThread player1, RuchGraczaThread player2){
        ServerCondition sc = new ServerCondition(new Board());
        this.player1 = player1;
        this.player2 = player2;

        player1.setServerCondition(sc);
        player2.setServerCondition(sc);
    }
    public Boolean call(){

        try {

            player1.call();
            player2.call();


            // b.setBoard();
            //b.showBoard();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }


    // TODO --------- GETTERY I SETTERY ---------



}
