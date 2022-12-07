package com.example.docshare.outros;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.docshare.R;
import com.example.docshare.metodos.FileGenerator;
import com.example.docshare.metodos.ImageHelper;
import com.example.docshare.usuario.TelaUsuario;

import java.io.IOException;

public class VizualizarForm extends FileGenerator implements ImageHelper {

    private TextView nome, rg, cpf, setor, cargo, telefone, email, locacao, equipamento, modelo, equipID, diagnostico, solucao, troca, obs, descricao;
    private String[] chavesForm = {"nome", "rg", "cpf", "setor", "cargo", "telefone", "email", "locacao", "equipamento", "modelo", "equipID", "diagnostico", "solucao", "troca", "obs", "descricaoImg"};
    private TextView txtImage;
    private Button btVoltar, btCompartilhar, btHome;
    private ImageView preview;
    Bitmap bitmap = null;
    String bitmapPath = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizar_form);
        getSupportActionBar().hide();

        Intent intentReceberForm = getIntent();
        bitmapPath = intentReceberForm.getStringExtra("BitmapImage");
        if(bitmapPath != null)
            bitmap = BitmapFactory.decodeFile(bitmapPath);



        Bundle dadosOS = intentReceberForm.getExtras();

        InicializarComponentes();
        AtribuirValores(dadosOS, bitmap);

        // BOTÃO VOLTAR
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // BOTÃO COMPARTILHAR
        btCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GerarPDF(dadosOS, bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaUsuario.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /***
     * Impressão de dados na tela
     * Atribui os valores que foram inseridos pelo usuário na Activity Formulário
     * @param dadosOS - Dados de texto inseridos pelo usuário
     * @param bitmap - Imagem anexada pelo usuário
     */
    private void AtribuirValores(Bundle dadosOS, Bitmap bitmap) {
        TextView[] dadosDoForm = {nome, rg, cpf, setor, cargo, telefone, email, locacao, equipamento, modelo, equipID, diagnostico, solucao, troca, obs, descricao};
        for(int i = 0; i < dadosDoForm.length; i++){
            if (dadosOS.getString(chavesForm[i]) != null) {
                dadosDoForm[i].setText(dadosOS.getString(chavesForm[i]));
            } else {
                dadosOS.putString(chavesForm[i], "xxxxxxx");
                dadosDoForm[i].setText(dadosOS.getString(chavesForm[i]));
            }
        }

        if(bitmap != null) {
            preview.setImageBitmap(bitmap);
            preview.setVisibility(View.VISIBLE);
            descricao.setVisibility(View.VISIBLE);
            txtImage.setVisibility(View.VISIBLE);
        }
    }

    private void InicializarComponentes() {
        nome = findViewById(R.id.txtNome);
        rg = findViewById(R.id.txtRG);
        cpf = findViewById(R.id.txtCPF);
        setor = findViewById(R.id.txtSetor);
        cargo = findViewById(R.id.txtCargo);
        telefone = findViewById(R.id.txtTelefone);
        email = findViewById(R.id.txtEmail);
        locacao = findViewById(R.id.txtLocacao);
        equipamento = findViewById(R.id.txtEquipamento);
        modelo = findViewById(R.id.txtModelo);
        equipID = findViewById(R.id.txtEquipID);
        diagnostico = findViewById(R.id.txtDiag);
        solucao = findViewById(R.id.txtSolucao);
        troca = findViewById(R.id.txtTroca);
        obs = findViewById(R.id.txtOBS);
        descricao = findViewById(R.id.txtDescricao);

        preview = findViewById(R.id.imagePreview);
        txtImage = findViewById(R.id.vwInfoImagem);

        btVoltar = findViewById(R.id.btVoltar);
        btCompartilhar = findViewById(R.id.btSaveShare);
        btHome = findViewById(R.id.btHome);
    }
}