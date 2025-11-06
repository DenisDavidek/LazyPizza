package com.lazypizza.lazypizzaapp

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.lazypizza.lazypizzaapp.core.domain.model.User
import com.lazypizza.lazypizzaapp.features.authentication.data.service.AndroidPhoneAuthService
import com.lazypizza.lazypizzaapp.features.authentication.model.service.PhoneAuthService

@Composable
actual fun onBackClick(action: () -> Unit) {
    BackHandler {
        action()
    }
}

actual fun getPhoneAuthService(): PhoneAuthService {
    return AndroidPhoneAuthService()
}

@Composable
actual fun rememberUser(): User? {
    val auth = Firebase.auth

    var currentUser by remember { mutableStateOf(auth.currentUser) }

    DisposableEffect(auth) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            currentUser = firebaseAuth.currentUser
        }
        auth.addAuthStateListener(listener)

        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    return remember(currentUser) {
        currentUser?.let { firebaseUser ->
            User(
                phone = firebaseUser.phoneNumber!!,
            )
        }
    }
}