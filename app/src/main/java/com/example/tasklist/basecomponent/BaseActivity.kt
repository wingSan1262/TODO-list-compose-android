package com.example.tasklist.basecomponent

import android.os.Bundle
import androidx.activity.ComponentActivity

abstract class BaseActivity : ComponentActivity() {

    val activityInjector by lazy {
        ActivityInjector(this)}

    abstract fun inject(injector : ActivityInjector)

    override fun onCreate(savedInstanceState: Bundle?) {
        inject(activityInjector)
        super.onCreate(savedInstanceState)
    }
}