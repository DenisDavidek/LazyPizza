package com.lazypizza.lazypizzaapp.features.authentication.model.service

interface PhoneAuthService {
    suspend fun sendVerificationCode(
        phoneNumber: String,
        onCodeSent: (verificationId: String) -> Unit,
        onError: (Exception) -> Unit
    )
    
    suspend fun verifyCode(
        verificationId: String,
        code: String
    ): Result<String> // Returns user ID on success
}