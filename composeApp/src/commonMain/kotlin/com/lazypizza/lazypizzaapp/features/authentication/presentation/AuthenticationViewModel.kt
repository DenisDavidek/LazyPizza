package com.lazypizza.lazypizzaapp.features.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.utils.PhoneValidation
import com.lazypizza.lazypizzaapp.features.authentication.domain.service.PhoneAuthService
import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.AuthStage
import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.ResendCodeState
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authService: PhoneAuthService
) : ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState())
    val state = _state.asStateFlow()

    private val _events = Channel<AuthenticationEvents>()
    val events = _events.receiveAsFlow()

    private var resendTimerJob: Job? = null
    private val resendCodeDelaySeconds = 60

    fun onAction(action: AuthenticationAction) {
        when (action) {
            AuthenticationAction.OnContinueClick -> {
                handleContinueClick()
            }

            AuthenticationAction.OnConfirmClick -> {
                handleConfirmClick()
            }

            is AuthenticationAction.OnCodeDigitEnter -> {
                handleCodeDigitEnter(action.index, action.value)
            }

            is AuthenticationAction.OnDeleteDigit -> {
                handleDeleteDigit(action.index)
            }

            is AuthenticationAction.OnPhoneNumberChange -> {
                handlePhoneNumberChange(action.phone)
            }

            AuthenticationAction.OnResendCodeClick -> {
                handleResendCode()
            }

            else -> { }
        }
    }

    private fun handleContinueClick() {
        val phoneNumber = "+${_state.value.phoneNumber}"

        _state.update { it.copy(errors = emptyMap()) }

        _state.update { it.copy(currentStage = AuthStage.Verification) }

        sendCode(phoneNumber)
    }

    private fun handleConfirmClick() {
        _state.value.let { state ->
            val verificationId = state.currentVerificationId
            val code = state.verificationDigits.joinToString("")

            if (verificationId == null) {
                _state.update {
                    it.copy(
                        errors = it.errors + (AuthenticationState.ERROR_VERIFICATION_FAILED to
                                "Verification session expired. Please request a new code.")
                    )
                }
                viewModelScope.launch {
                    _events.send(
                        AuthenticationEvents.OnMessage("Verification session expired. Please request a new code.")
                    )
                }
                return
            }

            if (code.length != state.verificationDigits.size) {
                _state.update {
                    it.copy(
                        errors = it.errors + (AuthenticationState.ERROR_INCORRECT_CODE to
                                "Please enter all digits")
                    )
                }
                return
            }

            _state.update { it.copy(errors = it.errors - AuthenticationState.ERROR_INCORRECT_CODE) }

            verifyCode(verificationId, code)
        }
    }

    private fun handleCodeDigitEnter(index: Int, value: String) {
        val currentDigits = _state.value.verificationDigits.toMutableList()

        if (value.length > 1) {
            currentDigits[index] = value.last().toString()
        } else {
            currentDigits[index] = value
        }

        val allFilled = currentDigits.none { it.isBlank() }

        _state.update { state ->
            state.copy(
                verificationDigits = currentDigits,
                confirmEnabled = allFilled,
                errors = state.errors - AuthenticationState.ERROR_INCORRECT_CODE
            )
        }
    }

    private fun handleDeleteDigit(index: Int) {
        val currentDigits = _state.value.verificationDigits.toMutableList()
        currentDigits[index] = ""

        _state.update { state ->
            state.copy(
                verificationDigits = currentDigits,
                confirmEnabled = false,
                errors = state.errors - AuthenticationState.ERROR_INCORRECT_CODE
            )
        }
    }

    private fun handlePhoneNumberChange(phone: String) {
        if (phone.all(Char::isDigit)) {
            val isPhoneValid = PhoneValidation.isValid(phone)
            _state.update {
                it.copy(
                    phoneNumber = phone,
                    continueEnabled = isPhoneValid,
                    errors = it.errors - AuthenticationState.ERROR_INVALID_PHONE
                )
            }
        }
    }

    private fun handleResendCode() {
        val currentState = _state.value.resendCodeState

        if (currentState !is ResendCodeState.Ready) {
            return
        }

        val phoneNumber = "+${_state.value.phoneNumber}"

        _state.update {
            it.copy(
                verificationDigits = List(it.verificationDigits.size) { "" },
                confirmEnabled = false,
                errors = emptyMap()
            )
        }

        sendCode(phoneNumber)
    }

    private fun sendCode(phoneNumber: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            authService.sendVerificationCode(
                phoneNumber = phoneNumber,
                onCodeSent = { verificationId ->
                    Logger.d { "Code was sent successfully. Verification ID: $verificationId" }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            currentVerificationId = verificationId,
                            errors = emptyMap()
                        )
                    }

                    startResendTimer()

                    viewModelScope.launch {
                        _events.send(
                            AuthenticationEvents.OnMessage("Verification code sent to $phoneNumber")
                        )
                    }
                },
                onError = { error ->
                    Logger.e { "Error occurred while sending code: ${error.message}" }

                    val errorMessage = parseErrorMessage(error)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            errors = it.errors + (AuthenticationState.ERROR_SEND_CODE to errorMessage)
                        )
                    }

                    viewModelScope.launch {
                        _events.send(AuthenticationEvents.OnMessage(errorMessage))
                    }

                    if (_state.value.currentStage == AuthStage.Verification) {
                        _state.update { it.copy(resendCodeState = ResendCodeState.Ready) }
                    } else {
                        _state.update {
                            it.copy(
                                currentStage = AuthStage.EnterPhone,
                                resendCodeState = ResendCodeState.Ready
                            )
                        }
                    }
                }
            )
        }
    }

    private fun verifyCode(verificationId: String, code: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = authService.verifyCode(verificationId, code)

            result.onSuccess { userId ->
                Logger.d { "Verification successful. User ID: $userId" }

                _state.update {
                    it.copy(
                        isLoading = false,
                        errors = emptyMap()
                    )
                }

                resendTimerJob?.cancel()

                _events.send(
                    AuthenticationEvents.OnNavigateBack
                )

            }.onFailure { error ->
                Logger.e { "Verification failed: ${error.message}" }

                val errorMessage = parseVerificationErrorMessage(error)

                _state.update {
                    it.copy(
                        isLoading = false,
                        errors = it.errors + (AuthenticationState.ERROR_INCORRECT_CODE to errorMessage),
                        verificationDigits = List(it.verificationDigits.size) { "" },
                        confirmEnabled = false
                    )
                }

                _events.send(AuthenticationEvents.OnMessage(errorMessage))
            }
        }
    }

    private fun startResendTimer() {
        resendTimerJob?.cancel()

        resendTimerJob = viewModelScope.launch {
            for (remaining in resendCodeDelaySeconds downTo 0) {
                _state.update {
                    it.copy(
                        resendCodeState = if (remaining > 0) {
                            ResendCodeState.Reloading(remaining)
                        } else {
                            ResendCodeState.Ready
                        }
                    )
                }

                if (remaining > 0) {
                    delay(1000)
                }
            }
        }
    }

    private fun parseErrorMessage(error: Exception): String {
        return when {
            error.message?.contains("region", ignoreCase = true) == true ->
                "SMS service is not available in your region"

            error.message?.contains("quota", ignoreCase = true) == true ->
                "SMS quota exceeded. Please try again later"

            error.message?.contains("invalid", ignoreCase = true) == true ->
                "Invalid phone number format. Please check and try again"

            error.message?.contains("too many requests", ignoreCase = true) == true ||
                    error.message?.contains("too-many-requests", ignoreCase = true) == true ->
                "Too many attempts. Please try again in a few minutes"

            error.message?.contains("app identifier", ignoreCase = true) == true ||
                    error.message?.contains("play integrity", ignoreCase = true) == true ||
                    error.message?.contains("recaptcha", ignoreCase = true) == true ->
                "App verification failed. Please check your internet connection and try again"

            error.message?.contains("network", ignoreCase = true) == true ->
                "Network error. Please check your internet connection"

            else ->
                "Failed to send verification code. Please try again"
        }
    }

    private fun parseVerificationErrorMessage(error: Throwable): String {
        return when {
            error.message?.contains("invalid", ignoreCase = true) == true ->
                "Invalid verification code. Please try again"

            error.message?.contains("expired", ignoreCase = true) == true ->
                "Verification code expired. Please request a new code"

            error.message?.contains("session", ignoreCase = true) == true ->
                "Verification session expired. Please request a new code"

            error.message?.contains("code", ignoreCase = true) == true ->
                "Incorrect verification code. Please try again"

            else ->
                "Incorrect code. Please try again"
        }
    }

    override fun onCleared() {
        super.onCleared()
        resendTimerJob?.cancel()
    }
}