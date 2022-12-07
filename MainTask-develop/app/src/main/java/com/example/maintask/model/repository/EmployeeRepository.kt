package com.example.maintask.model.repository

import androidx.annotation.WorkerThread
import com.example.maintask.model.database.dao.EmployeeDao
import com.example.maintask.model.database.entity.EmployeeEntity
import com.example.maintask.model.database.entity.TeamMemberEntity
import kotlinx.coroutines.flow.Flow

class EmployeeRepository(private val employeeDao: EmployeeDao) {
    val employees: Flow<List<EmployeeEntity>> = employeeDao.getEmployees()

    @WorkerThread
    suspend fun insert(employee: EmployeeEntity) {
        employeeDao.insert(employee)
    }

    @WorkerThread
    fun getEmployeeById(id: Int): Flow<EmployeeEntity> {
        return employeeDao.getEmployeeById(id)
    }

    @WorkerThread
    suspend fun deleteEmployeeById(id: Int) {
        employeeDao.deleteEmployeeById(id)
    }

    @WorkerThread
    suspend fun deleteAll() {
        employeeDao.deleteAll()
    }
}