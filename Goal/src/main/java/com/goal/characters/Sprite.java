package com.goal.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by erz on 12/10/13.
 */
public class Sprite {
    private static final int BMP_ROWS = 2;
    private static final int BMP_COLUMNS = 4;
    private int x = 0;
    private int y = 0;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int canvasW;
    private int canvasH;
    private Paint paint;
    private Rect src;
    private Rect dst;

    private Random random;

    private int speedX, speedY;

    //constant variables
    private double gravity = 9.8;

    private boolean goingup = false;
    private int groundLevel;

    private int row = 0;

    public Sprite(int w, int h, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.bmp = bmp;
        this.canvasW = w;
        this.canvasH = h;

        x = 50;
        y = 50;

        //x = w/3 - width/2;
        groundLevel = (3*h)/5;;
        Log.v("DELETE_THIS", "groundLevel " + groundLevel);
        //y = groundLevel;

        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        src = new Rect();
        dst = new Rect();

        speedX = 15;
        speedY = 15;

        random = new Random();
    }

    private void update() {

        x += speedX;
        y += speedY;

        if(x<50){
            speedX = -speedX;
            x = 50;
        }

        if(x>canvasW-50){
            speedX = -speedX;
            x = canvasW-50;
        }

        if(y<50){
            speedY = -speedY;
            y = 50;
        }

        if(y>canvasH-50){
            speedY = -speedY;
            y = canvasH-50;
        }

        /*currentFrame = ++currentFrame % BMP_COLUMNS;

        if(goingup){
            y -= 45;
            if(y<height/2){
                goingup = false;
            }
        }else {
            y += 45;
            if(y > groundLevel){
                row = 0;
                y = groundLevel;
            }
        }*/
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawCircle(x,y,50,paint);
        //int srcX = currentFrame * width;
        //int srcY = row * height;
        //src.set(srcX, srcY, srcX + width, srcY + height);
        //dst.set(x, y, x + width, y + height);
        //canvas.drawBitmap(bmp, src, dst, null);
        //canvas.drawLine(0, y, width, y, paint);
    }

    public void setRow(int i){
        row = i;
    }

    public Rect getRect(){
        return new Rect(x-25, y-25, x+25, y+25);
    }

    public void jump(){
        row = 1;
        goingup = true;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void switchSpeed(){
        int s = random.nextInt(2);
        switch (s){
            case 0:
                speedY = speedY;
                break;
            case 1:
                speedY = -speedY;
                break;
        }

        s = random.nextInt(2);
        switch (s){
            case 0:
                speedX = speedX;
                break;
            case 1:
                speedX = -speedX;
                break;
        }
    }
}
