package com.example.checkers;

import java.util.concurrent.Callable;

public class ClockThread implements Callable<Clock> {
    private Clock clock;
    public ClockThread(Clock clock){
        this.clock = clock;
    }

    @Override
    public Clock call() throws Exception {

        clock.printClock();

        while (clock.isRunning()){
            int sekundy = clock.getSekundy();
            int minuty = clock.getMinuty();
            clock.updateLabels(minuty, sekundy);

            if (sekundy == 0 && minuty != 0){
                clock.setSekundy(59);
                minuty--;
                clock.setMinuty(minuty);

            } else if (sekundy != 0) {
                sekundy--;
                clock.setSekundy(sekundy);
            }
            Thread.sleep(1000);
        }

        return clock;
    }
}
