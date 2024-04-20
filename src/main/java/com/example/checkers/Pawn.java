package com.example.checkers;

public class Pawn {
    private boolean isWhite;
    private boolean isDead;
    private boolean isQueen;
    private Character ch;
    private Integer y;

    public Pawn(boolean isWhite, Character ch, Integer y){
            this.isWhite = isWhite;
            this.isDead = false;
            this.isQueen = false;
            this.ch = ch;
            this.y = y;
    }


    public void avancement(){
        if(isQueen){
            System.out.println("Królowa");
        }else{
            System.out.println("Pionek");
        }
    }



    public void kill(){
        isDead = true;
    }

    public void move(){};
    public boolean isWhite() {
        return isWhite;
    }
}
