package com.lazypizza.lazypizzaapp.features.authentication.presentation

import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.AuthStage
import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.ResendCodeState

data class AuthenticationState(
    val phoneNumber: String = "",
    val verificationDigits: List<String> = List(4) { "" },
    val currentStage: AuthStage = AuthStage.EnterPhone,
    val continueEnabled: Boolean = false,
    val confirmEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val resendCodeState: ResendCodeState = ResendCodeState.Ready,
    val errors: Map<String, String> = mapOf() // KEY - ERROR MESSAGE
) {
    companion object {
        const val ERROR_INCORRECT_CODE = "incorrect_code"
    }
}