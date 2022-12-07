package com.example.maintask.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.maintask.model.database.entity.TeamMemberEntity
import com.example.maintask.model.executor.Executor
import com.example.maintask.model.viewHolder.ExecutorViewHolder

class ExecutorAdapter: ListAdapter<TeamMemberEntity, ExecutorViewHolder>(ExecutorComparator()) {
    class ExecutorComparator: DiffUtil.ItemCallback<TeamMemberEntity>() {
        override fun areItemsTheSame(oldItem: TeamMemberEntity, newItem: TeamMemberEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TeamMemberEntity, newItem: TeamMemberEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExecutorViewHolder {
        return ExecutorViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExecutorViewHolder, position: Int) {
        val currentExecutor = getItem(position)
        holder.bind(currentExecutor)
    }
}