package com.lazypizza.lazypizzaapp.features.authentication.presentation

import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.AuthStage
import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.ResendCodeState

data class AuthenticationState(
    val phoneNumber: String = "",
    val verificationDigits: List<String> = List(6) { "" },
    val currentStage: AuthStage = AuthStage.EnterPhone,
    val continueEnabled: Boolean = false,
    val confirmEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val resendCodeState: ResendCodeState = ResendCodeState.Ready,
    val currentVerificationId: String? = null,
    val errors: Map<String, String> = emptyMap()
) {
    companion object {
        const val ERROR_INCORRECT_CODE = "incorrect_code"
        const val ERROR_SEND_CODE = "send_code_error"
        const val ERROR_INVALID_PHONE = "invalid_phone"
        const val ERROR_VERIFICATION_FAILED = "verification_failed"
    }
}
