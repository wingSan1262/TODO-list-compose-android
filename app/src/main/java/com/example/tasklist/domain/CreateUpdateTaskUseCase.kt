package com.example.tasklist.domain

import com.example.tasklist.basecomponent.BaseUseCase
import com.example.tasklist.data.local.LocalRepoApi

class CreateUpdateTaskUseCase(
    val localApi : LocalRepoApi
): BaseUseCase<TaskModel, Any>() {
    override fun setup(parameter: TaskModel) {
        super.setup(parameter)
        execute {
            localApi.insertTask(parameter)
        }
    }
}