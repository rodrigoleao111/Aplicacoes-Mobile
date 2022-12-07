package com.example.docshare.formularios;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.docshare.metodos.UserInfo;
import com.example.docshare.usuario.FormLogin;
import com.example.docshare.R;
import com.example.docshare.metodos.FileGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormCadastro extends FileGenerator {

    private Spinner cargo_user, setor_user;
    private Button bt_cadastrar;
    private ImageView loadingBg;
    private TextInputEditText input_email, input_senha, input_confirmar_senha, input_nome, input_cpf, input_rg, input_telefone;
    private AutoCompleteTextView autoCompleteTxt_cargo, autoCompleteTxt_setor;
    private ProgressBar loadingPb;
    private String item_cargo, item_setor;

    private String[] array_cargos = {"Operador Técnico", "Engenheiro Elétrico", "Agente de Campo", "Supervisor de Manutenção"};
    private String[] array_setores = {"Engenharia", "Manutenção", "Qualidade"};

    ArrayAdapter<String> adapter_cargos;
    ArrayAdapter<String> adapter_setores;

    private final String[] mensagens = {"Erro: Preencha todos os campos", "Cadastro realizado", "Erro: Campos de senha diferentes"};

    FirebaseFirestore db_cadastros = FirebaseFirestore.getInstance();
    String usuarioID;
    String email, senha, confirma_senha;
    Map<String,Object> dados_usuario = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        getSupportActionBar().hide();
        IniciarComponentes();

        adapter_cargos = new ArrayAdapter<String>(this, R.layout.dropdown_item, array_cargos);
        adapter_setores = new ArrayAdapter<String>(this, R.layout.dropdown_item, array_setores);

        autoCompleteTxt_cargo.setAdapter(adapter_cargos);
        autoCompleteTxt_setor.setAdapter(adapter_setores);

        autoCompleteTxt_cargo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item_cargo = parent.getItemAtPosition(position).toString();}});

        autoCompleteTxt_setor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item_setor = parent.getItemAtPosition(position).toString();}});


        // Botão Finalizar Cadastro
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColetarInformacoes();

                if(email.isEmpty() || senha.isEmpty() || confirma_senha.isEmpty()){
                    Toast.makeText(getApplicationContext(), mensagens[0], Toast.LENGTH_LONG).show();
                } else {
                    if(senha.equals(confirma_senha)){
                        loadingBg.setVisibility(View.VISIBLE);
                        loadingPb.setVisibility(View.VISIBLE);
                        CadastrarUsuario(email, senha, dados_usuario);
                    } else {
                        Toast.makeText(getApplicationContext(), mensagens[2], Toast.LENGTH_LONG).show();
                        loadingPb.setVisibility(View.INVISIBLE);
                        loadingBg.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


    }

    private void ColetarInformacoes() {
        // Coletar informações inseridas
        email = input_email.getText().toString();
        senha = input_senha.getText().toString();
        confirma_senha = input_confirmar_senha.getText().toString();

        // Informações do documento usuário
        dados_usuario.put("nome", input_nome.getText().toString());
        dados_usuario.put("cpf", input_cpf.getText().toString());
        dados_usuario.put("rg", input_rg.getText().toString());
        dados_usuario.put("telefone", input_telefone.getText().toString());
        dados_usuario.put("cargo", item_cargo);
        dados_usuario.put("setor", item_setor);
        dados_usuario.put("profilePicUri", "void");
        dados_usuario.put("profilePicUrl", "void");

    }

    @Override
    public void onBackPressed() {
        goToFormLogin();
        super.onBackPressed();
    }


    /***
     * Realizar comunicação com Firebase para autenticar novo usuário e salvar informações no DB
     * Utiliza as ferramentas: Google Authentication
     * @param email inserido pelo usuário
     * @param senha inserido pelo usuário
     * @param dados_usuario informações a serem adicionadas ao DB
     */
    private void CadastrarUsuario(String email, String senha, Map<String, Object> dados_usuario){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    SalvarDadosCadastrais(dados_usuario);

                    Toast.makeText(getApplicationContext(), mensagens[1], Toast.LENGTH_LONG).show();
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        erro = "A senha precisa ter no mínimo 6 caracteres";
                    } catch (FirebaseAuthUserCollisionException e){
                        erro = "Essa conta já foi cadastrada";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail inválido";
                    } catch (Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }
                    Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_LONG).show();
                    loadingPb.setVisibility(View.INVISIBLE);
                    loadingBg.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    /***
     * Salvar informações cadastrais no banco de dados
     * Utiliza as ferramentas: Google Firestone
     * @param cadastro informações(K, V)
     */
    private void SalvarDadosCadastrais(Map<String, Object> cadastro){
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db_cadastros.collection("Usuarios").document(usuarioID);

        documentReference.set(cadastro).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
                InstanciarFirebase();
                goToFormLogin();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao salvar os dados: " + e.toString());
            }
        });
    }


    public void IniciarComponentes(){
        autoCompleteTxt_cargo = findViewById(R.id.autoCompleteCargo);
        autoCompleteTxt_setor = findViewById(R.id.autoCompleteSetor);

        input_nome = findViewById(R.id.input_nome);
        input_senha = findViewById(R.id.input_senha);
        input_confirmar_senha = findViewById(R.id.input_confirmar_senha);
        input_cpf = findViewById(R.id.input_cpf);
        input_rg = findViewById(R.id.input_rg);
        input_telefone = findViewById(R.id.input_telefone);
        input_email = findViewById(R.id.input_email);

        bt_cadastrar = findViewById(R.id.button_cadastrar);

        loadingBg = findViewById(R.id.loagingBg);
        loadingPb = findViewById(R.id.loadingPb);

        adapter_cargos = new ArrayAdapter<String>(this, R.layout.dropdown_item, array_cargos);
        adapter_setores = new ArrayAdapter<String>(this, R.layout.dropdown_item, array_setores);
        autoCompleteTxt_cargo.setAdapter(adapter_cargos);
        autoCompleteTxt_setor.setAdapter(adapter_setores);
    }


    private void goToFormLogin(){
        Intent intent = new Intent(getApplicationContext(), FormLogin.class);
        startActivity(intent);
        finish();
    }

    private void InstanciarFirebase() {
        FirebaseFirestore db_dados_usuario = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db_dados_usuario.collection("Usuarios").document(userID);
        UserInfo.setUserCredentials(documentReference, userID);
    }



}