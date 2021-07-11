package com.example.breakout;

import android.content.Context;
import android.content.pm.ModuleInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.util.Random;

import static java.lang.System.out;

public class EngineLevel4 extends SurfaceView implements Runnable {
    public MediaPlayer wallhit, brickhit, loseLife, loseGame, batHit, win, bgMusic;
    Drawable drawable = getResources().getDrawable(R.drawable.bat1);
    Drawable drawable1 = getResources().getDrawable(R.drawable.brick1);
    Drawable drawable2 = getResources().getDrawable(R.drawable.brick2);
    Drawable drawable3 = getResources().getDrawable(R.drawable.bila);
    Drawable drawable4 = getResources().getDrawable(R.drawable.powerup);
    Drawable drawable5 = getResources().getDrawable(R.drawable.life);
    Drawable drawable6 = getResources().getDrawable(R.drawable.life);
    Drawable drawable7 = getResources().getDrawable(R.drawable.life);
    Drawable drawable8 = getResources().getDrawable(R.drawable.brick21);
    Drawable drawable9 = getResources().getDrawable(R.drawable.brick33);
    Drawable drawable10 = getResources().getDrawable(R.drawable.brick32);
    Drawable drawable11 = getResources().getDrawable(R.drawable.brick31);
    Typeface textTypeFace;
    Bat bat;
    Ball ball;
    Brick[] bricks = new Brick[200];
    int numBricks = 0;
    int score = 0;
    int lives = 3;
    long powerUpHit;
    PowerUp[] powerUps = new PowerUp[2];
    private Canvas canvas;
    private Paint paint;
    private Thread gameThread = null;
    private SurfaceHolder ourHolder;
    private volatile boolean playing;
    private boolean paused = true;
    private int screenX;
    private int screenY;
    private long fps;
    private long timeThisFrame;

    //--------------------------- BreakoutEngine Constructor ---------------------------//
    public EngineLevel4(Context context, int x, int y) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();
        screenX = x;
        screenY = y;
        bat = new Bat(screenX, screenY);
        ball = new Ball(screenX, screenY);
        loseLife = MediaPlayer.create(context, R.raw.loselife);
        loseGame = MediaPlayer.create(context, R.raw.gameover);
        batHit = MediaPlayer.create(context, R.raw.bathit);
        bgMusic = MediaPlayer.create(context, R.raw.musiclvl4);
        bgMusic.setVolume(0.1f, 0.1f);
        bgMusic.setLooping(true);
        textTypeFace = ResourcesCompat.getFont(context, R.font.prototype);
        restart();
    }

    //--------------------------- Pause & Resume -----------------------------------------------//
    public void pause() {
        playing = false;
        bgMusic.pause();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    public void resume() {
        playing = true;
        bgMusic.start();
        gameThread = new Thread(this);
        gameThread.start();
    }

    //--------------------------- Run ------------------------------------------------------//
    @Override
    public void run() {
        while (playing) {
            //Get the time that the action starts to calculate the fps
            long startFrameTime = System.currentTimeMillis();
            //Check if the user is playing or not
            if (!paused) {
                update();
            }
            draw();

            //Calculate how long it took to update(if it happens) and draw
            timeThisFrame = System.currentTimeMillis() - startFrameTime;

            //Calculate fps and turn from milliseconds to seconds
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    //--------------------------- Update ------------------------------------------------------//
    private void update() {
        bat.update(fps);
        ball.update(fps);
        if ((powerUpHit / 1000) + 5 == System.currentTimeMillis() / 1000) {
            bat.setRect(200);
            ball.setVelocity(200, -400);
        }
        for (int j = 0; j < 2; j++)
            if (powerUps[j].getVisible()) {
                if (powerUps[j].getRect().top < screenY)
                    powerUps[j].update(fps);
                else {
                    powerUps[j].setVisible(false);
                }
            }
        for (int j = 0; j < 2; j++) {
            if (RectF.intersects(powerUps[j].getRect(), bat.getRect())) {
                powerUps[j].setType(bat, ball);
                powerUps[j].setVisible(false);
                powerUpHit = System.currentTimeMillis();
                powerUps[j].setBounds(screenX, screenY);
            }
        }
        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    bricks[i].setLives(bricks[i].getLives() - 1);
                    if (bricks[i].getLives() == 0) {
                        bricks[i].setInvisible();
                        if (i == 10) {
                            powerUps[0].setVisible(true);
                            powerUps[0].setBounds(bricks[11].getX() + 100, bricks[11].getY() + 100);
                        }
                        if (i == 13) {
                            powerUps[1].setVisible(true);
                            powerUps[1].setBounds(bricks[14].getX() + 100, bricks[14].getY() + 100);
                        }
                        score = score + 10;
                    }
                    ball.reverseYVelocity();
                    createSoundBricks(getContext().getApplicationContext());
                }
            }
        }

        if (RectF.intersects(bat.getRect(), ball.getRect())) {
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(bat.getRect().top - 2);
            batHit.start();
        }
        if (ball.getRect().bottom > screenY) {
            ball.reverseYVelocity();
            ball.clearObstacleY(screenY - 2);
            loseLife.start();
            lives--;
            paused = true;
            ball.reset(screenX, screenY);
            bat.reset(screenX, screenY);
            if (lives == 0) {
                loseGame.start();
                for (int j = 0; j < 2; j++) {
                    if (powerUps[j].getVisible()) {
                        powerUps[j].setVisible(false);
                        powerUps[j].setBounds(screenX, screenY);
                    }

                }
                bgMusic.pause();
                paused = true;
                score = 0;
                lives = 3;
                restart();
            }
        }
        if (ball.getRect().top < 0) {
            ball.reverseYVelocity();
            ball.clearObstacleY(12);
            createSoundWalls(getContext().getApplicationContext());

        }
        if (ball.getRect().left < 0) {
            ball.reverseXVelocity();
            ball.clearObstacleX(2);
            createSoundWalls(getContext().getApplicationContext());

        }
        if (ball.getRect().right > screenX - 10) {
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 22);
            createSoundWalls(getContext().getApplicationContext());
        }
        if (score == numBricks * 10) {
            paused = true;
            createSoundWin(getContext().getApplicationContext());
            bgMusic.pause();
            restart();
        }

    }

    //--------------------------- Restart ------------------------------------------------------//
    void restart() {
        if (!bgMusic.isPlaying()) {
            bgMusic.start();
        }
        score = 0;
        lives = 3;
        ball.reset(screenX, screenY);
        bat.reset(screenX, screenY);
        powerUps[0] = new PowerUp();
        powerUps[1] = new PowerUp();
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;
        numBricks = 0;
        int ok;
        for (int column = 0; column < 8; column++)
            for (int row = 0; row < 3; row++) {
                if (numBricks == 10 || numBricks == 13)
                    ok = 1;
                else
                    ok = 0;
                if (ok == 0)
                {
                    bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                    bricks[numBricks].setLives(2);
                    bricks[numBricks].setBrickKind(1);
                }
                else
                    {
                    bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                    bricks[numBricks].setLives(3);
                    bricks[numBricks].setBrickKind(2);
                    }
                numBricks++;
            }
    }

    //--------------------------- Draw ------------------------------------------------------//
    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            //Lock canvas to draw on
            canvas = ourHolder.lockCanvas();

            //Set background color, set color to paint with, draw rectangle and paint
            canvas.drawColor(Color.argb(255, 31, 19, 20));
            if (lives <= 3 && lives >= 1) {
                drawable5.setBounds(10, screenY - 100, 130, screenY);
                drawable5.draw(canvas);
            }
            if (lives <= 3 && lives >= 2) {
                drawable6.setBounds(130, screenY - 100, 250, screenY);
                drawable6.draw(canvas);
            }
            //Log.i("Error","screenX "+screenX);
            if (lives == 3) {
                drawable7.setBounds(250, screenY - 100, 370, screenY);
                drawable7.draw(canvas);
            }
            paint.setColor(Color.argb(0, 0, 0, 0));
            canvas.drawRect(bat.getRect(), paint);

            //Set bounds on texture, draw it on the canvas
            drawable.setBounds(bat.getRect1());
            drawable.draw(canvas);

            //Set color to paint with, draw the ball and paint it
            paint.setColor(Color.argb(0, 0, 0, 0));
            //Set bounds on texture, draw it on the canvas
            drawable3.setBounds(ball.getRect1().left, ball.getRect1().bottom, ball.getRect1().right, ball.getRect1().top);
            drawable3.draw(canvas);

            //Set the color to paint with, draw bricks as rectangles, set bounds
            //and draw texture over bricks
            paint.setColor(Color.argb(255, 249, 129, 0));

            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paint);
                    if (bricks[i].getBrickKind() == 1) {
                        if (bricks[i].getLives() == 2) {
                            drawable2.setBounds(bricks[i].getRect1());
                            drawable2.draw(canvas);
                        } else {
                            drawable8.setBounds(bricks[i].getRect1());
                            drawable8.draw(canvas);
                        }
                    } else {
                        if (bricks[i].getLives() == 3) {
                            drawable9.setBounds(bricks[i].getRect1());
                            drawable9.draw(canvas);
                        } else if (bricks[i].getLives() == 2) {
                            drawable11.setBounds(bricks[i].getRect1());
                            drawable11.draw(canvas);
                        } else {
                            drawable10.setBounds(bricks[i].getRect1());
                            drawable10.draw(canvas);
                        }
                    }
                }

            }
            for (int j = 0; j < 2; j++)
                if (powerUps[j].getVisible()) {
                    paint.setColor(Color.argb(0, 255, 255, 255));
                    canvas.drawRect(powerUps[j].getRect(), paint);
                    drawable4.setBounds(powerUps[j].getRect1().left, powerUps[j].getRect1().bottom, powerUps[j].getRect1().right, powerUps[j].getRect1().top);
                    drawable4.draw(canvas);
                }
            //Set color to paint with, set text size, draw score and lives on canvas
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTypeface(textTypeFace);
            paint.setTextSize(70);
            canvas.drawText("Score: " + score, screenX - 400, screenY - 10, paint);

            //Unlock canvas and post everything that has been drawn
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    //--------------------------- Touch ------------------------------------------------------//
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                paused = false;
                if (motionEvent.getX() > bat.getX() + 130) {
                    bat.setMovementState(bat.RIGHT);
                } else {
                    bat.setMovementState(bat.LEFT);
                }
                break;
            case MotionEvent.ACTION_UP:
                bat.setMovementState(bat.STOPPED);
                break;
        }
        return true;
    }

    public void createSoundWalls(Context context) {
        wallhit = MediaPlayer.create(context, R.raw.wallhit);
        wallhit.start();
        if (wallhit.isPlaying() == false) {
            wallhit.stop();
            wallhit.release();
            if (wallhit != null) {
                wallhit = null;
            }
            try {
                brickhit.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void createSoundBricks(Context context) {

        brickhit = MediaPlayer.create(context, R.raw.brickhitonelife);
        brickhit.start();
        if (brickhit.isPlaying() == false) {
            brickhit.stop();
            brickhit.release();
            if (brickhit != null) {
                brickhit = null;
            }

            try {
                brickhit.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void createSoundWin(Context context) {
        win = MediaPlayer.create(context, R.raw.win);
        win.start();
        if (win.isPlaying() == false) {
            win.stop();
            win.release();
            if (win != null) {
                win = null;
            }
            try {
                win.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}