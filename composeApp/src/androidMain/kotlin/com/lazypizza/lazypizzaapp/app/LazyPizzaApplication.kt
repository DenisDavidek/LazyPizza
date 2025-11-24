package com.lazypizza.lazypizzaapp.app

import android.app.Application
import com.lazypizza.lazypizzaapp.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class LazyPizzaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@LazyPizzaApplication)
        }
    }
}