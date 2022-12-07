package com.example.maintask.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_member")
data class TeamMemberEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val photoPath: String,
    var workTime: Long = 0,
)