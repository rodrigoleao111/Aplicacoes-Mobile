package com.example.docshare.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docshare.R;
import com.example.docshare.metodos.ImageHelper;
import com.example.docshare.usuario.ConfiguracoesDeUsuario;
import com.example.docshare.usuario.FormLogin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class ConfiguracoesFragment extends Fragment {

    private Button buttonSair;
    private ImageButton button_close;
    private TextView mudarSenha, editarPerfil, sobre;   // Botões
    private TextView nome, cargo, email, telefone, gitDayvid, gitRodrigo, siteInforma;
    private ImageView profilePic;
    private TextInputEditText input_senhaAtual, input_novaSenha, input_confirmarSenha;
    Bundle paths = new Bundle();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db_dados_usuario = FirebaseFirestore.getInstance();
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid(), senhaAtual, novaSenha, confirmarSenha;
    String emailUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracoes, container, false);
        IniciarComponentes(view);

        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToUserConfig = new Intent( getContext(), ConfiguracoesDeUsuario.class);
                goToUserConfig.putExtras(paths);
                startActivity(goToUserConfig);
            }
        });

        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Você será desconectado");
                dialog.setMessage("Deseja continuar?");
                dialog.setCancelable(false);

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent back_to_login = new Intent(getContext(), FormLogin.class);
                        Toast.makeText(getContext(), "Usuário deslogado", Toast.LENGTH_LONG).show();
                        startActivity(back_to_login);
                    }
                });

                dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                dialog.create();
                dialog.show();

            }
        });

        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AlertDialog.Builder sobreDialog = new AlertDialog.Builder(getContext());
            View viewSobre = inflater.inflate(R.layout.dialog_sobre, null);
            sobreDialog.setView(viewSobre);
            sobreDialog.setCancelable(false);
            AlertDialog dialog = sobreDialog.create();
            dialog.show();

            gitDayvid = viewSobre.findViewById(R.id.gitDayvid);
            gitRodrigo = viewSobre.findViewById(R.id.gitRodrigo);
            siteInforma = viewSobre.findViewById(R.id.siteInforma);
            button_close = viewSobre.findViewById(R.id.button_close);

            gitDayvid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToURL("https://github.com/Dayv1dx");}});

            gitRodrigo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToURL("https://github.com/rodrigoleao111");}});

            siteInforma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToURL("https://www.informa.com.br/");}});

            button_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {dialog.dismiss();}});
            }
        });

        mudarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogSenha = new AlertDialog.Builder(getContext());
                View viewMudarSenha = inflater.inflate(R.layout.dialog_mudar_senha, null);
                dialogSenha.setView(viewMudarSenha);
                dialogSenha.setCancelable(false);

                input_senhaAtual = viewMudarSenha.findViewById(R.id.inputSenhaAtual);
                input_novaSenha = viewMudarSenha.findViewById(R.id.inputNovaSenha);
                input_confirmarSenha = viewMudarSenha.findViewById(R.id.inputConfirmarSenha);

                dialogSenha.setPositiveButton("salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       senhaAtual = input_senhaAtual.getText().toString();
                       novaSenha = input_novaSenha.getText().toString();
                       confirmarSenha = input_confirmarSenha.getText().toString();

                       if(senhaAtual.isEmpty() || novaSenha.isEmpty() || confirmarSenha.isEmpty()){
                           Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                       } else {
                           if( novaSenha.length() < 6 || confirmarSenha.length() < 6){
                               Toast.makeText(getContext(), "A senha deve ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                           } else {
                           AuthCredential credentials = EmailAuthProvider.getCredential(emailUser, senhaAtual);
                           user.reauthenticate(credentials).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {

                                   if (novaSenha.equals(confirmarSenha)){
                                       user.updatePassword(novaSenha);
                                       Toast.makeText(getContext(), "Senha alterada com sucesso", Toast.LENGTH_SHORT).show();
                                       Log.d("sucesso", "deu certo");

                                   } else {
                                       Toast.makeText(getContext(), "Erro ao mudar senha", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getContext(), "Erro ao mudar senha", Toast.LENGTH_SHORT).show();
                                   Log.d("fracasso", "deu errado");
                               }
                           });
                       }}
                    }
                });
                dialogSenha.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                dialogSenha.create();
                dialogSenha.show();

            }


        });

        return view;
    }

    private void goToURL(String s) {
        Uri url = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, url));
    }


    private void IniciarComponentes(View view) {
        buttonSair = view.findViewById(R.id.buttonSair);
        editarPerfil = view.findViewById(R.id.EditarPerfil);
        mudarSenha = view.findViewById(R.id.MudarSenha);
        sobre = view.findViewById(R.id.Sobre);
        nome = view.findViewById(R.id.textView6);
        cargo = view.findViewById(R.id.textView7);
        email = view.findViewById(R.id.textView);
        telefone = view.findViewById(R.id.textView4);
        profilePic = view.findViewById(R.id.imageView2);

    }



    @Override
    public void onStart() {
        super.onStart();
        DocumentReference documentReference = db_dados_usuario.collection("Usuarios").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    nome.setText(documentSnapshot.getString("nome"));
                    cargo.setText(documentSnapshot.getString("cargo"));
                    telefone.setText(documentSnapshot.getString("telefone"));
                    email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    if(!Objects.equals(documentSnapshot.getString("profilePicUri"), "void")) {
                        Bitmap profilePicBitmap = BitmapFactory.decodeFile(documentSnapshot.getString("profilePicUri"));
                        if(profilePicBitmap !=null)
                            profilePic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(profilePicBitmap, profilePicBitmap.getHeight()));
                    }
                }
            }
        });

    }
}