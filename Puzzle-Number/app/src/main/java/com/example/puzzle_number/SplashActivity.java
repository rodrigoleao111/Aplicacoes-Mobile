package com.example.puzzle_number;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity implements Runnable{
    private final Handler mhandler = new Handler();
    private AnimationDrawable animacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animacao);
        animacao = (AnimationDrawable) imageView.getBackground();
        mhandler.postDelayed(this,1200);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animacao.start();
    }

    @Override
    public void run() {
        Intent intent = new Intent(getApplicationContext(),MainActivitymenu.class);
        startActivity(intent);
        finish();
    }
}