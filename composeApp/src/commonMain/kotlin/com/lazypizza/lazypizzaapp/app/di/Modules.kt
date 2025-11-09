package com.lazypizza.lazypizzaapp.app.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.lazypizza.lazypizzaapp.core.data.db.AppDatabase
import com.lazypizza.lazypizzaapp.core.data.db.DatabaseFactory
import com.lazypizza.lazypizzaapp.features.authentication.presentation.AuthenticationViewModel
import com.lazypizza.lazypizzaapp.features.cart.data.DefaultCartRepository
import com.lazypizza.lazypizzaapp.features.cart.domain.CartRepository
import com.lazypizza.lazypizzaapp.features.cart.presentation.CartViewModel
import com.lazypizza.lazypizzaapp.features.order_history.presentation.OrderViewModel
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogViewModel
import com.lazypizza.lazypizzaapp.getPhoneAuthService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainProductCatalogViewModel)
    viewModelOf(::CartViewModel)
    viewModel { AuthenticationViewModel(getPhoneAuthService()) }
    viewModelOf(::OrderViewModel)
}

expect val platformModule: Module

val sharedModule = module {
    single {
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).fallbackToDestructiveMigration(true).build()
    }
    single {
        get<AppDatabase>().cartDao
    }
    singleOf(::DefaultCartRepository).bind<CartRepository>()
}