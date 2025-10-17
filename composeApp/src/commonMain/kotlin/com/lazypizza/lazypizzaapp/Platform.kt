package com.lazypizza.lazypizzaapp

import androidx.compose.runtime.Composable

@Composable
expect fun onBackClick(action: () -> Unit)