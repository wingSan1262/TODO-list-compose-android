package com.example.tasklist.feature.navigator

import androidx.navigation.NavHostController
import com.example.tasklist.feature.CREATE_EDIT_SCREEN
import com.example.tasklist.feature.TASK_LIST_SCREEN

class TaskScreenNavigator(
    val nav : NavHostController
){

    fun navigateToMain (isPop : Boolean = false) {
        nav.navigate(
            route = TASK_LIST_SCREEN
        ){ if(isPop) popUpTo(TASK_LIST_SCREEN) }
    }

    fun navigateToEdit() {
        nav.navigate(
            route = CREATE_EDIT_SCREEN
        )
    }
}