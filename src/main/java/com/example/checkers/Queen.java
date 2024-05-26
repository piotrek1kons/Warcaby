package com.example.checkers;

public class Queen extends Pawn {

    public Queen(Pawn pawn) {
        super(pawn.isWhite(), pawn.getCh(), pawn.getY());
        super.setQueen(true);
    }

}
