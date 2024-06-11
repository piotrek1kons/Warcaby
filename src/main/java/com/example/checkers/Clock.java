package com.example.checkers;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Clock {

    private int minuty = 5;
    private int sekundy = 0;

    private Label minuteLabel;
    private Label secondLabel;

    public Clock(Label minute, Label second) {
        this.minuteLabel = minute;
        this.secondLabel = second;
    }

    public void updateLabels(int minutes, int seconds) {
        Platform.runLater(() -> {
            minuteLabel.setText(String.format("%02d", minutes));
            secondLabel.setText(String.format("%02d", seconds));
        });
    }
    //clock.getMinuteLabel().setText((minuty<10?"0":"")+String.valueOf(clock.getMinuty()));
    //clock.getSecondLabel().setText((sekundy<10?"0":"")+String.valueOf(clock.getSekundy()));


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

    public Label getMinuteLabel() {
        return minuteLabel;
    }

    public void setMinuteLabel(Label minuteLabel) {
        this.minuteLabel = minuteLabel;
    }

    public Label getSecondLabel() {
        return secondLabel;
    }

    public void setSecondLabel(Label secondLabel) {
        this.secondLabel = secondLabel;
    }
}
