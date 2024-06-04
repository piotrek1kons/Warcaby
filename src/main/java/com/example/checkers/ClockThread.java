package com.example.checkers;

import java.util.concurrent.Callable;

public class ClockThread implements Callable<Boolean> {
    private int minuty = 5;
    private int sekundy = 0;

    @Override
    public Boolean call() throws Exception {

        //while (minuty != 0 && sekundy != 0){
            System.out.println((minuty < 10 ? "0" : "") + minuty + " : " + (sekundy < 10 ? "0" : "") + sekundy);

            if (sekundy == 0 && minuty != 0){
                minuty--;
                sekundy = 59;
                return true;
            } else if (sekundy != 0) {
                sekundy--;
                return true;
            }
        //}


        return false;
    }
}
