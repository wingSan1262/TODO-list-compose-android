package com.example.tasklist.domain

import com.example.tasklist.basecomponent.BaseUseCase
import com.example.tasklist.data.local.LocalRepoApi

class GetTaskUseCase(
    val localApi : LocalRepoApi
): BaseUseCase<Any?, List<TaskModel>>() {
    override fun setup(parameter: Any?) {
        super.setup(parameter)
        execute {
            localApi.getAllTasks()
        }
    }
}