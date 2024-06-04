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

    public User(String nick, int wins, int draws, int lost){
        this.nick = nick;
        this.wins = wins;
        this.draws = draws;
        this.lost = lost;
    }

    @Override
    public String toString(){
        return nick + ";" + wins + ";" + draws + ";" + lost;
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
