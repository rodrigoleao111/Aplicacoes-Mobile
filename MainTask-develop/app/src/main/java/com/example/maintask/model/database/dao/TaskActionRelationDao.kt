package com.example.maintask.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maintask.model.database.entity.TaskActionRelationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskActionRelationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(taskActionRelation: TaskActionRelationEntity)

    @Query("SELECT * FROM task_action_relation")
    fun getAllRelations(): Flow<List<TaskActionRelationEntity>>

    @Query("SELECT * FROM task_action_relation WHERE task_id = :actionId")
    fun getRelationsByTaskId(actionId: Int): Flow<List<TaskActionRelationEntity>>

    @Query("DELETE FROM task_action_relation")
    suspend fun deleteAll()
}