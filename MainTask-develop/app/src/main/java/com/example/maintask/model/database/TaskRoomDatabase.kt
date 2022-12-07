package com.example.maintask.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.maintask.model.database.dao.*
import com.example.maintask.model.database.entity.*
import com.example.maintask.model.database.migration.ManualMigration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [TaskEntity::class, ActionEntity::class, TaskActionRelationEntity::class,
        TeamMemberEntity::class, EmployeeEntity::class],
    version = 3, exportSchema = true)
abstract class TaskRoomDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun actionDao(): ActionDao
    abstract fun taskActionRelationDao(): TaskActionRelationDao
    abstract fun teamMemberDao(): TeamMemberDao
    abstract fun employeeDao(): EmployeeDao

    companion object{
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getDatabase(context: Context): TaskRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    "task_database"
                )
                    .addMigrations(ManualMigration.MIGRATION_1_2, ManualMigration.MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}