package com.markoapps.javatest.timer;

public class TimerItem {
    Callback callback;
    long timeToExecute;

    public TimerItem(Callback callback, long timeToExecute) {
        this.callback = callback;
        this.timeToExecute = timeToExecute;
    }
}
