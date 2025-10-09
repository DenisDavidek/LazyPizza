package com.lazypizza.lazypizzaapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.core.presentation.MainProductCatalogTopBar
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.navigation.AppNavigation
import com.lazypizza.lazypizzaapp.navigation.LazyPizzaScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    val navHostController = rememberNavController()
    val backStackEntry = navHostController.currentBackStackEntryAsState()

    val isMainProductCatalogScreenVisible = backStackEntry.value?.destination?.route == LazyPizzaScreen.MainProductCatalog::class.qualifiedName

    LaunchedEffect(isMainProductCatalogScreenVisible){
        Logger.e("isMainProductCatalogScreenVisible $isMainProductCatalogScreenVisible")
    }


    AppTheme {

        Scaffold(topBar = {
            if (isMainProductCatalogScreenVisible)
            MainProductCatalogTopBar(modifier = Modifier.fillMaxWidth().wrapContentHeight())

        }) { padding ->
            AppNavigation(navHostController, modifier = Modifier.padding(padding))
        }

    }

}