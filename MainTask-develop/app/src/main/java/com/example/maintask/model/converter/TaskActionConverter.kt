package com.example.maintask.model.converter

import com.example.maintask.model.task.TaskActionModel

class TaskActionConverter {
    fun toActionTitleList(actionList: List<TaskActionModel>): List<String> {
        val stringList = mutableListOf<String>()
        actionList.forEach { action ->
            stringList.add(action.action)
        }
        return stringList
    }
}