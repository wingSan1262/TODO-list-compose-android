package com.example.tasklist.feature

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.example.tasklist.feature.navigator.TaskScreenNavigator
import com.example.tasklist.feature.screens.CreateEditTaskScreen
import com.example.tasklist.feature.screens.TaskListScreen

@ExperimentalMaterialApi
@Composable
fun TaskComposeNavigationHost(
    taskViewModel: TaskViewModel,
    nav: NavHostController,
){
    val navigator = remember(nav){
        TaskScreenNavigator(nav)
    }

    NavHost(nav, startDestination = TASK_LIST_SCREEN){
        TaskListScreen(taskViewModel) {
            navigator.navigateToEdit()
        }
        CreateEditTaskScreen(taskViewModel){
            navigator.navigateToMain(isPop = true)
        }
    }
}

val TASK_LIST_SCREEN by lazy { "MAIN_SCREEN" }
val CREATE_EDIT_SCREEN by lazy {"CREATE_EDIT_SCREEN"}