package com.lazypizza.lazypizzaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.lazypizza.lazypizzaapp.app.ActivityProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Firebase.initialize(context = application)
        setContent {
            App()
        }
        ActivityProvider.currentActivity = this
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}