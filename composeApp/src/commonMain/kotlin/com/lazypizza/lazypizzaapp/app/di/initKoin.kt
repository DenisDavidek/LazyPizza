package com.lazypizza.lazypizzaapp.app.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appConfig: KoinAppDeclaration? = null) {
    val modules = listOf(appModule)

    startKoin {
        appConfig?.invoke(this)

        modules(modules)
    }
}