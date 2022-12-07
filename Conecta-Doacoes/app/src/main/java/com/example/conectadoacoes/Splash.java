package com.example.conectadoacoes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    //Criando um método para passar para a próxima tela
    private void PassarActivity(){

        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        //Fazendo com que a primeira tela passe para a tela de login
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PassarActivity();

            }
        },2000);

    }
}