package com.example.checkers;

import java.util.concurrent.Callable;

public class ClockThread implements Callable<Clock> {
    private Clock clock;
    private final Object pauseLock = new Object();
    private boolean paused = true;
    public ClockThread(Clock clock){
        this.clock = clock;
    }

    @Override
    public Clock call() throws Exception {
        clock.printClock();

        while (clock.isRunning()){
            synchronized (pauseLock) {
                while (paused) {
                    pauseLock.wait();
                }
            }
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

    public void pause() {
        synchronized (pauseLock) {
            paused = true;
        }
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
}
