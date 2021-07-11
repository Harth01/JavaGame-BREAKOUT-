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

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    private ImageButton volume;
    boolean buttonOn;
    boolean isPlaying=true;
    public MediaPlayer muzicaMeniu;

    //--------------------------- MainActivity Constructor ---------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent a = getIntent();
        buttonOn = a.getBooleanExtra("buttonOnLevelSelector", true);
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

        Animation buttanim = AnimationUtils.loadAnimation(this, R.anim.button_anim);
        setContentView(R.layout.activity_main);


        //------------------------ Button functionality ---------------------------
        volume = (ImageButton) findViewById(R.id.VolumeMain);
        if(!buttonOn){
            volume.setBackgroundResource(R.drawable.volume_off);
        }
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume.startAnimation(buttanim);
                if (buttonOn) {
                    buttonOn = false;
                    volume.setBackgroundResource(R.drawable.volume_off);
                    muzicaMeniu.pause();
                    isPlaying=false;
                } else {
                    buttonOn = true;
                    volume.setBackgroundResource(R.drawable.volume);
                    muzicaMeniu.start();
                }
            }
        });

        button = (ImageButton) findViewById(R.id.newgame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.startAnimation(buttanim);
                openlvl();
            }
        });
    }


    //--------------------------- MainActivity class functions ---------------------------
    public void openlvl() {
        Intent intent = new Intent(this, LevelSelect.class);
        intent.putExtra("buttonOn", buttonOn);
        startActivity(intent);
    }
    public void onPause(){
        super.onPause();
        muzicaMeniu.stop();
        muzicaMeniu.release();
        isPlaying=false;
    }
    public void onResume() {
        super.onResume();
        muzicaMeniu = MediaPlayer.create(this, R.raw.muzica_meniu);
        muzicaMeniu.setLooping(true);
        if (buttonOn) {
            if (isPlaying) {
                muzicaMeniu.start();
            } else {
                try {
                    muzicaMeniu.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                muzicaMeniu.start();
            }
        }
    }
}
    //-----------------------------------------------------------------------------------------------