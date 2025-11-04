package com.lazypizza.lazypizzaapp.features.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazypizza.lazypizzaapp.design_systems.utils.PhoneValidation
import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.AuthStage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.collections.map

class AuthenticationViewModel : ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState())
    val state = _state.asStateFlow()

    fun onAction(action: AuthenticationAction) {
        when (action) {
            AuthenticationAction.OnContinueClick -> {
                _state.update { it.copy(currentStage = AuthStage.Verification) }

                // TODO Implement send code
            }


            AuthenticationAction.OnConfirmClick -> {
                // TODO Implement otp check

            }

            is AuthenticationAction.OnCodeDigitEnter -> {
                val map = _state.value.verificationDigits.mapIndexed { index, digit ->
                    if (index == action.index) {
                        action.value
                    } else digit
                }
                _state.update { state ->
                    state.copy(
                        verificationDigits = map,
                        confirmEnabled = !map.any { it.isBlank() }
                    )
                }
            }
            is AuthenticationAction.OnDeleteDigit -> {
                _state.update { state ->
                    state.copy(
                        verificationDigits = state.verificationDigits.mapIndexed { index, digit ->
                            if (index == action.index) {
                                ""
                            } else digit
                        }
                    )
                }
            }

            is AuthenticationAction.OnPhoneNumberChange -> {
                if (action.phone.all(Char::isDigit)) {
                    val isPhoneValid = PhoneValidation.isValid(action.phone)
                    _state.update {
                        it.copy(
                            phoneNumber = action.phone,
                            continueEnabled = isPhoneValid
                        )
                    }
                }
            }

            AuthenticationAction.OnResendCodeClick -> {
                // TODO Implement resend code flow
            }

            else -> {}
        }
    }

}