package com.example.checkers;

public class User {
    private String username;
    private int wins;
    private int draws;
    private int lost;

    public User(String username){
        this.username = username;
        this.wins = 0;
        this.draws = 0;
        this.lost = 0;
    }

    public User(String username, int wins, int draws, int lost){
        this.username = username;
        this.wins = wins;
        this.draws = draws;
        this.lost = lost;
    }

    @Override
    public String toString(){
        return username + ";" + wins + ";" + draws + ";" + lost;
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

    public String getUsername() {
        return username;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLost() {
        return lost;
    }
}
