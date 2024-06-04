package com.example.checkers;

import java.util.concurrent.Callable;

public class ClockThread implements Callable<Clock> {

    @Override
    public Clock call() throws Exception {
        Clock clock = new Clock();

        clock.printClock();

        while (clock.isRunning()){
            int sekundy = clock.getSekundy();
            int minuty = clock.getMinuty();

            if (sekundy == 0 && minuty != 0){
                clock.setSekundy(59);
                minuty--;
                clock.setMinuty(minuty);
            } else if (sekundy != 0) {
                sekundy--;
                clock.setSekundy(sekundy);
            }
        }

        return clock;
    }
}
