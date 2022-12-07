package com.example.maintask.model.repository

import android.app.Application
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.maintask.model.user.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class CreateAccountRepository(private val application: Application) {
    val userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val emptyString = ""

    // Funcao que realiza o registro no firebase
    @RequiresApi(Build.VERSION_CODES.P)
    fun register(name: String, email: String, password: String, path: Uri?) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor) { task ->
                if (task.isSuccessful) {

                    val filename = UUID.randomUUID().toString()

                    if (path != null)
                        saveImgInFireStore(name, filename, path) { uri ->
                            saveUserInFireStore(name, uri)
                        }
                    else saveUserInFireStore(name, emptyString)

                    userMutableLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Toast.makeText(
                        application,
                        "Registration Failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener() { e ->
                e.message?.let { Log.i("teste", it) }
            }
    }

    private fun saveImgInFireStore(name: String, filename: String, path: Uri, action: (uri: String) -> Unit) {
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        // Envia a imagem para o firebase
        ref.putFile(path)
            .addOnSuccessListener {
                // Faz o download da url da imagem enviada
                ref.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.i("teste", "Download da uri feito com sucesso: ${uri.toString()}")
                        action(uri.toString())
                    }
                    .addOnFailureListener() { e ->
                        Log.i("teste", "falha no download do uri: ${e.message}")
                        action(emptyString)
                    }
            }
            .addOnFailureListener() { e ->
                Log.e("teste", "falha no envio da foto: ${e.message}")
                action(emptyString)
            }
    }

    private fun saveUserInFireStore(name: String, uri: String) {
        val user = UserModel(
            firebaseAuth.uid,
            name,
            firebaseAuth.currentUser?.email,
            uri
        )

        // Usa o firestore para armazenar os dados do usuario
        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener { document ->
                Log.i("teste", "Usuario cadastrado co sucesso: ${user.toString()}")
            }
            .addOnFailureListener { e ->
                e.message?.let { Log.i("teste", "Falha ao criar o usuário no firestore: ${e.message}") }
            }

    }
}