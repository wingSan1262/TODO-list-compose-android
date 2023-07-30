package com.example.tasklist.feature.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tasklist.basecomponent.EventEffect
import com.example.tasklist.basecomponent.ResourceEffect
import com.example.tasklist.basecomponent.showToast
import com.example.tasklist.basecomponent.showToastCompose
import com.example.tasklist.feature.CREATE_EDIT_SCREEN
import com.example.tasklist.feature.TaskViewModel
import com.example.tasklist.feature.component.TaskForm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun NavGraphBuilder.CreateEditTaskScreen(
    taskViewModel : TaskViewModel,
    onBackToMainList : ()->Unit
){
    composable(route = CREATE_EDIT_SCREEN){

        val owner = LocalContext.current as ComponentActivity

        var isSubmitting by remember { mutableStateOf(false) }
        val createUpdateState = taskViewModel.createUpdateTaskData.observeAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Task Details") },
                    navigationIcon = {
                        IconButton(onClick = { onBackToMainList() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null) }
                    } )
            },
            content = {
                TaskForm(
                    taskModel = taskViewModel.selectedData,
                    {
                        if(it.validateData{
                                owner.showToast("please check form, error : $it")
                            }) {
                            isSubmitting = true;
                            taskViewModel.createUpdateTask(it)
                        }

                    },
                    isSubmitting
                )
            }
        )

        EventEffect(
            createUpdateState,
            onSuccess = {
                isSubmitting = false;onBackToMainList()
                taskViewModel.getTaskList()
            },
            onFail = {isSubmitting = false; owner.showToast(it.exception.message.toString())}
        )
    }
}