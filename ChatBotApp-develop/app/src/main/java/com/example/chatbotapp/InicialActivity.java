package com.example.chatbotapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InicialActivity extends AppCompatActivity implements I_RecyclerViewInterface{
    ArrayList<String> listadetxt;
    String txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        Bundle extras = getIntent().getExtras();
        CardView cardview = findViewById(R.id.cardView);
        if(extras!=null){
           this.listadetxt= extras.getStringArrayList("listadetxt");
        }
        RecyclerView recyclerview = findViewById(R.id.aRecyclerView);
        I_RecyclerViewAdapter adaptador = new I_RecyclerViewAdapter(this,this.listadetxt,this);
        recyclerview.setAdapter(adaptador);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(InicialActivity.this,MainActivity.class);
        intent.putExtra("txtname",listadetxt.get(position));
        startActivity(intent);
    } 
}

