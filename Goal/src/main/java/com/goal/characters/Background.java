package com.goal.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by erz on 12/17/13.
 */
public class Background {
    private static final int BMP_ROWS = 3;
    private static final int BMP_COLUMNS = 1;
    private static final int MAX_SPEED = 5;
    private int[] posX;
    private int[] greenX;
    private int posY = 0;
    private int[] speed;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int canvasW;
    private boolean[] alive;
    private Rect src;
    private Rect dst;

    private int bg1 = 0, bg2 = 1, greenBack = 2, greenback2 = 2;

    public Background(int w, int h, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.bmp = bmp;

        this.canvasW = w;
        posY = (3*h)/5;
        int x = 0;
        posX = new int[2];
        alive = new boolean[2];
        for(int i=0; i<2; i++){
            posX[i] = x;
            x += width;
            alive[i] = true;
        }

        x = 0;
        greenX = new int[2];
        for(int i=0; i<2; i++){
            greenX[i] = x;
            x += width;
        }

        src = new Rect();
        dst = new Rect();
    }

    private void update() {
        for(int i=0; i<2; i++){
            posX[i] -= 15;
            if(posX[i] < -width){
                posX[i] = width-15;
            }
        }

        for(int i=0; i<2; i++){
            greenX[i] -= 5;
            if(greenX[i] < -width){
                greenX[i] = width-5;
            }
        }

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY;

        srcY = greenBack * height;
        src.set(srcX, srcY, srcX + width, srcY + height);
        dst.set(greenX[0], posY, greenX[0] + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);

        srcY = greenback2 * height;
        src.set(srcX, srcY, srcX + width, srcY + height);
        dst.set(greenX[1], posY, greenX[1] + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);

        srcY = bg1 * height;
        src.set(srcX, srcY, srcX + width, srcY + height);
        dst.set(posX[0], posY, posX[0] + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);

        srcY = bg2 * height;
        src.set(srcX, srcY, srcX + width, srcY + height);
        dst.set(posX[1], posY, posX[1] + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }
}