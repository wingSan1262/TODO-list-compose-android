package com.example.tasklist.domain

import com.example.tasklist.data.local.entity.TaskEntity
import java.text.SimpleDateFormat
import java.util.*

data class TaskModel(
    val id: Long = 0,
    val name: String = "",
    val startTime: FormattedDateTime? = null,
    val endTime: FormattedDateTime? = null
){
    fun toTaskEntity(): TaskEntity{
        return TaskEntity(
            id, name, startTime?.toDateTimeInMillis() ?: 0,
            endTime?.toDateTimeInMillis() ?: 0
        )
    }

    fun validateData(errorMsg: (String) -> Unit): Boolean {
        if (name.isEmpty()) {
            errorMsg("Name is Empty")
            return false
        }

        val currentTimeInMillis = Calendar.getInstance().timeInMillis

        if (startTime == null || endTime == null) {
            errorMsg("Start Time and End Time cannot be null")
            return false
        }

        if (startTime.toDateTimeInMillis() < currentTimeInMillis) {
            errorMsg("Your Current Time Exceeds Start Time")
            return false
        }

        if (startTime.toDateTimeInMillis() >= endTime.toDateTimeInMillis()) {
            errorMsg("Your Start Time Exceeds End Time")
            return false
        }

        return true
    }
}

data class FormattedDateTime(
    val dayOfWeek: String,
    val dayOfMonth: Int,
    val month: Int,
    val monthName: String,
    val year: Int,
    val hour: Int,
    val minute: Int
){
    fun toDateTimeInMillis(): Long {
        val calendar = Calendar.getInstance()
        // month - 1 since Calendar months are 0-indexed
        calendar.set(year, month - 1, dayOfMonth, hour, minute, 0)
        return calendar.timeInMillis
    }
}

fun Long.formatDateToFormattedDateTime(): FormattedDateTime {
    val sdfDayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault())
    val sdfDayOfMonth = SimpleDateFormat("dd", Locale.getDefault())
    val sdfMonth = SimpleDateFormat("MM", Locale.getDefault())
    val sdfMonthName = SimpleDateFormat("MMMM", Locale.getDefault())
    val sdfYear = SimpleDateFormat("yyyy", Locale.getDefault())
    val sdfHour = SimpleDateFormat("HH", Locale.getDefault())
    val sdfMinute = SimpleDateFormat("mm", Locale.getDefault())

    val date = Date(this)

    return FormattedDateTime(
        dayOfWeek = sdfDayOfWeek.format(date),
        dayOfMonth = sdfDayOfMonth.format(date).toInt(),
        month = sdfMonth.format(date).toInt(),
        monthName = sdfMonthName.format(date),
        year = sdfYear.format(date).toInt(),
        hour = sdfHour.format(date).toInt(),
        minute = sdfMinute.format(date).toInt()
    )
}