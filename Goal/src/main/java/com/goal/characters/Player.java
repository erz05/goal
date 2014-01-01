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

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void update() {

    }

    public void onDraw(Canvas canvas, Paint paint) {
        update();
        canvas.drawCircle(x, y, 50, paint);
    }

    public boolean checkCollision(Rect r){
        return false;
    }

    public Rect getRect(){
        return new Rect(x-25, y-25, x+25, y+25);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
