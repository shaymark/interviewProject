package com.markoapps.javatest.timer;

import java.util.Timer;
import java.util.TimerTask;

public class OriginTimer {
    Timer timer;

    void setTimer(Callback callback, long delay) {

        System.out.println("setTimer:" + delay);

        if(timer != null) {
            timer.cancel();
        }
        timer = new Timer("Timer");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("executing");
                callback.execute();
            }
        };

        timer.schedule(task, delay);
    }
}
