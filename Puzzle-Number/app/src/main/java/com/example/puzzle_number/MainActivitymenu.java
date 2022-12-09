package com.example.puzzle_number;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivitymenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    
    EditText editText;
    String dificuldade;
    String nome;
    Spinner dificuldades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitymenu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

       /* Bundle intent = getIntent().getExtras();
        if(intent==null){

        } else {
            nome= intent.getString("nome");
        }
        */

        dificuldades = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dificuldades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dificuldades.setAdapter(adapter);
        Button button = findViewById(R.id.começar);
        editText = findViewById(R.id.editTextTextPersonName);
        /*
        if(nome==null){

        } else {
            editText.setText(nome);
        }

         */
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateText(view);
                dificuldade=dificuldades.getSelectedItem().toString();
                if(nome.equals("")){
                    Toast toast =Toast.makeText(getApplicationContext(),
                            "nome invalido",Toast.LENGTH_SHORT);
                    toast.show();
                } else if(nome!=null && dificuldade==null){
                    Toast toast =Toast.makeText(getApplicationContext(),
                            nome+"dificuldade invalida",Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    irParatela(dificuldade);
                }
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

    }
    public void irParatela(String dificuldade){
        Intent intent = new Intent();

        if(dificuldade.equals("Facil")){
             intent.setClass(getApplicationContext(),MainActivityTelaFacil.class);
            intent.putExtra("nome",nome);
            startActivity(intent);
        } else if(dificuldade.equals("Normal")){
             intent.setClass(getApplicationContext(),MainActivityTelaNormal.class);
            intent.putExtra("nome",nome);
            startActivity(intent);
        } else if(dificuldade.equals("Dificil")){
             intent.setClass(getApplicationContext(),MainActivityTelaDificil.class);
            intent.putExtra("nome",nome);
            startActivity(intent);
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    public void updateText(View view){
        nome = editText.getText().toString();
    }
}