package com.example.tasklist

import android.app.Application
import com.example.tasklist.basecomponent.AppModule

class MyApp : Application() {

    val appModule by lazy {
        AppModule(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}