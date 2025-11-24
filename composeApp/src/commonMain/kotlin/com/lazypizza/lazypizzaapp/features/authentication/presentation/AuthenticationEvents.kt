package com.lazypizza.lazypizzaapp.features.authentication.presentation

sealed interface AuthenticationEvents {
    data class OnMessage(val message: String) : AuthenticationEvents
    data object OnNavigateBack : AuthenticationEvents
}