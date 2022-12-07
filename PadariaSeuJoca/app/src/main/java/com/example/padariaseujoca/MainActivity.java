package com.example.padariaseujoca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNome = (EditText) findViewById(R.id.editTextPersonName);
        edtAdress = (EditText) findViewById(R.id.editTextPersonAdress);

    }

    public void goToCatalog(View view) {
        String nome = edtNome.getText().toString();
        String endereco = edtAdress.getText().toString();

        if(nome.equals("") || endereco.equals("")) {
            // Se nome ou endereço estiver vazio, colocar uma mensagem na tela
            Toast.makeText(MainActivity.this, "Insira seu nome e endereço para prosseguir.", Toast.LENGTH_LONG).show();
        } else {
            // Caso tudo ok, seguir para tela catálogo
            Intent goCatalogActivity = new Intent(getApplicationContext(), Catalogo.class);
            // Enviar informações
            Bundle cadastro = new Bundle();
            cadastro.putString("kNome", nome);
            cadastro.putString("kEndereco", endereco);
            goCatalogActivity.putExtras(cadastro);
            startActivity(goCatalogActivity);
        }
    }

    public EditText getEdtNome() {
        return edtNome;
    }

    public void setEdtNome(EditText edtNome) {
        this.edtNome = edtNome;
    }

    public EditText getEdtIdade() {
        return edtAdress;
    }

    public void setEdtIdade(EditText edtAdress) {
        this.edtAdress = edtAdress;
    }
}