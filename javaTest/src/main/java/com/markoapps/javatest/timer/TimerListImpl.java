package com.markoapps.javatest.timer;

import java.util.ArrayList;
import java.util.List;

interface TimerList{
    void addItem(TimerItem item);
    TimerItem getFirstItem();
    void deleteFirstItem();
}

public class TimerListImpl implements TimerList {

    List<TimerItem> timerList = new ArrayList<>();

    @Override
    public void addItem(TimerItem item) {
        for(int i=0; i<timerList.size(); i++){
            TimerItem listItem = timerList.get(i);
            if(listItem.timeToExecute > item.timeToExecute) {
                timerList.add(i, item);
                return;
            }
        }
        timerList.add(item);
    }

    @Override
    public TimerItem getFirstItem() {
        if(!timerList.isEmpty()) {
            return timerList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void deleteFirstItem() {
        timerList.remove(0);
    }

}
