package com.goal.util;

import android.graphics.Canvas;

import com.goal.views.BackgroundView;

/**
 * Created by erz on 12/17/13.
 */
public class BackgroundLoopThread extends Thread {
    static final long FPS = 8;
    private BackgroundView background;
    private boolean running = false;

    public BackgroundLoopThread(BackgroundView background){
        this.background = background;
    }

    public void setRunning(boolean run){
        running = run;
    }

    public boolean isRunning(){
        return running;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = background.getHolder().lockCanvas();
                synchronized (background.getHolder()) {
                    background.onDraw(c);
                }
            } finally {
                if (c != null) {
                    background.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }
}
