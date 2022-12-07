package com.example.docshare.outros;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.docshare.R;
import com.example.docshare.metodos.RequestPermissions;
import com.example.docshare.metodos.UserInfo;
import com.example.docshare.usuario.FormLogin;
import com.example.docshare.usuario.TelaUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent;

                if(usuarioAtual != null) {
                    intent = new Intent(getApplicationContext(), TelaUsuario.class);
                    InstanciarFirebase();
                }
                else
                    intent = new Intent(SplashScreen.this, FormLogin.class);

                startActivity(intent);
                finish();
            }
        },1000);

    }

    private void InstanciarFirebase() {
        FirebaseFirestore db_dados_usuario = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db_dados_usuario.collection("Usuarios").document(userID);
        UserInfo.setUserCredentials(documentReference, userID);
    }

}