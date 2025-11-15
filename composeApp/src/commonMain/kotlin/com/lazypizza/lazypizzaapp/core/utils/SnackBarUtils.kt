package com.lazypizza.lazypizzaapp.core.utils

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.showSnackBar(snackBarHostState: SnackbarHostState, message: String){
    this.launch {
        snackBarHostState.showSnackbar(
            message = message,
        )
    }
}