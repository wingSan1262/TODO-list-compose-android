package com.example.tasklist.data.local

import com.example.tasklist.data.local.entity.TaskEntity
import com.example.tasklist.data.local.room.TaskDao
import com.example.tasklist.domain.TaskModel

interface LocalRepoApi {
    suspend fun getAllTasks(): List<TaskModel>
    suspend fun insertTask(task: TaskModel)
    suspend fun deleteTask(task: TaskModel)
}

class LocalRepoApiIml(
    private val taskDao: TaskDao
) : LocalRepoApi {
    override suspend fun getAllTasks(): List<TaskModel> {
        return taskDao.getAllTasks().map{
            it.toTaskModel()
        }
    }

    override suspend fun insertTask(task: TaskModel) {
        taskDao.insert(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: TaskModel) {
        taskDao.delete(task.toTaskEntity())
    }

}

