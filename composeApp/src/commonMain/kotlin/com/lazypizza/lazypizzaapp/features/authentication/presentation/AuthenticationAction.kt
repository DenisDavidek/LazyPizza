package com.lazypizza.lazypizzaapp.features.authentication.presentation

sealed interface AuthenticationAction {
    data class OnPhoneNumberChange(val phone: String) : AuthenticationAction
    data class OnCodeDigitEnter(val index: Int, val value: String) : AuthenticationAction
    data class OnDeleteDigit(val index: Int) : AuthenticationAction
    data object OnResendCodeClick : AuthenticationAction
    data object OnContinueWithoutSigningClick : AuthenticationAction
    data object OnContinueClick : AuthenticationAction
    data object OnConfirmClick : AuthenticationAction
}