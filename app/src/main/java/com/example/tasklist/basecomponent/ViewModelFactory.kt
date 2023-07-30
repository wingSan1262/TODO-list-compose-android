package com.example.tasklist.basecomponent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasklist.domain.CreateUpdateTaskUseCase
import com.example.tasklist.domain.DeleteTaskUseCase
import com.example.tasklist.domain.GetTaskUseCase
import com.example.tasklist.feature.TaskViewModel

class ViewModelFactory(
    private val getTaskUseCase: GetTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val createUpdateTaskUseCase: CreateUpdateTaskUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(getTaskUseCase, deleteTaskUseCase, createUpdateTaskUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}