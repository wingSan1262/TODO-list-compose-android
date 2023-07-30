package com.example.tasklist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tasklist.domain.TaskModel
import com.example.tasklist.domain.formatDateToFormattedDateTime

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val startTime: Long,
    val endTime: Long
){
    fun toTaskModel(): TaskModel {
        return TaskModel(
            id = id,
            name = name,
            startTime = startTime.formatDateToFormattedDateTime(),
            endTime = endTime.formatDateToFormattedDateTime()
        )
    }
}