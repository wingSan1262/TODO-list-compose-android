package com.example.tasklist.basecomponent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import com.example.tasklist.domain.FormattedDateTime
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

@Composable
fun <Model> ResourceEffect(
    state : State<Event<ResourceState<Model>>?>,
    onSuccess: suspend CoroutineScope.(data : ResourceState.Success<Model>)-> Unit = {},
    onFail: suspend CoroutineScope.(data : ResourceState.Failure<Model>)-> Unit = {},
){
    state.value?.run{
        val dataObserve = this.nonFilteredContent()
        LaunchedEffect(dataObserve) {
            when(dataObserve){
                is ResourceState.Success -> {
                    onSuccess(dataObserve)
                }
                is ResourceState.Failure -> {
                    onFail(dataObserve)
                }
            }
        }
    }
}

@Composable
fun <Model> EventEffect(
    state : State<Event<ResourceState<Model>>?>,
    onSuccess: suspend CoroutineScope.(data : ResourceState.Success<Model>)-> Unit = {},
    onFail: suspend CoroutineScope.(data : ResourceState.Failure<Model>)-> Unit = {},
){
    state.value?.run{
        val dataObserve = this.contentIfNotHandled
        LaunchedEffect(dataObserve) {
            when(dataObserve){
                is ResourceState.Success -> {
                    onSuccess(dataObserve)
                }
                is ResourceState.Failure -> {
                    onFail(dataObserve)
                }
            }
        }
    }
}

@Composable
fun showToastCompose(msg: String){
    val context = LocalContext.current as ComponentActivity
    context.showToast(msg)
}


@Composable
fun PickDateTime(
    dateObtained: (FormattedDateTime) -> Unit
) {

    val owner = LocalContext.current as ComponentActivity

    val currentDateTime = Calendar.getInstance()
    val startYear = currentDateTime.get(Calendar.YEAR)
    val startMonth = currentDateTime.get(Calendar.MONTH)
    val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
    val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
    val startMinute = currentDateTime.get(Calendar.MINUTE)

    DatePickerDialog(owner, { _, year, month, day ->
        TimePickerDialog(owner, { _, hour, minute ->
            val selectedDateTime = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            val formattedDateTime = FormattedDateTime(
                dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDateTime.time),
                dayOfMonth = day,
                month = month + 1, // Adding 1 because Calendar months are zero-indexed
                monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(selectedDateTime.time),
                year = year,
                hour = hour,
                minute = minute
            )
            dateObtained(formattedDateTime)
        }, startHour, startMinute, false
        ).show()
    }, startYear, startMonth, startDay).show()
}

fun <Content> Event<ResourceState<Content>>.getBareContent() : Content? {
    this.nonFilteredContent().run {
        when(this){
            is ResourceState.Success -> {
                return this.body
            }
            is ResourceState.Failure -> {
                return this.body
            }
        }
    }
}

fun Int.getTimeAddZero() : String{
    return if(this > 9) this.toString() else "0$this"
}