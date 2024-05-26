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

    // TODO musi zmienić planszę na to żeby Pawn na polu Field było NULL ale nw jak to zrobić
    public void kill(){
        isDead = true;
    }

    // TODO to chyba można zrobić inaczej
    public void move(Character ch, int y){
        this.ch = ch;
        this.y = y;
    }


    // TODO --------- GETTERY I SETTERY ---------
    public boolean isWhite() {
        return isWhite;
    }
    public Character getCh() {
        return ch;
    }
    public Integer getY() {
        return y;
    }
    public void setQueen(boolean isQueen){
        this.isQueen = isQueen;
    }
}
