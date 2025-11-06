package com.lazypizza.lazypizzaapp.app.di

import com.lazypizza.lazypizzaapp.features.authentication.presentation.AuthenticationViewModel
import com.lazypizza.lazypizzaapp.features.cart.presentation.CartViewModel
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogViewModel
import com.lazypizza.lazypizzaapp.getPhoneAuthService
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainProductCatalogViewModel)
    viewModelOf(::CartViewModel)
    viewModel { AuthenticationViewModel(getPhoneAuthService()) }
}