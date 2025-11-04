package com.lazypizza.lazypizzaapp.design_systems.utils

// Regex patterns for phone validation
object PhoneValidation {
    // US/Canada format: +1 followed by 10 digits
    // Accepts: +1 234 567 8901, +12345678901, +1-234-567-8901
    private val US_PATTERN = Regex("^\\+?1\\s?\\(?([0-9]{3})\\)?[\\s.-]?([0-9]{3})[\\s.-]?([0-9]{4})$")
    
    // Uzbekistan format: +998 followed by 9 digits
    // Accepts: +998 12 345 67 89, +998123456789, +998-12-345-67-89
    private val UZ_PATTERN = Regex("^\\+?998\\s?([0-9]{2})\\s?([0-9]{3})\\s?([0-9]{2})\\s?([0-9]{2})$")
    
    // Strict patterns (only digits, no separators)
    private val US_STRICT = Regex("^1[0-9]{10}$")
    private val UZ_STRICT = Regex("^998[0-9]{9}$")
    
    /**
     * Validates phone number for both US (+1) and Uzbekistan (+998) formats
     * @param phone The phone number string (with or without formatting)
     * @return true if valid, false otherwise
     */
    fun isValid(phone: String): Boolean {
        val cleaned = phone.replace(Regex("[\\s()-]"), "").removePrefix("+")
        return when {
            cleaned.startsWith("1") -> US_STRICT.matches(cleaned)
            cleaned.startsWith("998") -> UZ_STRICT.matches(cleaned)
            else -> false
        }
    }
    
    /**
     * Validates and returns the country code if valid
     * @param phone The phone number string
     * @return Country code ("1" or "998") if valid, null otherwise
     */
    fun getCountryCode(phone: String): String? {
        val cleaned = phone.replace(Regex("[\\s()-]"), "").removePrefix("+")
        return when {
            cleaned.startsWith("1") && US_STRICT.matches(cleaned) -> "1"
            cleaned.startsWith("998") && UZ_STRICT.matches(cleaned) -> "998"
            else -> null
        }
    }
    
    /**
     * Extracts only digits from phone number
     * @param phone The phone number string with any formatting
     * @return String containing only digits
     */
    fun extractDigits(phone: String): String {
        return phone.filter { it.isDigit() }
    }
    
    /**
     * Checks if phone is complete (has all required digits)
     */
    fun isComplete(phone: String): Boolean {
        val digits = extractDigits(phone)
        return when {
            digits.startsWith("1") -> digits.length == 11 // 1 + 10 digits
            digits.startsWith("998") -> digits.length == 12 // 998 + 9 digits
            else -> false
        }
    }
}