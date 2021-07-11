package com.example.breakout;

import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

public class Ball {
    private RectF rect;
    private Rect rect1;
    private float xVelocity;
    private float yVelocity;
    private float ballWidth = 10;
    private float ballHeight = 10;

    Ball(int screenX,int screenY) {
        xVelocity = 200;
        yVelocity = -400;
        rect = new RectF();
        rect1=new Rect();
    }

    RectF getRect() {
        return rect;
    }
    Rect getRect1() {
        return rect1;
    }
    void update(long fps) {
        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
        rect1.left=(int)rect.left;
        rect1.top=(int)rect.top;
        rect1.bottom=(int)rect.bottom;
        rect1.right=(int)rect.right;

    }

    void reverseYVelocity() {
        yVelocity = -yVelocity;
    }

    void reverseXVelocity() {
        xVelocity = -xVelocity;
    }

    void setRandomXVelocity() {
        Random generator = new Random();
        int answer = generator.nextInt(2);
        if (answer == 0) {
            reverseXVelocity();
        }
    }

    void clearObstacleY(float y) {
        rect.bottom = y;
        rect.top = y - ballHeight;
    }


    void clearObstacleX(float x) {
        rect.left = x;
        rect.right = x + ballWidth;
    }

    void reset(int x, int y) {
        rect.left = x / 2 + 85;
        rect.top = y - 168;
        rect.right = x / 2 + 85 + ballWidth;
        rect.bottom = y - 168 - ballHeight;
        rect1.left=(int)rect.left;
        rect1.right=(int)rect.right;
        rect1.bottom=(int)rect.bottom;
        rect1.top=(int)rect.top;
        xVelocity = 200;
        yVelocity = -400;

    }
    void setVelocity(float valueX,float valueY)
    {
        if(xVelocity>0)
        xVelocity=valueX;
        else
            xVelocity=-valueX;
        if(yVelocity>0)
        yVelocity=-valueY;
        else
            yVelocity=valueY;
    }
}
