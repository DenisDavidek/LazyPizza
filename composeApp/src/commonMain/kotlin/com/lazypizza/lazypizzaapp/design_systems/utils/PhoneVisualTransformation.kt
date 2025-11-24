package com.lazypizza.lazypizzaapp.design_systems.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { it.isDigit() }

        val countryCodeLength = when {
            digitsOnly.startsWith("1") -> 1
            digitsOnly.startsWith("421") -> 3
            digitsOnly.startsWith("998") -> 3
            else -> 0
        }

        val formatted = buildString {
            digitsOnly.forEachIndexed { index, char ->
                when {
                    // Country code
                    index == 0 -> append(char)
                    index < countryCodeLength -> append(char)
                    index == countryCodeLength -> append(" $char")
                    // US: +1 234 567 8901
                    countryCodeLength == 1 && (index == 4 || index == 7) -> append(" $char")
                    // Slovakia: +421 123 456 789
                    digitsOnly.startsWith("421") && (index == 6 || index == 9) -> append(" $char")
                    // Uzbekistan: +998 12 345 67 89
                    digitsOnly.startsWith("998") && (index == 5 || index == 8) -> append(" $char")
                    else -> append(char)
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val digitsBeforeOffset = text.text.take(offset).count { it.isDigit() }
                val countryCodeLen = when {
                    digitsOnly.startsWith("1") -> 1
                    digitsOnly.startsWith("421") -> 3
                    digitsOnly.startsWith("998") -> 3
                    else -> 0
                }

                return when {
                    digitsBeforeOffset <= countryCodeLen -> digitsBeforeOffset
                    countryCodeLen == 1 -> {
                        when {
                            digitsBeforeOffset <= 4 -> digitsBeforeOffset + 1
                            digitsBeforeOffset <= 7 -> digitsBeforeOffset + 2
                            else -> digitsBeforeOffset + 3
                        }
                    }
                    digitsOnly.startsWith("421") -> {
                        when {
                            digitsBeforeOffset <= 6 -> digitsBeforeOffset + 1
                            digitsBeforeOffset <= 9 -> digitsBeforeOffset + 2
                            else -> digitsBeforeOffset + 3
                        }
                    }
                    digitsOnly.startsWith("998") -> {
                        when {
                            digitsBeforeOffset <= 5 -> digitsBeforeOffset + 1
                            digitsBeforeOffset <= 8 -> digitsBeforeOffset + 2
                            else -> digitsBeforeOffset + 3
                        }
                    }
                    else -> digitsBeforeOffset
                }.coerceAtMost(formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val countryCodeLen = when {
                    digitsOnly.startsWith("1") -> 1
                    digitsOnly.startsWith("421") -> 3
                    digitsOnly.startsWith("998") -> 3
                    else -> 0
                }

                return when {
                    offset <= countryCodeLen -> offset
                    countryCodeLen == 1 -> {
                        when {
                            offset <= 2 -> 1
                            offset <= 5 -> offset - 1
                            offset <= 6 -> 4
                            offset <= 9 -> offset - 2
                            offset <= 10 -> 7
                            else -> offset - 3
                        }
                    }
                    digitsOnly.startsWith("421") -> {
                        when {
                            offset <= 4 -> 3
                            offset <= 7 -> offset - 1
                            offset <= 8 -> 6
                            offset <= 11 -> offset - 2
                            offset <= 12 -> 9
                            else -> offset - 3
                        }
                    }
                    digitsOnly.startsWith("998") -> {
                        when {
                            offset <= 4 -> 3
                            offset <= 6 -> offset - 1
                            offset <= 7 -> 5
                            offset <= 10 -> offset - 2
                            offset <= 11 -> 8
                            else -> offset - 3
                        }
                    }
                    else -> offset
                }.coerceAtMost(digitsOnly.length)
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}