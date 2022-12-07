package com.example.maintask.model.repository

import androidx.annotation.WorkerThread
import com.example.maintask.model.database.dao.ActionDao
import com.example.maintask.model.database.entity.ActionEntity
import kotlinx.coroutines.flow.Flow

class ActionRepository(private val actionDao: ActionDao) {
    val actionList: Flow<List<ActionEntity>> = actionDao.getAllActions()

    @WorkerThread
    suspend fun insert(action: ActionEntity) {
        actionDao.insert(action)
    }

    @WorkerThread
    fun getActionByTaskId(taskId: Int): Flow<List<ActionEntity>> {
        return actionDao.getActionByTaskId(taskId)
    }

    @WorkerThread
    suspend fun updateElapsedTime(id: Int, elapsedTime: String) {
        actionDao.updateElapsedTime(id, elapsedTime)
    }

    @WorkerThread
    suspend fun deleteAll(){
        actionDao.deleteAll()
    }
}