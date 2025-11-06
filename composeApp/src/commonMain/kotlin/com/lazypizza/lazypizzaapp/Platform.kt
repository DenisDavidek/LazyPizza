package com.lazypizza.lazypizzaapp

import androidx.compose.runtime.Composable
import com.lazypizza.lazypizzaapp.core.domain.model.User
import com.lazypizza.lazypizzaapp.features.authentication.model.service.PhoneAuthService

@Composable
expect fun onBackClick(action: () -> Unit)

expect fun getPhoneAuthService(): PhoneAuthService

@Composable
expect fun rememberUser(): User?

expect fun logoutUser()