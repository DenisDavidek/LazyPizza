package com.lazypizza.lazypizzaapp.core.utils

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable


@Composable
actual fun isPortrait(): Boolean {
    var portrait = true
    BoxWithConstraints {
        portrait = maxHeight > maxWidth
    }
    return portrait
}