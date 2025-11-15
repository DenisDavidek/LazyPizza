package com.lazypizza.lazypizzaapp

import androidx.compose.runtime.Composable
import com.lazypizza.lazypizzaapp.core.domain.model.User
import com.lazypizza.lazypizzaapp.features.authentication.domain.service.PhoneAuthService
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Composable
actual fun onBackClick(action: () -> Unit) {
}

actual fun getPhoneAuthService(): PhoneAuthService {
    TODO("Not yet implemented")
}

@Composable
actual fun rememberUser(): User? {
    TODO("Not yet implemented")
}

actual fun logoutUser() {
}