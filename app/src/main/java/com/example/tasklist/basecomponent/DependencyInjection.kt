package com.example.tasklist.basecomponent

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModelProvider
import com.example.tasklist.MyApp
import com.example.tasklist.data.local.LocalRepoApiIml
import com.example.tasklist.data.local.room.RoomDataBase
import com.example.tasklist.domain.CreateUpdateTaskUseCase
import com.example.tasklist.domain.DeleteTaskUseCase
import com.example.tasklist.domain.GetTaskUseCase
import com.example.tasklist.feature.MainActivity
import com.example.tasklist.feature.TaskViewModel

class AppModule(val owner : Application){

    val provideRoomDb by lazy {
        RoomDataBase.getDatabase(owner)
    }

    val provideTaskDao by lazy {
        provideRoomDb.taskDao()
    }

    val localRepoApi by lazy {
        LocalRepoApiIml(provideTaskDao)
    }
}

class ActivityModule(
    val owner : ComponentActivity,
    val appModule : AppModule
){

    val viewModelFactory by lazy {
        ViewModelFactory(
            provideGetTaskUseCase, provideDeleteTaskUseCase, provideCreateUpdateTaskUseCase
        )
    }

    val taskViewModel : TaskViewModel by lazy {
        ViewModelProvider(owner, viewModelFactory).get(TaskViewModel::class.java)
    }

    val provideCreateUpdateTaskUseCase : CreateUpdateTaskUseCase by lazy {
        CreateUpdateTaskUseCase(appModule.localRepoApi) }

    val provideDeleteTaskUseCase by lazy {
        DeleteTaskUseCase(appModule.localRepoApi) }

    val provideGetTaskUseCase by lazy {
        GetTaskUseCase(appModule.localRepoApi)
    }

}

class ActivityInjector (
    val owner : ComponentActivity,
    val activityModule: ActivityModule = ActivityModule(
        owner, (owner.application as MyApp).appModule)
) {
    @OptIn(ExperimentalMaterialApi::class)
    fun inject(activity: MainActivity){
        activity.taskViewModel = activityModule.taskViewModel
    }
}