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

    private boolean modifyPlayers = false;
    private boolean ballInPlay = false;

    private float width;
    private float height;

    private float half;
    private float fourth;
    private float sixth;
    private float eighth;
    private float sixteenth;

    public Game(Context context) {
        super(context);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        half = height/2;
        fourth = height/4;
        sixth = height/6;
        eighth = height/8;
        sixteenth = height/16;

        goal.set((int) (width - eighth), (int) fourth, (int) width, (int) (height - fourth));
    }

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(getWidth(), getHeight(), bmp);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(canvas != null){
            canvas.drawColor(Color.parseColor("#0E8C3A"));

            canvas.drawRect(0, 0, width, height, fieldPaint);

            canvas.drawRect(0, fourth, eighth, height-fourth, fieldPaint);
            canvas.drawRect(0, sixth, fourth, height - sixth, fieldPaint);

            canvas.drawRect(goal, fieldPaint);
            canvas.drawRect(width-fourth, sixth, width, height-sixth, fieldPaint);

            canvas.drawLine(width/2, 0, width/2, height, fieldPaint);
            canvas.drawCircle(width/2, height/2, height/8, fieldPaint);

            canvas.drawCircle(0,0, sixteenth, fieldPaint);
            canvas.drawCircle(width,0, sixteenth, fieldPaint);
            canvas.drawCircle(width,height, sixteenth, fieldPaint);
            canvas.drawCircle(0,height, sixteenth, fieldPaint);

            if(ballInPlay && ball != null){
                ball.onDraw(canvas);
                //player.onDraw(canvas);

                if(ball.getRect().intersect(goal)){
                    listener.setScore(10);
                    ball.reset();
                }
            }

            if(ball != null){
                modifyPlayers = false;

                if(players.size() > 0){
                    for(Player player: players){
                        player.onDraw(canvas, playerPaint);
                        double xDif = ball.getX() - player.getX();
                        double yDif = ball.getY() - player.getY();
                        double distanceSquared = xDif * xDif + yDif * yDif;
                        boolean collision = distanceSquared < (ball.getRadius() + player.getRadius()) * (ball.getRadius() + player.getRadius());
                        if(collision){
                            double eta = 1.0;
                            double px = player.getX() - ball.getX();
                            double py = player.getY() - ball.getY();
                            double vnorm = Math.hypot(px,py);
                            px = px/vnorm;
                            py = py/vnorm;
                            double m1 = 1;
                            double m2 = 1;
                            double mt = m1+m2;
                            double v1 = ball.getSpeedX()*px+ball.getSpeedY()*py;
                            double v2 = -ball.getSpeedX()*px+(-ball.getSpeedY())*py;
                            double qx = py;
                            double qy = -px;
                            double u1 = ball.getSpeedX()*qx+ball.getSpeedY()*qy;
                            double v1f = ((eta+1.0)*m2*v2+v1*(m1-eta*m2))/mt;
                            ball.setSpeedX((int)(v1f*px+u1*qx));
                            ball.setSpeedY((int)(v1f*py+u1*qy));
                        }


                        /*if(player.getRect().intersect(ball.getRect())){
                            ball.switchSpeed();
                        }*/
                    }
                }

                modifyPlayers = true;
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

    public void createPlayer(int x, int y){
        if(modifyPlayers){
            Player player = new Player(x, y);
            players.add(player);
        }
    }

    public void setListener(GameListener listener){
        this.listener = listener;
    }

    public void resetLevel(){
        ballInPlay = false;
        ball.reset();
        if(modifyPlayers)
            players.clear();
    }

    public void shootBall(){
        ballInPlay = true;
    }
}