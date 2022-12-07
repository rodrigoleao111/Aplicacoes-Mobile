package com.example.maintask.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.maintask.model.database.entity.TaskEntity
import com.example.maintask.model.viewHolder.TaskViewHolder
import com.example.maintask.viewmodel.TaskViewModel

class TaskAdapter(private val taskViewModel: TaskViewModel)
    : ListAdapter<TaskEntity, TaskViewHolder>(TaskComparator()) {

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current){
            taskId ->
                taskViewModel.setTaskId(taskId)
                taskViewModel.setButtonClick(true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.create(parent)
    }

    class TaskComparator: DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem.id == newItem.id
        }

    }

}