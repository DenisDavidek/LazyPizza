package com.lazypizza.lazypizzaapp.features.authentication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lazypizza.lazypizzaapp.core.utils.ObserveAsEvents
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.components.GradientButton
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.utils.PhoneVisualTransformation
import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.AuthStage
import com.lazypizza.lazypizzaapp.features.authentication.presentation.model.ResendCodeState
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthenticationRoot(
    onNavigateToMain: () -> Unit,
    viewModel: AuthenticationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is AuthenticationEvents.OnMessage -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            AuthenticationEvents.OnNavigateBack -> onNavigateToMain()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        AuthenticationScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    AuthenticationAction.OnContinueWithoutSigningClick -> {
                        onNavigateToMain()
                    }

                    else -> viewModel.onAction(action)
                }
            }
        )
    }
}

@Composable
fun AuthenticationScreen(
    state: AuthenticationState,
    onAction: (AuthenticationAction) -> Unit,
) {
    val windowSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val windowWidth = with(density) { windowSize.width.toDp() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to LazyPizza",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = state.currentStage.subtitle(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        Column (
            modifier = Modifier
                .then(if (windowWidth > 840.dp) {
                    Modifier.widthIn(max = 400.dp)
                } else Modifier)
        ) {
            TextField(
                value = state.phoneNumber,
                onValueChange = { value ->
                    onAction(AuthenticationAction.OnPhoneNumberChange(value))
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                placeholder = {
                    Text(
                        text = "1 000 000 0000",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                prefix = {
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                visualTransformation = PhoneVisualTransformation(),
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth()
            )

            if (state.currentStage == AuthStage.Verification) {
                Spacer(Modifier.height(12.dp))

                val focusRequesters =
                    remember { List(state.verificationDigits.size) { FocusRequester() } }
                val focusManager = LocalFocusManager.current

                LaunchedEffect(state.currentStage) {
                    if (state.currentStage == AuthStage.Verification) {
                        focusRequesters.firstOrNull()?.requestFocus()
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    state.verificationDigits.forEachIndexed { index, digit ->
                        TextField(
                            value = digit,
                            onValueChange = { value ->
                                when {
                                    // Handle paste (multiple digits)
                                    value.length > 1 && value.all(Char::isDigit) -> {
                                        val digits =
                                            value.take(state.verificationDigits.size - index)
                                        digits.forEachIndexed { offset, char ->
                                            val targetIndex = index + offset
                                            if (targetIndex < state.verificationDigits.size) {
                                                onAction(
                                                    AuthenticationAction.OnCodeDigitEnter(
                                                        targetIndex,
                                                        char.toString()
                                                    )
                                                )
                                            }
                                        }
                                        val nextEmptyIndex =
                                            state.verificationDigits.indexOfFirst { it.isEmpty() }
                                        if (nextEmptyIndex != -1) {
                                            focusRequesters[nextEmptyIndex].requestFocus()
                                        } else {
                                            focusManager.clearFocus()
                                        }
                                    }

                                    value.length == 1 && value.all(Char::isDigit) -> {
                                        onAction(
                                            AuthenticationAction.OnCodeDigitEnter(
                                                index,
                                                value
                                            )
                                        )
                                        if (index < state.verificationDigits.size - 1) {
                                            focusRequesters[index + 1].requestFocus()
                                        } else {
                                            focusManager.clearFocus()
                                        }
                                    }

                                    value.isEmpty() && digit.isNotEmpty() -> {
                                        onAction(AuthenticationAction.OnDeleteDigit(index))
                                    }
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            ),
                            textStyle = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            ),
                            placeholder = {
                                Text(
                                    text = "0",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        textAlign = TextAlign.Center
                                    ),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = if (index == state.verificationDigits.size - 1) {
                                    ImeAction.Done
                                } else ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    if (index < state.verificationDigits.size - 1) {
                                        focusRequesters[index + 1].requestFocus()
                                    }
                                },
                                onDone = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            singleLine = true,
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequesters[index])
                                .onKeyEvent { event ->
                                    if (event.key == Key.Backspace &&
                                        event.type == KeyEventType.KeyDown &&
                                        digit.isEmpty() &&
                                        index > 0
                                    ) {
                                        onAction(AuthenticationAction.OnDeleteDigit(index - 1))
                                        focusRequesters[index - 1].requestFocus()
                                        true
                                    } else {
                                        false
                                    }
                                }
                                .border(
                                    width = 1.dp,
                                    color = if (state.errors[AuthenticationState.ERROR_INCORRECT_CODE] != null) {
                                        MaterialTheme.colorScheme.primary
                                    } else MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                        )
                    }
                }

                if (state.errors[AuthenticationState.ERROR_INCORRECT_CODE] != null) {
                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Incorrect code. Please try again",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            GradientButton(
                onClick = {
                    when (state.currentStage) {
                        AuthStage.EnterPhone -> {
                            onAction(AuthenticationAction.OnContinueClick)
                        }

                        AuthStage.Verification -> {
                            onAction(AuthenticationAction.OnConfirmClick)
                        }
                    }
                },
                buttonText = state.currentStage.buttonText(),
                colors = listOf(
                    Color(0xffF9966F),
                    Color(0xffF36B50),
                ),
                shadowColor = MaterialTheme.colorScheme.primary.copy(.25f),
                modifier = Modifier.fillMaxWidth(),
                enabled = when (state.currentStage) {
                    AuthStage.EnterPhone -> state.continueEnabled
                    AuthStage.Verification -> state.confirmEnabled
                }
            )

            TextButton(
                onClick = {
                    onAction(AuthenticationAction.OnContinueWithoutSigningClick)
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Continue without signing in",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            if (state.currentStage == AuthStage.Verification) {
                TextButton(
                    onClick = {
                        onAction(AuthenticationAction.OnResendCodeClick)
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    enabled = state.resendCodeState == ResendCodeState.Ready
                ) {
                    Text(
                        text = when (val resendCodeState = state.resendCodeState) {
                            ResendCodeState.Ready -> "Resend code"
                            is ResendCodeState.Reloading -> "You can request a new code in 00:${resendCodeState.remainingSeconds}"
                        },
                        style = when (state.resendCodeState) {
                            ResendCodeState.Ready -> MaterialTheme.typography.titleSmall
                            is ResendCodeState.Reloading -> MaterialTheme.typography.bodySmall
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        }


    if (state.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(.2f))
                .pointerInput(Unit) { },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        AuthenticationScreen(
            state = AuthenticationState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun Preview2() {
    AppTheme {
        AuthenticationScreen(
            state = AuthenticationState(
                currentStage = AuthStage.Verification,
                resendCodeState = ResendCodeState.Reloading(20)
            ),
            onAction = {}
        )
    }
}