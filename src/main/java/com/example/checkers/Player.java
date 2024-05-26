package com.example.checkers;

public class Player {
    private User user;
    private int points;
    private boolean isWhite;
    private boolean isTurn;

    public Player(User user, boolean isWhite){
        this.user = user;
        this.isWhite = isWhite;

        if (this.isWhite){
            isTurn = true;
        } else {
            isTurn = false;
        }
    }


    // TODO --------- ZARZĄDZANIE PUNKTAMI ---------
    // na podstawie punktów decydujemy kto wygrał
    public void whoWins(Player p2){
        int pointsPlayer2 = p2.getPoints();
        if (this.points > pointsPlayer2){
            user.setWins();
        } else if (this.points < pointsPlayer2){
            user.setLost();
        } else {
            user.setDraws();
        }
    }

    // TODO --------- GETTERY I SETTERY ---------
    public void setPoints(int points){
        this.points += points;
    }
    public int getPoints(){
        return points;
    }
    public boolean getTurn(){
        return isTurn;
    }
}