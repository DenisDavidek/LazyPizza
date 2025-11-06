package com.lazypizza.lazypizzaapp

import androidx.compose.runtime.Composable
import com.lazypizza.lazypizzaapp.features.authentication.model.service.PhoneAuthService

@Composable
expect fun onBackClick(action: () -> Unit)

expect fun getPhoneAuthService(): PhoneAuthService