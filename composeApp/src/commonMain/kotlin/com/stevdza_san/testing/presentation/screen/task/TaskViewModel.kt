package com.stevdza_san.testing.presentation.screen.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevdza_san.testing.domain.MongoRepository
import com.stevdza_san.testing.domain.model.TaskAction
import com.stevdza_san.testing.domain.model.ToDoTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class TaskViewModel(private val mongoDB: MongoRepository): ViewModel() {
    fun setAction(action: TaskAction) {
        when (action) {
            is TaskAction.Add -> {
                addTask(action.task)
            }

            is TaskAction.Update -> {
                updateTask(action.task)
            }

            else -> {}
        }
    }

    fun getSelectedTask(taskId: String): ToDoTask? {
        return mongoDB.getSelectedTask(taskId = taskId)
    }

    private fun addTask(task: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            mongoDB.addTask(task)
        }
    }

    private fun updateTask(task: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            mongoDB.updateTask(task)
        }
    }
}