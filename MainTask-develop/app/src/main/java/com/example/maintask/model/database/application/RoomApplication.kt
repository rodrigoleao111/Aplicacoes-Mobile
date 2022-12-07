package com.example.maintask.model.database.application

import android.app.Application
import com.example.maintask.model.database.TaskRoomDatabase
import com.example.maintask.model.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RoomApplication: Application() {
    val database by lazy { TaskRoomDatabase.getDatabase(this)}
    val taskRepository by lazy { TaskRepository(database.taskDao())}
    val actionRepository by lazy { ActionRepository(database.actionDao())}
    val taskActionRepository by lazy { TaskActionRelationRepository(database.taskActionRelationDao())}
    val teamMemberRepository by lazy { TeamMemberRepository(database.teamMemberDao())}
    val employeeRepository by lazy { EmployeeRepository(database.employeeDao()) }
}