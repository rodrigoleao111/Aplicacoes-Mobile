package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;


import java.io.File;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class TelaSplash extends AppCompatActivity {
    AssetManager assetManager;
    ArrayList<String> listadetxt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                TrocarTela();
            }
        }, 2000);
    }
    private void TrocarTela() {
        try {
            leitordePastatxt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, InicialActivity.class);
        intent.putStringArrayListExtra("listadetxt",listadetxt);
        startActivity(intent);
        finish();
    }

    private void leitordePastatxt() throws IOException{

        String[] serto = getAssets().list("tipo");
        String[] serto2 = getExternalFilesDir(null).list();
        ArrayList<String> listdetxt = new ArrayList<String>();

        for(int x=0; x<serto.length;x++){
            if(serto[x].contains("P2Z_")){
                listdetxt.add(serto[x].substring(4));
            }
        }
        for(int x=0;x<serto2.length;x++){
            if(serto2[x].contains("P2Z_") && !listdetxt.contains(serto2[x].substring(4))){
                listdetxt.add(serto2[x].substring(4));
            }
        }
        this.listadetxt = listdetxt;
        for(int x=0;x<serto.length;x++){  // colocando as pastas do assets para dentro do externalfilesdir
            String diretorio = "tipo/"+serto[x];
            File filefolder = new File(getApplicationContext().getExternalFilesDir(null),serto[x]);
            if(!filefolder.exists()) {
                filefolder.mkdir();
            } else {

            }
            String []listagemdapasta = getAssets().list(diretorio);
            for(int y=0;y<listagemdapasta.length;y++){
                InputStream fileentrada = getAssets().open(diretorio+"/"+listagemdapasta[y]);
                File filesaida = new File(getApplicationContext().getExternalFilesDir(null).getPath()+"/"
                +serto[x],listagemdapasta[y]);
                OutputStream saida = new FileOutputStream(filesaida);
                byte[] data = new byte [fileentrada.available()];
                fileentrada.read(data);
                saida.write(data);
                fileentrada.close();
                saida.close();

            }
        }
    }


    }

