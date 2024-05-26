package com.example.checkers;

public class Field {
    private Pawn pawn = null;
    private boolean isWhite;

    public Field(boolean isWhite){  // najpierw tworzymy planszę, potem ustawiamy pionki
        this.isWhite = isWhite;
    }


    // TODO --------- GETTERY I SETTERY ---------
    public void setPawn(Pawn pawn){
        this.pawn = pawn;
    }
    public Pawn getPawn(){
        return this.pawn;
    }
    public boolean getColor(){
        return isWhite;
    }
}
