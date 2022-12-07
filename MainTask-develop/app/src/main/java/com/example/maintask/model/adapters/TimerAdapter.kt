package com.example.maintask.model.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.maintask.model.task.TaskActionModel
import com.example.maintask.model.time.ElapsedTimeHelper
import com.example.maintask.model.viewHolder.TimerViewHolder
import com.example.maintask.viewmodel.TimerViewModel

class TimerAdapter(
    private val context: Context,
    private val taskActions: MutableList<TaskActionModel>,
    private val timerViewModel: TimerViewModel
) : RecyclerView.Adapter<TimerViewHolder>() {

    private val completedActions = Array(itemCount) { false }

    companion object {
        private const val FIRST: Int = 1
        private const val ORDERLESS: Int = 0
        private const val ACTION_IS_NOT_AVAILABLE_TO_START = "Não foi possível iniciar a Ação."

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        return TimerViewHolder.create(parent, timerViewModel)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        setText(holder, position)
        selectActionResponsible(holder, position)
        initStopwatch(holder, position)
        setActionStatus(position)
        verifyCompletedTasks()
    }

    private fun setText(holder:TimerViewHolder, position: Int) {
        val action = taskActions[position]
        holder.setText(action)
    }

    private fun selectActionResponsible(holder: TimerViewHolder, position: Int) {
        val action = taskActions[position]
        val teamList = timerViewModel.getTeamList()
        holder.selectResponsible(context, action, teamList)
    }

    private fun initStopwatch(holder: TimerViewHolder, position: Int) {
        runStopwatch(holder, position)
        resetStopwatch(holder, position)
    }

    private fun runStopwatch(holder: TimerViewHolder, position: Int){
        val currentAction = taskActions[position]
        holder.playButton.setOnClickListener {
            if (isAbleToStart(position)) {
                holder.startStop(currentAction)
                verifyCompletedTasks()
            }
            else
                Toast.makeText(
                    context,
                    ACTION_IS_NOT_AVAILABLE_TO_START,
                    Toast.LENGTH_LONG
                ).show()
        }
    }

    private fun resetStopwatch(holder: TimerViewHolder, position: Int){
        holder.resetButton.setOnClickListener {
            holder.reset(taskActions[position])
            verifyCompletedTasks()
        }
    }

    private fun verifyCompletedTasks() {
        if (completedActions.contains(false))
            timerViewModel.hasFinishedAllActions(false)
        else
            timerViewModel.hasFinishedAllActions(true)
    }

    private fun setActionStatus(position: Int) {
        val wasCompleted = wasCompleted(position)
        completedActions[position] = wasCompleted
    }

    private fun wasCompleted(position: Int): Boolean {
        val elapsedTimeHelper = ElapsedTimeHelper(taskActions[position].time)
        val seconds = elapsedTimeHelper.getSeconds()
        val minutes = elapsedTimeHelper.getMinutes()
        if ((seconds == 0) && (minutes == 0))
            return false
        return true
    }

    private fun isAbleToStart(position: Int): Boolean {
        val currentAction = taskActions[position]
        return when {
            currentAction.worker == null -> false
            currentAction.order == FIRST -> true
            currentAction.order == ORDERLESS -> true
            else -> previousActionWasCompleted(position)
        }
    }

    private fun previousActionWasCompleted(position: Int): Boolean {
        val lastPosition = position - 1
        return wasCompleted(lastPosition)
    }

    override fun getItemCount() = taskActions.size
}