package com.example.puzzle_number;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityTelaNormal extends AppCompatActivity {
    Tabuleiro tab = new Tabuleiro(4);
    String nome;
    //tabuleiro java

    Button botãoplay;
    Button botãostop;
    TextView textview1;
    TextView textview2;
    MediaPlayer player;
    int verdade=-1;

    //gridbotões
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    Button button4;
    Button button5;
    Button button6;
    Button button7;

    Button button8;
    Button button9;
    Button button10;
    Button button11;

    Button button12;
    Button button13;
    Button button14;
    Button button15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tela_normal);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Bundle intent = getIntent().getExtras();
         nome = intent.getString("nome");
        textview1 = findViewById(R.id.textView11);
        textview2 = findViewById(R.id.textView12);
        botãoplay = findViewById(R.id.botãoplay1);
        botãostop = findViewById(R.id.botãostop1);
        textview1.setText("Olá "+nome);
        textview2.setText(Integer.toString(tab.getNdeMovimentos()));

        button0 = findViewById(R.id.button10);
        button1 = findViewById(R.id.button11);
        button2 = findViewById(R.id.button12);
        button3 = findViewById(R.id.button13);

        button4 = findViewById(R.id.button14);
        button5 = findViewById(R.id.button15);
        button6 = findViewById(R.id.button16);
        button7 = findViewById(R.id.button17);

        button8 = findViewById(R.id.button18);
        button9 = findViewById(R.id.button19);
        button10 = findViewById(R.id.button110);
        button11 = findViewById(R.id.button111);

        button12 = findViewById(R.id.button112);
        button13 = findViewById(R.id.button113);
        button14 = findViewById(R.id.button114);
        button15 = findViewById(R.id.button115);

        refreshFrame();
        play(getCurrentFocus());
        button0.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(0,0);
                refreshFrame();
            }
        });
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(0,1);
                refreshFrame();
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(0,2);
                refreshFrame();
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(0,3);
                refreshFrame();
            }
        });


        button4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,0);
                refreshFrame();
            }
        });
        button5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,1);
                refreshFrame();
            }
        });
        button6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,2);
                refreshFrame();
            }
        });
        button7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,3);
                refreshFrame();
            }
        });


        button8.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,0);
                refreshFrame();
            }
        });
        button9.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,1);
                refreshFrame();
            }
        });
        button10.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,2);
                refreshFrame();
            }
        });
        button11.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,3);
                refreshFrame();
            }
        });


        button12.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,0);
                refreshFrame();
            }
        });
        button13.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,1);
                refreshFrame();
            }
        });
        button14.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,2);
                refreshFrame();
            }
        });
        button15.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,3);
                refreshFrame();
            }
        });

        botãoplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                play(view);
            }
        });
        botãostop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                stop(view);
            }
        });

    }

    public void tentarmovimento(int x,int y){
        if(tab.ismovimentovalido(x,y,"cima")){
            tab.movimentar(x,y,"cima");
        } else if(tab.ismovimentovalido(x,y,"baixo")){
            tab.movimentar(x,y,"baixo");
        } else if(tab.ismovimentovalido(x,y,"esquerda")){
            tab.movimentar(x,y,"esquerda");
        } else if(tab.ismovimentovalido(x,y,"direita")){
            tab.movimentar(x,y,"direita");
        }
    }


    public void refreshFrame() {
        if(tab.getGrid()[0][0].getValor()==0){
            button0.setVisibility(View.INVISIBLE);
        } else {
            button0.setVisibility(View.VISIBLE);
            button0.setText(Integer.toString(tab.getGrid()[0][0].getValor()));
        }
        if(tab.getGrid()[0][1].getValor()==0){
            button1.setVisibility(View.INVISIBLE);
        } else {
            button1.setVisibility(View.VISIBLE);
            button1.setText(Integer.toString(tab.getGrid()[0][1].getValor()));
        }
        if(tab.getGrid()[0][2].getValor()==0){
            button2.setVisibility(View.INVISIBLE);
        } else {
            button2.setVisibility(View.VISIBLE);
            button2.setText(Integer.toString(tab.getGrid()[0][2].getValor()));
        }
        if(tab.getGrid()[0][3].getValor()==0){
            button3.setVisibility(View.INVISIBLE);
        } else {
            button3.setVisibility(View.VISIBLE);
            button3.setText(Integer.toString(tab.getGrid()[0][3].getValor()));
        }

        if(tab.getGrid()[1][0].getValor()==0){
            button4.setVisibility(View.INVISIBLE);
        } else {
            button4.setVisibility(View.VISIBLE);
            button4.setText(Integer.toString(tab.getGrid()[1][0].getValor()));
        }
        if(tab.getGrid()[1][1].getValor()==0){
            button5.setVisibility(View.INVISIBLE);
        } else {
            button5.setVisibility(View.VISIBLE);
            button5.setText(Integer.toString(tab.getGrid()[1][1].getValor()));
        }
        if(tab.getGrid()[1][2].getValor()==0){
            button6.setVisibility(View.INVISIBLE);
        } else {
            button6.setVisibility(View.VISIBLE);
            button6.setText(Integer.toString(tab.getGrid()[1][2].getValor()));
        }

        if(tab.getGrid()[1][3].getValor()==0){
            button7.setVisibility(View.INVISIBLE);
        } else {
            button7.setVisibility(View.VISIBLE);
            button7.setText(Integer.toString(tab.getGrid()[1][3].getValor()));
        }

        if(tab.getGrid()[2][0].getValor()==0){
            button8.setVisibility(View.INVISIBLE);
        } else {
            button8.setVisibility(View.VISIBLE);
            button8.setText(Integer.toString(tab.getGrid()[2][0].getValor()));
        }
        if(tab.getGrid()[2][1].getValor()==0){
            button9.setVisibility(View.INVISIBLE);
        } else {
            button9.setVisibility(View.VISIBLE);
            button9.setText(Integer.toString(tab.getGrid()[2][1].getValor()));
        }
        if(tab.getGrid()[2][2].getValor()==0){
            button10.setVisibility(View.INVISIBLE);
        } else {
            button10.setVisibility(View.VISIBLE);
            button10.setText(Integer.toString(tab.getGrid()[2][2].getValor()));
        }
        if(tab.getGrid()[2][3].getValor()==0){
            button11.setVisibility(View.INVISIBLE);
        } else {
            button11.setVisibility(View.VISIBLE);
            button11.setText(Integer.toString(tab.getGrid()[2][3].getValor()));
        }

        if(tab.getGrid()[3][0].getValor()==0){
            button12.setVisibility(View.INVISIBLE);
        } else {
            button12.setVisibility(View.VISIBLE);
            button12.setText(Integer.toString(tab.getGrid()[3][0].getValor()));
        }
        if(tab.getGrid()[3][1].getValor()==0){
            button13.setVisibility(View.INVISIBLE);
        } else {
            button13.setVisibility(View.VISIBLE);
            button13.setText(Integer.toString(tab.getGrid()[3][1].getValor()));
        }
        if(tab.getGrid()[3][2].getValor()==0){
            button14.setVisibility(View.INVISIBLE);
        } else {
            button14.setVisibility(View.VISIBLE);
            button14.setText(Integer.toString(tab.getGrid()[3][2].getValor()));
        }

        if(tab.getGrid()[3][3].getValor()==0){
            button15.setVisibility(View.INVISIBLE);
        } else {
            button15.setVisibility(View.VISIBLE);
            button15.setText(Integer.toString(tab.getGrid()[3][3].getValor()));
        }

        if(tab.isTabuleiroOrdenado()){
         /*   Toast toast =Toast.makeText(getApplicationContext(),"Você precisou de: "
                    +tab.getNdeMovimentos()+" movimentos",Toast.LENGTH_SHORT
            );
            toast.show();
            tab= new Tabuleiro(4);
            refreshFrame();

          */
            paraTelaRanking(getCurrentFocus());
        }
        textview2.setText(Integer.toString(tab.getNdeMovimentos()));
    }
    public void paraTelaRanking(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivityTelaVitoria.class);
        intent.putExtra("nome",nome);
        intent.putExtra("Pontuação",tab.getNdeMovimentos());
        intent.putExtra("Dificuldade","Normal");
        startActivity(intent);
        finish();
    }

    public void play(View view){
        if(player==null && verdade==-1){
            player = MediaPlayer.create(this,R.raw.music2);
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
    } public void stopPlayer(){
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