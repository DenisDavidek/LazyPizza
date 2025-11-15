package com.lazypizza.lazypizzaapp.features.authentication.data.service

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.lazypizza.lazypizzaapp.app.ActivityProvider
import com.lazypizza.lazypizzaapp.features.authentication.domain.service.PhoneAuthService
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidPhoneAuthService : PhoneAuthService {

    private val auth = FirebaseAuth.getInstance().apply {
        firebaseAuthSettings.setAppVerificationDisabledForTesting(false)
        firebaseAuthSettings.forceRecaptchaFlowForTesting(true)
    }

    override suspend fun sendVerificationCode(
        phoneNumber: String,
        onCodeSent: (verificationId: String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        suspendCoroutine { continuation ->
            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Auto-retrieval or instant verification
                    signInWithCredential(credential, onCodeSent, onError)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onError(e)
                    continuation.resume(Unit)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    onCodeSent(verificationId)
                    continuation.resume(Unit)
                }
            }

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(getCurrentActivity())
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    override suspend fun verifyCode(
        verificationId: String,
        code: String
    ): Result<String> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            val result = auth.signInWithCredential(credential).await()
            Result.success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun signInWithCredential(
        credential: PhoneAuthCredential,
        onCodeSent: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                onCodeSent(result.user?.uid ?: "")
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    private fun getCurrentActivity(): Activity {
        return ActivityProvider.currentActivity ?: throw NotImplementedError()
    }
}