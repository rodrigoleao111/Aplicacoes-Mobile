package com.example.maintask.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.maintask.model.database.entity.TeamMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamMemberDao {
    @Insert(entity = TeamMemberEntity::class ,onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(teamMember: TeamMemberEntity)

    @Query("SELECT * FROM team_member")
    fun getMembers(): Flow<List<TeamMemberEntity>>

    @Query("SELECT * FROM team_member WHERE id = :id")
    fun getMemberById(id: Int): Flow<TeamMemberEntity>

    @Query("UPDATE team_member SET workTime = :elapsedTime WHERE id = :id")
    suspend fun setWorkTime(id: Int, elapsedTime: Long)

    @Query("DELETE FROM team_member WHERE id = :id ")
    suspend fun deleteMemberById(id: Int)

    @Query("DELETE FROM team_member")
    suspend fun deleteAll()
}