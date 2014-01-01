package com.goal.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.goal.R;
import com.goal.characters.Background;
import com.goal.util.BackgroundLoopThread;

/**
 * Created by erz on 12/17/13.
 */
public class BackgroundView extends SurfaceView {

    private SurfaceHolder holder;
    private BackgroundLoopThread backgroundLoopThread;
    private Background background;

    public BackgroundView(Context context) {
        super(context);
        backgroundLoopThread = new BackgroundLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                backgroundLoopThread.setRunning(false);
                while (retry) {
                    try {
                        backgroundLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
                background = new Background(getWidth(), getHeight(), bmp);
                if(!backgroundLoopThread.isRunning()){
                    backgroundLoopThread.setRunning(true);
                    backgroundLoopThread.start();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(canvas != null){
            canvas.drawColor(Color.BLACK);
            background.onDraw(canvas);
        }
    }

    public void start(){
        if(backgroundLoopThread != null && !backgroundLoopThread.isRunning()){
            backgroundLoopThread.setRunning(true);
            backgroundLoopThread.start();
        }
    }

}
