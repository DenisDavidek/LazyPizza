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
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
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

    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
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

fun getAsyncImageLoader(context: PlatformContext): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            add(KtorNetworkFetcherFactory())
        }
        .build()
}