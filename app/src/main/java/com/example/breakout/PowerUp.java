package com.example.breakout;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.Random;

public class PowerUp {
    private RectF rect;
    private Rect rect1;
    private float yVelocity;
    private boolean isVisible;
    private float ballWidth = 30;
    private float ballHeight = 30;
    private int whichBrick;
    private long hitBat;
    PowerUp() {
        yVelocity = -200;
        rect = new RectF();
        rect1= new Rect();
    }

    RectF getRect() {
        return rect;
    }
    Rect getRect1() {
        return rect1;
    }
    void update(long fps) {
        rect.top = rect.top - (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
        rect1.top=(int)rect.top;
        rect1.bottom=(int)rect.bottom;
        rect1.right=(int)rect.right;
    }
    void setBounds(float left,float top){
        rect.left=left;
        rect.top=top;
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
        rect1.left=(int)rect.left;
        rect1.top=(int)rect.top;
        rect1.bottom=(int)rect.bottom;
        rect1.right=(int)rect.right;
    }
    void setVisible(boolean status) {
        isVisible = status;
    }
    boolean getVisible() {return isVisible;}
    void setType(Bat bat,Ball ball)
    {
        Random generator=new Random();
        int answer=generator.nextInt(4);
        Log.e("Error","Random generator"+answer);
        switch (answer)
        {
            case 0:bat.setRect(600);
                Log.e("Error","Lung");
                break;
            case 1:ball.setVelocity(100,-200);
                Log.e("Error","Incet");
                break;
            case 2:bat.setRect(100);
                Log.e("Error","Scurt");
                break;
            case 3:ball.setVelocity(300,-500);
                Log.e("Error","Rapid");
                break;
        }
    }
    int getBrick()
    {
        return whichBrick;
    }
    void setBrick(int brick)
    {
        whichBrick=brick;
    }
    public long getHitBat() {
        return hitBat;
    }
    public void setHitBat(long hit) {
        hitBat = hit;
    }
}
