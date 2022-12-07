package com.example.maintask.model.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginRepository(private val application: Application) {
    val userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Funcao que faz o login
    fun login(email: String, password: String, action: () -> Unit) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                userMutableLiveData.postValue(firebaseAuth.currentUser)
                action()
            }
            .addOnFailureListener { e ->
                Toast.makeText(application, "Email, ou senha, inválido", Toast.LENGTH_LONG).show()
                Log.i("teste", "${e.message}")
            }
    }

    fun keepLogin(action: () -> Unit) {
        if (firebaseAuth.currentUser != null)
            action()
    }
}

