package com.example.tasklist.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tasklist.basecomponent.ActivityInjector
import com.example.tasklist.basecomponent.BaseActivity
import com.example.tasklist.basecomponent.Inject
import com.example.tasklist.basecomponent.ui.theme.TasklistTheme
import com.example.tasklist.feature.TaskViewModel

@ExperimentalMaterialApi
class MainActivity : BaseActivity() {

    @Inject
    lateinit var taskViewModel: TaskViewModel

    private lateinit var navController : NavHostController

    override fun inject(injector: ActivityInjector) {
        injector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasklistTheme {
                navController = rememberNavController()
                TaskComposeNavigationHost(taskViewModel, navController)
            }
        }
    }
}
