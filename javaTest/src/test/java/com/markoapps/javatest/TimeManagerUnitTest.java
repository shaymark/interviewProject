package com.markoapps.javatest;

import androidx.annotation.NonNull;

import com.markoapps.javatest.timer.TimerManager;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TimeManagerUnitTest {

    @Test
    public void testTimeManager_Multiple2() throws InterruptedException {
        TimerManager timerManager= new TimerManager();

        long now = System.currentTimeMillis();

        timerManager.setTimer(() -> {
            System.out.println("100");
            System.out.println(System.currentTimeMillis() - now);
        }, 100);
        timerManager.setTimer(() -> {
            System.out.println("100");
            System.out.println(System.currentTimeMillis() - now);
        }, 100);
        timerManager.setTimer(() -> {
            System.out.println("100");
            System.out.println(System.currentTimeMillis() - now);
        }, 100);
        timerManager.setTimer(() -> {
            System.out.println("100");
            System.out.println(System.currentTimeMillis() - now);
        }, 100);
        timerManager.setTimer(() -> {
            System.out.println("100");
            System.out.println(System.currentTimeMillis() - now);
        }, 100);
        timerManager.setTimer(() -> {
            System.out.println("500");
            System.out.println(System.currentTimeMillis() - now);
        }, 500);
        timerManager.setTimer(() -> {
            System.out.println("300");
            System.out.println(System.currentTimeMillis() - now);
        }, 300);
        timerManager.setTimer(() -> {
            System.out.println("200");
            System.out.println(System.currentTimeMillis() - now);
        }, 200);
        timerManager.setTimer(() -> {
            System.out.println("50");
            System.out.println(System.currentTimeMillis() - now);
        }, 50);

        Thread.sleep(2000);

    }

    @Test
    public void testTimeManager_Multiple() throws InterruptedException {
        TimerManager timerManager= new TimerManager();

        timerManager.setTimer(() -> System.out.println("100"), 100);
        timerManager.setTimer(() -> System.out.println("100"), 100);
        timerManager.setTimer(() -> System.out.println("100"), 100);
        timerManager.setTimer(() -> System.out.println("500"), 500);
        timerManager.setTimer(() -> System.out.println("300"), 300);
        timerManager.setTimer(() -> System.out.println("200"), 200);
        timerManager.setTimer(() -> System.out.println("50"), 50);

        Thread.sleep(1000);

    }

    @Test
    public void testTimeManagerRegular() throws InterruptedException {
        TimerManager timerManager= new TimerManager();

        timerManager.setTimer(() -> System.out.println("100"), 100);
        timerManager.setTimer(() -> System.out.println("500"), 500);
        timerManager.setTimer(() -> System.out.println("300"), 300);
        timerManager.setTimer(() -> System.out.println("200"), 200);
        timerManager.setTimer(() -> System.out.println("50"), 50);

        Thread.sleep(1000);

    }

    @Test
    public void testTimeManagerDelays() throws InterruptedException {
        TimerManager timerManager= new TimerManager();
        long now = System.currentTimeMillis();

        List<TestItem> testList = Arrays.asList(
                new TestItem("100", 100),
                new TestItem("50", 280),
                new TestItem("200", 400),
                new TestItem("300", 500),
                new TestItem("500", 700)
        );

        List<TestItem> testItems = new ArrayList<>();

        timerManager.setTimer(() -> {
            String tag = "100";
            long delay = System.currentTimeMillis() - now;
            System.out.println(tag);
            System.out.println(delay);
            testItems.add(new TestItem(tag, delay));
        }, 100);
        Thread.sleep(200);
        timerManager.setTimer(() -> {
            String tag = "500";
            long delay = System.currentTimeMillis() - now;
            System.out.println(tag);
            System.out.println(delay);
            testItems.add(new TestItem(tag, delay));
        }, 500);
        timerManager.setTimer(() -> {
            String tag = "300";
            long delay = System.currentTimeMillis() - now;
            System.out.println(tag);
            System.out.println(tag);
            testItems.add(new TestItem(tag, delay));
        }, 300);
        timerManager.setTimer(() -> {
            String tag = "200";
            long delay = System.currentTimeMillis() - now;
            System.out.println(tag);
            System.out.println(delay);
            testItems.add(new TestItem(tag, delay));
        }, 200);
        timerManager.setTimer(() -> {
            String tag = "50";
            long delay = System.currentTimeMillis() - now;
            System.out.println(tag);
            System.out.println(delay);
            testItems.add(new TestItem(tag, delay));
        }, 50);

        Thread.sleep(1000);

        assertNull(checkTestEquels(testList, testItems));

    }

    String checkTestEquels(List<TestItem> srcList, List<TestItem>destList) {
        if(srcList.size() != destList.size()) {
            return "list Size dont match " + srcList.size() + ":" + destList.size();
        }

        for(int i=0; i<srcList.size(); i++){
            if(!srcList.get(i).isEqual(destList.get(i))){
                return "items not equels:" + " expected:" +  srcList.get(i) + " got:" +  destList.get(i);
            }
        }

        return null;
    }

    class TestItem{
        final int GAP = 40;

        String tag;
        long delay;

        public TestItem(String tag, long delay) {
            this.tag = tag;
            this.delay = delay;
        }

        boolean isEqual(TestItem testItem){
            return  tag.equals(testItem.tag)
                    && delay < testItem.delay + GAP
                    && delay > testItem.delay - GAP;
        }

        @NonNull
        @Override
        public String toString() {
            return "tag:" + tag + " delay:" + delay;
        }
    }
}