package com.example.maintask.model.database.dao

import androidx.room.*
import com.example.maintask.model.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(taskEntity: TaskEntity)

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id = :taskId")
    fun getTaskById(taskId: Int): Flow<TaskEntity>

    @Update
    suspend fun update(task: TaskEntity)

    @Query("DELETE FROM task")
    suspend fun deleteAll()
}