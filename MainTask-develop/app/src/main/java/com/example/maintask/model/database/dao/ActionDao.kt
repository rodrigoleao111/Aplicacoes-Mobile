package com.example.maintask.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maintask.model.database.entity.ActionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(actionEntity: ActionEntity)

    @Query("SELECT * FROM task_action")
    fun getAllActions(): Flow<List<ActionEntity>>

    @Query("SELECT * FROM task_action WHERE id = :actionId")
    fun getActionById(actionId: Int): Flow<ActionEntity>

    @Query("SELECT * FROM task_action " +
            "INNER JOIN task_action_relation ON task_action_relation.action_id = task_action.id " +
            "WHERE task_action_relation.task_id = :taskId")
    fun getActionByTaskId(taskId: Int): Flow<List<ActionEntity>>

    @Query("UPDATE task_action SET elapsed_time = :elapsedTime WHERE id = :id")
    suspend fun updateElapsedTime(id: Int, elapsedTime: String)


    @Query("DELETE FROM task_action")
    suspend fun deleteAll()
}