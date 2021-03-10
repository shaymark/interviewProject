package com.markoapps.javatest.timer;

interface CustomTimer {
    void setTimer(Callback callback, long delay);
}



public class TimerManager implements CustomTimer {

    OriginTimer originTimer = new OriginTimer();
    TimerList timerList = new TimerListImpl();

    @Override
    public void setTimer(Callback callback, long delay) {
        addTimer(callback, delay);
    }

    void addTimer(Callback callback, long delay){
        TimerItem timerItem = new TimerItem(callback, System.currentTimeMillis() + delay);
        timerList.addItem(timerItem);

        scheduleFirstItem();
    }

    void scheduleFirstItem(){
        // get the next item to execute and reschedule
        TimerItem firstItem = timerList.getFirstItem();

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
        //execute first item
        TimerItem itemToExecute = timerList.getFirstItem();
        if(itemToExecute != null) {
            itemToExecute.callback.execute();
            timerList.deleteFirstItem();
        }

        scheduleFirstItem();
    }
}
