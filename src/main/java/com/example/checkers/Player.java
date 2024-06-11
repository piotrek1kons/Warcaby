package com.example.checkers;

public class Player {
    private User user;
    private int points;
    private boolean isWhite;
    //private boolean isTurn;

    public Player(User user, boolean isWhite){
        this.user = user;
        this.isWhite = isWhite;
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
    public void addPoints(int points){
        this.points += points;
    }
    public void resetPoints() {this.points = 0;}
    public int getPoints(){
        return points;
    }
    public User getUser(){
        return user;
    }
    public String isWhite(){
        if(isWhite) return "black";
        else return "white";
    }
}