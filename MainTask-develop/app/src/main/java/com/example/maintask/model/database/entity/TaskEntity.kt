package com.example.maintask.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val date: String,
    var status: Int,
    @ColumnInfo(name = "is_emergency") val isEmergency: Boolean,
    val author: String,
    val description: String,
    val tools: String
)