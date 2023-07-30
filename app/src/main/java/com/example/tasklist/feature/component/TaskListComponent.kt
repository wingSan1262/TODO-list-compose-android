@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.example.tasklist.feature.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasklist.basecomponent.getTimeAddZero
import com.example.tasklist.domain.FormattedDateTime
import com.example.tasklist.domain.TaskModel
import kotlin.math.absoluteValue


@Composable
fun TaskList(
    tasks: List<TaskModel>,
    onRemoveItem: (TaskModel) -> Unit,
    onSelectTask : (TaskModel) -> Unit,
) {
    LazyColumn {
        items(
            tasks,
            key = {it.id}
        ){
            TaskItem(task = it, onRemoveItem, onSelectTask)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(task: TaskModel, onRemoveItem: (TaskModel) -> Unit, onSelectTask: (TaskModel) -> Unit) {

    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onRemoveItem(task)
    }
    Spacer(modifier = Modifier.height(8.dp))
    SwipeToDismiss(
        dismissState,
        Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp),
        directions = setOf(
            DismissDirection.EndToStart
        ),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.5f else 0.3f)
        },
        background = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.White
                    else -> Color.Red
                }
            )
            val alignment = Alignment.CenterEnd
            val icon = Icons.Default.Delete

            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = Dp(20f)),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = "Delete Icon",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            Card(
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectTask(task) }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = task.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(
                        text = "Start: ${task.startTime?.dayOfWeek}, ${task.startTime?.monthName}" +
                                " ${task.startTime?.dayOfMonth}, ${task.startTime?.year} " +
                                "at ${task.startTime?.hour?.getTimeAddZero()}:${task.startTime?.minute?.getTimeAddZero()}",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "End: ${task.endTime?.dayOfWeek}, ${task.endTime?.monthName} " +
                                "${task.endTime?.dayOfMonth}, ${task.endTime?.year} " +
                                "at ${task.endTime?.hour?.getTimeAddZero()}:${task.endTime?.minute?.getTimeAddZero()}",
                        fontSize = 14.sp
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewTaskList() {
    TaskList(tasks = tasks,{}){}
}

val tasks: List<TaskModel> = listOf(
    TaskModel(
        id = 1,
        name = "Task 1",
        startTime = FormattedDateTime("Sunday", 26, 7, "July", 2023, 9, 30),
        endTime = FormattedDateTime("Monday", 27, 7, "July", 2023, 14, 0)
    ),
    TaskModel(
        id = 2,
        name = "Task 2",
        startTime = FormattedDateTime("Tuesday", 28, 7, "July", 2023, 12, 0),
        endTime = FormattedDateTime("Wednesday", 29, 7, "July", 2023, 15, 30)
    ),
)