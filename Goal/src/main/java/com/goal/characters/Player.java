package com.goal.characters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by erz on 12/13/13.
 */
public class Player {
    private int x;
    private int y;
    private int radius;

    public Player(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    private void update() {

    }

    public void onDraw(Canvas canvas, Paint paint) {
        update();
        canvas.drawCircle(x, y, radius, paint);
    }

    public Rect getRect(){
        return new Rect(x-radius, y-radius, x+radius, y+radius);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getRadius(){
        return radius;
    }
}