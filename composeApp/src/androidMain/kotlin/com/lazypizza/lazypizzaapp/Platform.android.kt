package com.lazypizza.lazypizzaapp

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.lazypizza.lazypizzaapp.features.authentication.data.service.AndroidPhoneAuthService
import com.lazypizza.lazypizzaapp.features.authentication.model.service.PhoneAuthService

@Composable
actual fun onBackClick(action: () -> Unit) {
    BackHandler {
        action()
    }
}

actual fun getPhoneAuthService(): PhoneAuthService {
    return AndroidPhoneAuthService()
}