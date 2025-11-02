package com.lazypizza.lazypizzaapp.core.presentation.locals

import androidx.compose.runtime.compositionLocalOf
import com.lazypizza.lazypizzaapp.core.domain.model.User

internal val LocalUser = compositionLocalOf<User?> {
    error("User not declared")
}