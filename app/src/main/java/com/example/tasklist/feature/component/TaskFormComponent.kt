@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.tasklist.feature.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasklist.basecomponent.PickDateTime
import com.example.tasklist.basecomponent.getTimeAddZero
import com.example.tasklist.domain.TaskModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskForm(
    taskModel: TaskModel,
    onSubmitting: (TaskModel)->Unit,
    isSubmitting : Boolean
) {

    var taskState by remember { mutableStateOf(taskModel) }

    var pickDateStateStart by remember { mutableStateOf(false) }
    if(pickDateStateStart)
        PickDateTime { pickDateStateStart = false;
            taskState = taskState.copy(startTime = it) }

    var pickDateStateEnd by remember { mutableStateOf(false) }
    if(pickDateStateEnd)
        PickDateTime { pickDateStateEnd = false;
            taskState = taskState.copy(endTime = it) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        BasicEditText(
            value = taskState.name,
            onValueChange = { taskState = taskState.copy(name = it) },
            label = "Item Name"
        )

        Spacer(modifier = Modifier.height(16.dp))

        taskState.startTime.run {
            BasicEditText(
                value = if(this != null)
                    "$dayOfWeek $dayOfMonth $monthName $year, ${hour.getTimeAddZero()}" +
                            ":${minute.getTimeAddZero()}" else "",
                onValueChange = {},
                label = "Start Time",
                enabled = false,
                onClick = {
                    pickDateStateStart = true
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        taskState.endTime.run {
            BasicEditText(
                value = if(this != null)
                    "$dayOfWeek $dayOfMonth $monthName $year, ${hour.getTimeAddZero()}" +
                            ":${minute.getTimeAddZero()}" else "",
                onValueChange = {},
                label = "End Time",
                enabled = false,
                onClick = {
                    pickDateStateEnd = true
                }
            )
        }

        CommonActionButton(
            {onSubmitting(taskState)},
            !isSubmitting,
            isSubmitting,
            "Submit"
        )
    }
}

@Preview()
@Composable
fun previewTaskForm(){
    TaskForm(taskModel = TaskModel(), onSubmitting = {}, isSubmitting = false)
}

@Composable
fun BasicEditText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = if(value.isEmpty()) Gray else Black
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
        enabled = enabled,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(label, color =  Color.Gray)
            }
            innerTextField()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
            .border(1.dp, Gray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    )
}