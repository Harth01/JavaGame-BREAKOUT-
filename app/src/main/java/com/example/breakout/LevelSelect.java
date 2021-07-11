package com.example.breakout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.io.IOException;

public class LevelSelect extends AppCompatActivity {
    private ImageButton backbtn;
    private ImageButton lvl1,lvl2,lvl3,lvl4,lvl5;
    boolean buttonOn;

    //--------------------------- LevelSelect Constructor ---------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        buttonOn = intent.getBooleanExtra("buttonOn", true);
        Animation buttanim = AnimationUtils.loadAnimation(this, R.anim.button_anim);

        Display display = getWindowManager().getDefaultDisplay();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        Point size = new Point();
        display.getSize(size);

        setContentView(R.layout.activity_level_select);


        //------------------------ Button functionality ---------------------------//
        backbtn = (ImageButton) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backbtn.startAnimation(buttanim);
                goback();
            }

        });
        lvl1 = (ImageButton) findViewById(R.id.btn1);
        lvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvl1.startAnimation(buttanim);
                goLvl1();
            }
        });
        lvl2 = (ImageButton) findViewById(R.id.btn2);
        lvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvl2.startAnimation(buttanim);
                goLvl2();
            }
        });
        lvl3 = (ImageButton) findViewById(R.id.btn3);
        lvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvl3.startAnimation(buttanim);
                goLvl3();
            }
        });
        lvl4 = (ImageButton) findViewById(R.id.btn4);
        lvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvl4.startAnimation(buttanim);
                goLvl4();
            }
        });
        lvl5 = (ImageButton) findViewById(R.id.btn5);
        lvl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvl5.startAnimation(buttanim);
                goLvl5();
            }
        });
    }

    //--------------------------- LevelSelect class functions ---------------------------//
    public void goback() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("buttonOnLevelSelector", buttonOn);
        startActivity(intent);
    }

    public void goLvl1() {
        Intent intent = new Intent(this, Level1.class);
        startActivity(intent);
    }
    public void goLvl2() {
        Intent intent = new Intent(this, Level2.class);
        startActivity(intent);
    }
    public void goLvl3() {
        Intent intent = new Intent(this, Level3.class);
        startActivity(intent);
    }
    public void goLvl4() {
        Intent intent = new Intent(this, Level4.class);
        startActivity(intent);
    }public void goLvl5() {
        Intent intent = new Intent(this, Level5.class);
        startActivity(intent);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    //-------------------------------------------------------------------------------------//
}