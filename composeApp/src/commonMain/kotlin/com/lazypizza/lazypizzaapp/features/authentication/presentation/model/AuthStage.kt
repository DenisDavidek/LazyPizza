package com.lazypizza.lazypizzaapp.features.authentication.presentation.model

enum class AuthStage {
    EnterPhone,
    Verification;

    fun subtitle(): String {
        return when (this) {
            EnterPhone -> "Enter your phone number"
            Verification -> "Enter code"
        }
    }
    fun buttonText(): String {
        return when (this) {
            EnterPhone -> "Continue"
            Verification -> "Confirm"
        }
    }
}