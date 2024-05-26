package com.example.checkers;

public class User {
    private String nick;
    private int wins;
    private int draws;
    private int lost;

    public User(String nick){
        this.nick = nick;
        this.wins = 0;
        this.draws = 0;
        this.lost = 0;
    }


    // TODO --------- GETTERY I SETTERY ---------
    public void setWins(){
        this.wins++;
    }
    public void setDraws(){
        this.draws++;
    }
    public void setLost(){
        this.lost++;
    }


}
