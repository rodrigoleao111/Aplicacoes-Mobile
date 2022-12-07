package com.example.maintask.callbacks

import android.widget.TextView
import com.example.maintask.model.task.TaskModel

interface MainActivityCallbacks {
    var toolbarTitle: TextView
    var selectedTask: TaskModel?
}