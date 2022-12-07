package com.example.maintask.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_action")
data class ActionEntity(
    val action: String,
    val order: Int,
    @ColumnInfo(name = "elapsed_time")
    val elapsedTime: String,
    val executor: String = "",
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
)