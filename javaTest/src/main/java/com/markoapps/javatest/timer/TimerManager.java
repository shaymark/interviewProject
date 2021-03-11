package com.markoapps.javatest.timer;

import java.util.PriorityQueue;

interface CustomTimer {
    void setTimer(Callback callback, long delay);
}



public class TimerManager implements CustomTimer {

    OriginTimer originTimer = new OriginTimer();

    // Priority queue allow to find the minimal item
    PriorityQueue<TimerItem> timerList = new PriorityQueue<>();

    @Override
    public void setTimer(Callback callback, long delay) {
        addTimer(callback, delay);
    }

    void addTimer(Callback callback, long delay){
        TimerItem timerItem = new TimerItem(callback, System.currentTimeMillis() + delay);
        timerList.add(timerItem);

        scheduleFirstItem();
    }

    void scheduleFirstItem(){
        // get the next item to execute and reschedule
        TimerItem firstItem = timerList.peek();

        if(firstItem != null) {
            long delay = firstItem.timeToExecute - System.currentTimeMillis();
            if(delay < 0) {
                delay = 0;
            }

            originTimer.setTimer(new Callback() {
                @Override
                public void execute() {
                    executeAndRefreshTimer();
                }
            }, delay);
        }
    }

    public void executeAndRefreshTimer(){
        //get and delete first item
        TimerItem itemToExecute = timerList.poll();

        // execute the callback
        if(itemToExecute != null) {
            itemToExecute.callback.execute();
        }

        scheduleFirstItem();
    }
}
