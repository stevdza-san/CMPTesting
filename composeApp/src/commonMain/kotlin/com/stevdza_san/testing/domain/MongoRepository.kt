package com.stevdza_san.testing.domain

import com.stevdza_san.testing.domain.model.ToDoTask
import kotlinx.coroutines.flow.Flow
import com.stevdza_san.testing.util.RequestState

interface MongoRepository {
    fun configureTheRealm()
    fun getSelectedTask(taskId: String): ToDoTask?
    fun readActiveTasks(): Flow<RequestState<List<ToDoTask>>>
    fun readCompletedTasks(): Flow<RequestState<List<ToDoTask>>>
    suspend fun addTask(task: ToDoTask)
    suspend fun updateTask(task: ToDoTask)
    suspend fun setCompleted(task: ToDoTask)
    suspend fun setFavorite(task: ToDoTask)
    suspend fun deleteTask(task: ToDoTask)
}