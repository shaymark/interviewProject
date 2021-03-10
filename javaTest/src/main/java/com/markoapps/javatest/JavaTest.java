package com.markoapps.javatest;

import com.markoapps.javatest.timer.Callback;
import com.markoapps.javatest.timer.TimerManager;

public class JavaTest {


    void main() {
        TimerManager timerManager= new TimerManager();

        timerManager.setTimer(() -> System.out.println("100"), 100);
        timerManager.setTimer(() -> System.out.println("500"), 500);
        timerManager.setTimer(() -> System.out.println("300"), 300);
        timerManager.setTimer(() -> System.out.println("200"), 200);
        timerManager.setTimer(() -> System.out.println("50"), 50);
    }


}
