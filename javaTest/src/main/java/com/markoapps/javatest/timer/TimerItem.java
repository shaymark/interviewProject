package com.markoapps.javatest.timer;

public class TimerItem implements Comparable{
    Callback callback;
    long timeToExecute;

    public TimerItem(Callback callback, long timeToExecute) {
        this.callback = callback;
        this.timeToExecute = timeToExecute;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof TimerItem) {
            if(timeToExecute == ((TimerItem)o).timeToExecute) return 0;
            return (timeToExecute < ((TimerItem)o).timeToExecute) ? -1 : 1;
        }
        return 0;
    }
}
