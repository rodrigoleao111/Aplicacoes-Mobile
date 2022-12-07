package com.example.maintask.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maintask.model.database.entity.EmployeeEntity
import com.example.maintask.model.database.entity.TeamMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert(entity = EmployeeEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(employee: EmployeeEntity)

    @Query("SELECT * FROM employee")
    fun getEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getEmployeeById(id: Int): Flow<EmployeeEntity>

    @Query("DELETE FROM employee WHERE id = :id ")
    suspend fun deleteEmployeeById(id: Int)

    @Query("DELETE FROM employee")
    suspend fun deleteAll()
}