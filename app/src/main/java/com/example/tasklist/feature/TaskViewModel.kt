package com.example.tasklist.feature

import androidx.lifecycle.ViewModel
import com.example.tasklist.domain.CreateUpdateTaskUseCase
import com.example.tasklist.domain.DeleteTaskUseCase
import com.example.tasklist.domain.GetTaskUseCase
import com.example.tasklist.domain.TaskModel

class TaskViewModel(
    val getTaskUseCase: GetTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val createUpdateTaskUseCase: CreateUpdateTaskUseCase
) : ViewModel() {

    val taskListData = getTaskUseCase.currentData
    fun getTaskList() = getTaskUseCase.setup(null)

    val deleteTaskData = deleteTaskUseCase.currentData
    fun deleteTask(task : TaskModel) = deleteTaskUseCase.setup(task)

    var selectedData = TaskModel()
    val createUpdateTaskData = createUpdateTaskUseCase.currentData
    fun createUpdateTask(task : TaskModel) = createUpdateTaskUseCase.setup(task)
}