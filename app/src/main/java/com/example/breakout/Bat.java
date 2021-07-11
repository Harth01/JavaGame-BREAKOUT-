package com.example.breakout;

import android.graphics.Rect;
import android.graphics.RectF;

public class Bat {
    private RectF rect;
    private Rect rect1;
    private float length;
    private float x, xscreen;
    private float paddleSpeed;
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;
    private int paddleMoving = STOPPED;

    Bat(int screenX, int screenY) {
        xscreen = screenX;
        length = 200;
        float height = 150;
        x = screenX / 2;
        float y = screenY - 170;
        rect = new RectF(x, y, x + length, y + height);
        rect1 = new Rect((int) rect.left, (int) rect.top, (int) rect.right, (int) rect.bottom);
        paddleSpeed = 1200;

    }

    RectF getRect() {
        return rect;
    }
    void setRect(float length){this.length=length;}
    Rect getRect1() {
        return rect1;
    }

    void setMovementState(int state) {
        paddleMoving = state;
    }

    void update(long fps) {
        if (x >= 0)
            if (paddleMoving == LEFT) {
                x = x - paddleSpeed / fps;
            }
        if (x + length < xscreen)
            if (paddleMoving == RIGHT) {
                x = x + paddleSpeed / fps;
            }
        rect.left = x;
        rect.right = x + length;
        rect1.left = (int) x;
        rect1.right = (int) (x + length);
    }

    void reset(int screenX, int screenY) {
        x = screenX / 2;
        rect.left = screenX / 2;
        rect.top = screenY - 170;
        rect.bottom = screenY - 130;
        rect.right = screenX / 2 + 200;
        length = 200;
        rect1.left = screenX / 2;
        rect1.top = screenY - 170;
        rect1.bottom = screenY - 130;
        rect1.right = screenX / 2 + 200;
    }

    float getX() {
        return x;
    }
}


