package com.example.maintask.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.maintask.model.database.entity.TeamMemberEntity
import com.example.maintask.model.viewHolder.TeamViewHolder
import com.example.maintask.viewmodel.TeamViewModel

class TeamAdapter(
    private val teamViewModel: TeamViewModel
    ): ListAdapter<TeamMemberEntity, TeamViewHolder>(TeamComparator()) {

    class TeamComparator: DiffUtil.ItemCallback<TeamMemberEntity>() {
        override fun areItemsTheSame(oldItem: TeamMemberEntity, newItem: TeamMemberEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TeamMemberEntity, newItem: TeamMemberEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val currentEmployee = getItem(position)
        holder.bind(currentEmployee, teamViewModel)
    }
}