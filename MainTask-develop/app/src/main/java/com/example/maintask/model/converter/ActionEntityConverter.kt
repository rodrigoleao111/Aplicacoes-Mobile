package com.example.maintask.model.converter

import com.example.maintask.model.database.entity.ActionEntity
import com.example.maintask.model.task.TaskActionModel

class ActionEntityConverter {
    fun toTaskActionModelList(
        actionEntityList: List<ActionEntity>
    ): List<TaskActionModel> {
        val taskActionModelList = mutableListOf<TaskActionModel>()
        for (actionEntity in actionEntityList)
            taskActionModelList.add(
                toTaskActionModel(actionEntity)
            )
        return taskActionModelList
    }

    private fun toTaskActionModel(actionEntity: ActionEntity): TaskActionModel {
        return TaskActionModel(
            actionEntity.id,
            actionEntity.action,
            actionEntity.order,
            time = actionEntity.elapsedTime
        )
    }
}