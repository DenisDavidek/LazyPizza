package com.lazypizza.lazypizzaapp.features.authentication.presentation.model

sealed class ResendCodeState {
    data object Ready : ResendCodeState()
    data class Reloading(val remainingSeconds: Int) : ResendCodeState()
}
