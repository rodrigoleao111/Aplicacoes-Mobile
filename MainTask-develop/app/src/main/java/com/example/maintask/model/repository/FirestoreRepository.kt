package com.example.maintask.model.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository(private val application: Application) {
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _task = MutableLiveData<List<DocumentSnapshot>>()
    val task: LiveData<List<DocumentSnapshot>>
        get() = _task

    private val _employees = MutableLiveData<List<DocumentSnapshot>>()
    val employees: LiveData<List<DocumentSnapshot>>
        get() = _employees

    fun getTasks() {
        fireStore.collection("task")
            .get()
            .addOnSuccessListener { result ->
                _task.value = result.documents
            }
            .addOnFailureListener { _ ->
                Toast.makeText(
                    application,
                    "Erro ao consultar banco de dados.",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    fun getEmployees() {
        fireStore.collection("employee")
            .get()
            .addOnSuccessListener { result ->
                _employees.value = result.documents
            }
            .addOnFailureListener { _ ->
                Toast.makeText(
                    application,
                    "Erro ao consultar banco de dados.",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}