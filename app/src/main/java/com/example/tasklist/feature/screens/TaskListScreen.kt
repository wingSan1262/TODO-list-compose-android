@file:OptIn(ExperimentalMaterialApi::class)

package com.example.tasklist.feature.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tasklist.basecomponent.ResourceEffect
import com.example.tasklist.basecomponent.getBareContent
import com.example.tasklist.basecomponent.showToast
import com.example.tasklist.basecomponent.ui.theme.Primary
import com.example.tasklist.basecomponent.ui.theme.PrimaryVariant
import com.example.tasklist.domain.TaskModel
import com.example.tasklist.feature.TASK_LIST_SCREEN
import com.example.tasklist.feature.TaskViewModel
import com.example.tasklist.feature.component.TaskList
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun NavGraphBuilder.TaskListScreen(
    taskViewModel : TaskViewModel,
    onEditCreateTask : ()->Unit
){
    composable(route = TASK_LIST_SCREEN){

        val context = LocalContext.current as ComponentActivity

        var isRefreshing by remember { mutableStateOf(false) }

        val taskListState = taskViewModel.taskListData.observeAsState()
        val tasksListView = remember { mutableStateListOf<TaskModel>() }

        val taskDeleteState = taskViewModel.deleteTaskData.observeAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Task List", color = Color.White) },
                    elevation = 4.dp
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        taskViewModel.selectedData = TaskModel()
                        onEditCreateTask();
                    },
                    backgroundColor = PrimaryVariant,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true,
            content = {

                if(tasksListView.isEmpty())
                    EmptyIllustration()
                else
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        onRefresh = {
                            context.lifecycleScope.launch {
                                isRefreshing = true; taskViewModel.getTaskList()
                                delay(300); isRefreshing = false
                            }
                        }
                    ) {
                        TaskList(tasks = tasksListView,{
                            context.lifecycleScope.launch{
                                taskViewModel.deleteTask(it)
                                tasksListView.remove(it)
                            }
                        }){
                            taskViewModel.selectedData = it
                            onEditCreateTask()
                        }
                    }
            }
        )

        LaunchedEffect(true){
            if(taskListState.value?.getBareContent().isNullOrEmpty())
                taskViewModel.getTaskList()
        }

        ResourceEffect(
            taskListState,
            onSuccess = { tasksListView.clear();tasksListView.addAll(it.body)},
            onFail = { context.showToast(it.exception.message.toString()) } )

        ResourceEffect(
            taskDeleteState,
            onSuccess = {taskViewModel.getTaskList()},
            onFail = { context.showToast(it.exception.message.toString()) }
        )
    }
}

@Composable
fun EmptyIllustration() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Empty Illustration",
            tint = Color.Gray,
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp),
        )
        Text(
            text = "No Item Found",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}