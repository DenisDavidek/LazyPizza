package com.lazypizza.lazypizzaapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform