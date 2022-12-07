package com.example.maintask.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val photoPath: String
)