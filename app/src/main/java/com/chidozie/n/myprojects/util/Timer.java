package com.chidozie.n.myprojects.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chidozie on 10/10/2018.
 */

public class Timer implements Runnable {
    private static Timer ourInstance;
    private static final long ALARM_TIME = TimeUnit.SECONDS.toNanos(15);
    private boolean running;
    private List<TimeListener> listeners = new ArrayList<>();

    public static Timer getInstance() {
        if (ourInstance == null) {
            ourInstance = new Timer();
        }
        return ourInstance;
    }

    public interface TimeListener {
        void alarm();
    }

    private Timer() {
    }

    public void addListener(TimeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TimeListener listener) {
        listeners.remove(listener);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        while (running) {
            long deltaTime = System.nanoTime() - startTime;
            if (deltaTime >= ALARM_TIME) {
                startTime = System.nanoTime();
                for (TimeListener l : listeners) {
                    l.alarm();
                }
            }
        }
    }
}
