package com.example.breakout;

import android.graphics.Rect;
import android.graphics.RectF;

public class Brick {
    private RectF rect;
    private Rect rect1;
    private boolean isVisible;
    private int lives;
    private boolean hasPower;


    private int brickKind;

    Brick(int row, int column, int width, int height) {
        isVisible = true;
        int padding = 1;
        rect = new RectF(column * width + padding, row * height + padding, column * width + width - padding, row * height + height - padding);
        rect1 = new Rect((int) rect.left, (int) rect.top, (int) rect.right, (int) rect.bottom);

    }

    RectF getRect() {
        return this.rect;
    }

    Rect getRect1() {
        return this.rect1;
    }

    float getX() {
        return rect.left;
    }
    float getY() {
        return rect.top;
    }

    void setInvisible() {
        isVisible = false;
    }

    boolean getVisibility() {
        return isVisible;
    }

    int getLives(){return lives;}

    void setLives(int value){lives=value;}
    void setPower(boolean power){
        hasPower=power;
    }
    boolean getPower()
    {
        return hasPower;
    }
    public int getBrickKind() {
        return brickKind;
    }
    void setBrickKind(int kind)
    {
        brickKind=kind;
    }
}
