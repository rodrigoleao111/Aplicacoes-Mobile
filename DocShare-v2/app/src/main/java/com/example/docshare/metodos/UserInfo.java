package com.example.docshare.metodos;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/***
 * Classe contendo uma variável Bundle, com as informações do usuário logado em formato String, e métodos para
 * modificação de dados e criação de diretórios no dispositivo.
 *
 * Chaves {"userID", "nome", "cpf", "rg", "telefone", "cargo", "setor", "profilePicUri", "rootDir", "userDir", "imagesDir", "osDir"}
 */
public class UserInfo {

    private static Bundle userCredentials = new Bundle();



    public static Bundle getUserCredentials() {
        return userCredentials;
    }

    public static void setUserCredentials(DocumentReference documentReference, String userID) {
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    userCredentials.putString("userID", userID);
                    userCredentials.putString("nome",documentSnapshot.getString("nome"));
                    userCredentials.putString("cpf",documentSnapshot.getString("cpf"));
                    userCredentials.putString("rg",documentSnapshot.getString("rg"));
                    userCredentials.putString("telefone",documentSnapshot.getString("telefone"));
                    userCredentials.putString("cargo",documentSnapshot.getString("cargo"));
                    userCredentials.putString("setor",documentSnapshot.getString("setor"));
                    userCredentials.putString("profilePicUri",documentSnapshot.getString("profilePicUri"));

                }
            }
        });
    }

    public static void setUserPaths(String root, String user, String images, String os){
        userCredentials.putString("rootPath", root);
        userCredentials.putString("userPath", user);
        userCredentials.putString("imagesPath", images);
        userCredentials.putString("osPath", os);
    }



    public static void updateProfilePic(String pathProfilePic, DocumentReference documentReference){
        Map<String,Object> newProfilePicUri = new HashMap<>();
        newProfilePicUri.put("profilePicUri", pathProfilePic);

        documentReference.update(newProfilePicUri).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Imagem de perfil atualizada");
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if(documentSnapshot != null){
                            userCredentials.putString("profilePicUri", documentSnapshot.getString("profilePicUri"));
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao atualizar imagem de perfil: " + e);
            }
        });
    }



    public static void updateUserCredentials(Map<String, Object> updateData, DocumentReference documentReference){
        documentReference.update(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Dados atualizados");
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if(documentSnapshot != null){
                            setUserCredentials(
                                    documentReference,
                                    FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao atualizar imagem de perfil: " + e);
            }
        });
    }

    public static boolean CriarDiretoriosDoApp(boolean permission) {
        boolean exists = false;

        File rootDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "DocShare");
        if (!rootDir.exists()) {
            if (rootDir.mkdir()) {
                userCredentials.putString("rootDir", rootDir.getAbsolutePath());
                String imagesDirName = "DocShare_images", osDirName = "DocShare_os_files";
                File userFolder = new File(rootDir, userCredentials.getString("userID"));
                if (!userFolder.exists()) {
                    if(userFolder.mkdir())
                        userCredentials.putString("userDir", userFolder.getAbsolutePath());
                    File imagesDir = new File(userFolder, imagesDirName);
                    File osDir = new File(userFolder, osDirName);
                    if (!imagesDir.exists() && !osDir.exists()) {
                        if(imagesDir.mkdir() && osDir.mkdir()){
                            userCredentials.putString("imagesDir", imagesDir.getAbsolutePath());
                            userCredentials.putString("osDir", osDir.getAbsolutePath());
                            exists = true;
                        }
                    } else {
                        userCredentials.putString("imagesDir", imagesDir.getAbsolutePath());
                        userCredentials.putString("osDir", osDir.getAbsolutePath());
                        exists = true;
                    }
                } else {
                    userCredentials.putString("userDir", userFolder.getAbsolutePath());
                    exists = true;
                }
            } else
                exists = true;
        } else
            userCredentials.putString("rootDir", rootDir.getAbsolutePath());

        return exists;
    }

}
