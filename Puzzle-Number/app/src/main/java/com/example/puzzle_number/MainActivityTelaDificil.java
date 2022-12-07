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

public class MainActivityTelaDificil extends AppCompatActivity {
    Tabuleiro tab = new Tabuleiro(5);
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
    Button button16;
    Button button17;
    Button button18;
    Button button19;

    Button button20;
    Button button21;
    Button button22;
    Button button23;
    Button button24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tela_dificil);
        Bundle intent = getIntent().getExtras();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        nome = intent.getString("nome");
        textview1 = findViewById(R.id.textView21);
        textview2 = findViewById(R.id.textView22);
        botãoplay = findViewById(R.id.botãoplay2);
        botãostop = findViewById(R.id.botãostop2);
        textview1.setText("Olá "+nome);
        textview2.setText(Integer.toString(tab.getNdeMovimentos()));

        button0 = findViewById(R.id.button200);
        button1 = findViewById(R.id.button201);
        button2 = findViewById(R.id.button202);
        button3 = findViewById(R.id.button203);
        button4 = findViewById(R.id.button204);

        button5 = findViewById(R.id.button205);
        button6 = findViewById(R.id.button206);
        button7 = findViewById(R.id.button207);
        button8 = findViewById(R.id.button208);
        button9 = findViewById(R.id.button209);

        button10 = findViewById(R.id.button210);
        button11 = findViewById(R.id.button211);
        button12 = findViewById(R.id.button212);
        button13 = findViewById(R.id.button213);
        button14 = findViewById(R.id.button214);

        button15 = findViewById(R.id.button215);
        button16 = findViewById(R.id.button216);
        button17 = findViewById(R.id.button217);
        button18 = findViewById(R.id.button218);
        button19 = findViewById(R.id.button219);

        button20 = findViewById(R.id.button220);
        button21 = findViewById(R.id.button221);
        button22 = findViewById(R.id.button222);
        button23 = findViewById(R.id.button223);
        button24 = findViewById(R.id.button224);
        play(getCurrentFocus());
        refreshFrame();
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
                tentarmovimento(0,4);
                refreshFrame();
            }
        });


        button5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,0);
                refreshFrame();
            }
        });
        button6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,1);
                refreshFrame();
            }
        });
        button7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,2);
                refreshFrame();
            }
        });
        button8.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,3);
                refreshFrame();
            }
        });
        button9.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(1,4);
                refreshFrame();
            }
        });

        button10.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,0);
                refreshFrame();
            }
        });
        button11.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,1);
                refreshFrame();
            }
        });
        button12.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,2);
                refreshFrame();
            }
        });
        button13.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,3);
                refreshFrame();
            }
        });
        button14.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(2,4);
                refreshFrame();
            }
        });

        button15.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,0);
                refreshFrame();
            }
        });
        button16.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,1);
                refreshFrame();
            }
        });
        button17.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,2);
                refreshFrame();
            }
        });
        button18.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,3);
                refreshFrame();
            }
        });
        button19.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(3,4);
                refreshFrame();
            }
        });

        button20.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(4,0);
                refreshFrame();
            }
        });
        button21.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(4,1);
                refreshFrame();
            }
        });
        button22.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(4,2);
                refreshFrame();
            }
        });
        button23.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(4,3);
                refreshFrame();
            }
        });
        button24.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                tentarmovimento(4,4);
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
        if(tab.getGrid()[0][4].getValor()==0){
            button4.setVisibility(View.INVISIBLE);
        } else {
            button4.setVisibility(View.VISIBLE);
            button4.setText(Integer.toString(tab.getGrid()[0][4].getValor()));
        }

        if(tab.getGrid()[1][0].getValor()==0){
            button5.setVisibility(View.INVISIBLE);
        } else {
            button5.setVisibility(View.VISIBLE);
            button5.setText(Integer.toString(tab.getGrid()[1][0].getValor()));
        }
        if(tab.getGrid()[1][1].getValor()==0){
            button6.setVisibility(View.INVISIBLE);
        } else {
            button6.setVisibility(View.VISIBLE);
            button6.setText(Integer.toString(tab.getGrid()[1][1].getValor()));
        }
        if(tab.getGrid()[1][2].getValor()==0){
            button7.setVisibility(View.INVISIBLE);
        } else {
            button7.setVisibility(View.VISIBLE);
            button7.setText(Integer.toString(tab.getGrid()[1][2].getValor()));
        }

        if(tab.getGrid()[1][3].getValor()==0){
            button8.setVisibility(View.INVISIBLE);
        } else {
            button8.setVisibility(View.VISIBLE);
            button8.setText(Integer.toString(tab.getGrid()[1][3].getValor()));
        }
        if(tab.getGrid()[1][4].getValor()==0){
            button9.setVisibility(View.INVISIBLE);
        } else {
            button9.setVisibility(View.VISIBLE);
            button9.setText(Integer.toString(tab.getGrid()[1][4].getValor()));
        }

        if(tab.getGrid()[2][0].getValor()==0){
            button10.setVisibility(View.INVISIBLE);
        } else {
            button10.setVisibility(View.VISIBLE);
            button10.setText(Integer.toString(tab.getGrid()[2][0].getValor()));
        }
        if(tab.getGrid()[2][1].getValor()==0){
            button11.setVisibility(View.INVISIBLE);
        } else {
            button11.setVisibility(View.VISIBLE);
            button11.setText(Integer.toString(tab.getGrid()[2][1].getValor()));
        }
        if(tab.getGrid()[2][2].getValor()==0){
            button12.setVisibility(View.INVISIBLE);
        } else {
            button12.setVisibility(View.VISIBLE);
            button12.setText(Integer.toString(tab.getGrid()[2][2].getValor()));
        }
        if(tab.getGrid()[2][3].getValor()==0){
            button13.setVisibility(View.INVISIBLE);
        } else {
            button13.setVisibility(View.VISIBLE);
            button13.setText(Integer.toString(tab.getGrid()[2][3].getValor()));
        }
        if(tab.getGrid()[2][4].getValor()==0){
            button14.setVisibility(View.INVISIBLE);
        } else {
            button14.setVisibility(View.VISIBLE);
            button14.setText(Integer.toString(tab.getGrid()[2][4].getValor()));
        }

        if(tab.getGrid()[3][0].getValor()==0){
            button15.setVisibility(View.INVISIBLE);
        } else {
            button15.setVisibility(View.VISIBLE);
            button15.setText(Integer.toString(tab.getGrid()[3][0].getValor()));
        }
        if(tab.getGrid()[3][1].getValor()==0){
            button16.setVisibility(View.INVISIBLE);
        } else {
            button16.setVisibility(View.VISIBLE);
            button16.setText(Integer.toString(tab.getGrid()[3][1].getValor()));
        }
        if(tab.getGrid()[3][2].getValor()==0){
            button17.setVisibility(View.INVISIBLE);
        } else {
            button17.setVisibility(View.VISIBLE);
            button17.setText(Integer.toString(tab.getGrid()[3][2].getValor()));
        }

        if(tab.getGrid()[3][3].getValor()==0){
            button18.setVisibility(View.INVISIBLE);
        } else {
            button18.setVisibility(View.VISIBLE);
            button18.setText(Integer.toString(tab.getGrid()[3][3].getValor()));
        }
        if(tab.getGrid()[3][4].getValor()==0){
            button19.setVisibility(View.INVISIBLE);
        } else {
            button19.setVisibility(View.VISIBLE);
            button19.setText(Integer.toString(tab.getGrid()[3][4].getValor()));
        }


        if(tab.getGrid()[4][0].getValor()==0){
            button20.setVisibility(View.INVISIBLE);
        } else {
            button20.setVisibility(View.VISIBLE);
            button20.setText(Integer.toString(tab.getGrid()[4][0].getValor()));
        }
        if(tab.getGrid()[4][1].getValor()==0){
            button21.setVisibility(View.INVISIBLE);
        } else {
            button21.setVisibility(View.VISIBLE);
            button21.setText(Integer.toString(tab.getGrid()[4][1].getValor()));
        }
        if(tab.getGrid()[4][2].getValor()==0){
            button22.setVisibility(View.INVISIBLE);
        } else {
            button22.setVisibility(View.VISIBLE);
            button22.setText(Integer.toString(tab.getGrid()[4][2].getValor()));
        }
        if(tab.getGrid()[4][3].getValor()==0){
            button23.setVisibility(View.INVISIBLE);
        } else {
            button23.setVisibility(View.VISIBLE);
            button23.setText(Integer.toString(tab.getGrid()[4][3].getValor()));
        }
        if(tab.getGrid()[4][4].getValor()==0){
            button24.setVisibility(View.INVISIBLE);
        } else {
            button24.setVisibility(View.VISIBLE);
            button24.setText(Integer.toString(tab.getGrid()[4][4].getValor()));
        }

        if(tab.isTabuleiroOrdenado()){
           /* Toast toast =Toast.makeText(getApplicationContext(),"Você precisou de: "
                    +tab.getNdeMovimentos()+" movimentos",Toast.LENGTH_SHORT
            );
            toast.show();
            tab= new Tabuleiro(5);
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
        intent.putExtra("Dificuldade","Dificil");
        startActivity(intent);
        finish();
    }


    public void play(View view){
        if(player==null && verdade==-1){
            player = MediaPlayer.create(this,R.raw.music3);
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