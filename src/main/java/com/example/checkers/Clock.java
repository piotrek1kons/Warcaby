package com.example.checkers;

public class Clock {

    private int minuty = 5;
    private int sekundy = 0;

    public void printClock(){
        System.out.println((minuty < 10 ? "0" : "") + minuty + " : " + (sekundy < 10 ? "0" : "") + sekundy);
    }

    public boolean isRunning(){
        if (minuty == 0 && sekundy == 0){
            return false;
        }

        return true;
    }

    public int getMinuty() {
        return minuty;
    }
    public int getSekundy(){
        return sekundy;
    }
    public void setMinuty(int minuty){
        this.minuty = minuty;
    }
    public void setSekundy(int sekundy){
        this.sekundy = sekundy;
    }
}
