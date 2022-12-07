package com.example.puzzle_number;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivityTelaVitoria extends AppCompatActivity  {
    AnimationDrawable animacao;
    String nome;
    TextView textview1;
    TextView textview2;
    int NDeMovimentos;
    MediaPlayer player;
    int verdade=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tela_vitoria);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Button botão = findViewById(R.id.botãovoltar);
        imageView.setBackgroundResource(R.drawable.animacaochrono);
        animacao = (AnimationDrawable) imageView.getBackground();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Bundle intent = getIntent().getExtras();
        nome = intent.getString("nome");
        NDeMovimentos = intent.getInt("Pontuação");
        String dificuldade = intent.getString("Dificuldade");
        textview1 = findViewById(R.id.textView4);
        textview2 = findViewById(R.id.textView5);
        textview1.setText("Parabéns "+nome);
        textview2.setText("Você precisou de "+NDeMovimentos+" movimentos " +
                "na dificuldade "+ dificuldade);

        play(getCurrentFocus());
        botão.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animacao.start();
    }
    public void play(View view){
        if(player==null && verdade==-1){
            int gerador = new Random().nextInt(3);
            switch(gerador){
                case 0:
                    player = MediaPlayer.create(this,R.raw.victory0);
                    break;
                case 1:
                    player = MediaPlayer.create(this,R.raw.victory1);
                    break;
                case 2:
                    player = MediaPlayer.create(this,R.raw.victory2);
                    break;
            }

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
            player.start();
            verdade=0;
        } else if(player!=null && verdade==0){
            player.pause();
            verdade=1;
        } else if(player!=null && verdade==1){
            player.start();
            verdade=0;
        }
    }
    public void stop(View view) {
        stopPlayer();
    }
    public void stopPlayer(){
        if(player!=null){
            player.release();
            player=null;
            verdade=-1;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}