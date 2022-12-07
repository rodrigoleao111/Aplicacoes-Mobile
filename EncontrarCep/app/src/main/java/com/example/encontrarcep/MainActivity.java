package com.example.encontrarcep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BOTÃO PARA BUSCAR CEP
        Button btnBuscarCep = findViewById(R.id.btnMain_buscarCep);     // associação à variável
        btnBuscarCep.setOnClickListener(new View.OnClickListener() {    // método do botão
            @Override
            public void onClick(View view) {
                // buscar o CEP
            }
        });


    }
}