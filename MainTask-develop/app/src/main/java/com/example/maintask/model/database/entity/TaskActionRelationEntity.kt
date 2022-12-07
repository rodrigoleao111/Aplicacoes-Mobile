package com.example.maintask.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_action_relation")
data class TaskActionRelationEntity(
    @ColumnInfo(name = "task_id") val taskId: Int,
    @ColumnInfo(name = "action_id") val actionIn: Int,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
){

}