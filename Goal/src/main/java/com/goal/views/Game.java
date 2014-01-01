package com.goal.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.goal.R;
import com.goal.characters.Player;
import com.goal.characters.Sprite;
import com.goal.listeners.GameListener;
import com.goal.util.GameLoopThread;

import java.util.ArrayList;

/**
 * Created by erz on 12/9/13.
 */
public class Game extends SurfaceView{

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite ball;
    private Paint fieldPaint;
    private Paint playerPaint;
    private ArrayList<Player> players;
    private Rect goal;

    private GameListener listener;

    private boolean create = false;

    public Game(Context context) {
        super(context);
        //setBackgroundColor(Color.TRANSPARENT);
        //setZOrderOnTop(true);
        //getHolder().setFormat(PixelFormat.TRANSPARENT);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(gameLoopThread != null){
                    boolean retry = true;
                    gameLoopThread.setRunning(false);
                    while (retry) {
                        try {
                            gameLoopThread.join();
                            retry = false;
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                ball = createSprite(R.drawable.running);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.monstars);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });

        fieldPaint = new Paint();
        fieldPaint.setColor(Color.WHITE);
        fieldPaint.setStyle(Paint.Style.STROKE);
        fieldPaint.setStrokeWidth(12);

        playerPaint = new Paint();
        playerPaint.setColor(Color.RED);
        playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        playerPaint.setStrokeWidth(1);

        players = new ArrayList<Player>();

        goal = new Rect();
    }

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(getWidth(), getHeight(), bmp);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(canvas != null){
            canvas.drawColor(Color.parseColor("#0E8C3A"));

            float width = canvas.getWidth();
            float height = canvas.getHeight();

            float half = height/2;
            float fourth = height/4;
            float eighth = height/8;
            float sixteenth = height/16;

            goal = new Rect((int)(width-eighth), (int)fourth, (int)width, (int)(height-fourth));

            canvas.drawRect(0, 0, width, height, fieldPaint);

            canvas.drawRect(0, fourth, eighth, height-fourth, fieldPaint);
            canvas.drawRect(0, eighth, fourth, height-eighth, fieldPaint);

            canvas.drawRect(goal, fieldPaint);
            canvas.drawRect(width-fourth, eighth, width, height-eighth, fieldPaint);

            canvas.drawLine(width/2, 0, width/2, height, fieldPaint);
            canvas.drawCircle(width/2, height/2, height/8, fieldPaint);

            canvas.drawCircle(0,0, sixteenth, fieldPaint);
            canvas.drawCircle(width,0, sixteenth, fieldPaint);
            canvas.drawCircle(width,height, sixteenth, fieldPaint);
            canvas.drawCircle(0,height, sixteenth, fieldPaint);

            if(ball != null){
                ball.onDraw(canvas);
                //player.onDraw(canvas);

                create = false;

                if(players.size() > 0){
                    for(Player player: players){
                        player.onDraw(canvas, playerPaint);
                        if(player.getRect().intersect(ball.getRect())){
                            ball.switchSpeed();
                        }
                    }
                }

                create = true;

                if(ball.getRect().intersect(goal)){
                    listener.setScore(10);
                }
            }

            /*if(player.checkCollision(ball.getRect())){
                //Log.v("DELETE_THIS", "Collision");
            }*/
        }
    }

    public void changeRobot(int i){
        ball.setRow(i);
    }

    public void start(){
        if(gameLoopThread == null){
            gameLoopThread = new GameLoopThread(this);
        }
        if(gameLoopThread != null && !gameLoopThread.isRunning()){
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }


    public void resume(){
        if(gameLoopThread == null){
            gameLoopThread = new GameLoopThread(this);
        }
        if(gameLoopThread != null && !gameLoopThread.isRunning()){
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }

    public void pause(){
        if(gameLoopThread != null && gameLoopThread.isRunning()){
            gameLoopThread.setRunning(false);
            boolean retry = true;
            while (retry) {
                try {
                    gameLoopThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
            gameLoopThread = null;
        }
    }

    public void robotJump(){
        ball.jump();
    }

    public void createPlayer(int x, int y){
        if(create){
            Player player = new Player(x, y);
            players.add(player);
        }
    }

    public void setListener(GameListener listener){
        this.listener = listener;
    }
}