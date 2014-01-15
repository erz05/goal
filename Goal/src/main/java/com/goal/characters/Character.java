package com.goal.characters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by erz on 12/9/13.
 */
public class Character {
    private int x;
    private int y;
    private boolean alive;
    private int radius;

    private Paint paint;

    public Character(int x, int y, int radius, boolean star){
        this.alive = true;
        this.x = x;
        this.y = y;
        this.radius = radius;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        if(star){
            paint.setColor(Color.YELLOW);
        }else {
            paint.setColor(Color.BLUE);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getAlive(){
        return alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    private void update() {

    }

    public void onDraw(Canvas canvas){
        if(alive){
            update();
            canvas.drawCircle(x, y, radius, paint);
        }
    }

    public Rect getRect(){
        return new Rect(x-radius, y-radius, x+radius, y+radius);
    }
}
