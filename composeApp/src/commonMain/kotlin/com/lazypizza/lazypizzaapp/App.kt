package com.lazypizza.lazypizzaapp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.lazypizza.lazypizzaapp.core.presentation.MainProductCatalogTopBar
import com.lazypizza.lazypizzaapp.core.presentation.TitleTopBar
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.navigation.AppNavigation
import com.lazypizza.lazypizzaapp.navigation.LazyPizzaScreen
import com.lazypizza.lazypizzaapp.navigation.locals.LocalLazyPizzaNavItems
import com.lazypizza.lazypizzaapp.navigation.model.NavItem
import com.lazypizza.lazypizzaapp.navigation.nav_bars.BottomNavBar
import com.lazypizza.lazypizzaapp.navigation.nav_bars.RailNavBar
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.ic_cart
import lazypizza.composeapp.generated.resources.ic_history
import lazypizza.composeapp.generated.resources.ic_menu
import lazypizza.composeapp.generated.resources.order_history
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var navItems = listOf(
        NavItem(
            title = "Menu",
            icon = Res.drawable.ic_menu,
            screen = LazyPizzaScreen.MainProductCatalog,
            selected = true
        ),
        NavItem(
            title = "Cart",
            icon = Res.drawable.ic_cart,
            screen = LazyPizzaScreen.MainProductCatalog,
            badge = "4",
            selected = false
        ),
        NavItem(
            title = "History",
            icon = Res.drawable.ic_history,
            screen = LazyPizzaScreen.OrderHistory,
            selected = false
        ),
    )
    val navBarAllowedScreens = listOf(
        LazyPizzaScreen.MainProductCatalog,
                LazyPizzaScreen.OrderHistory
    )

    val navHostController = rememberNavController()
    val backStackEntry = navHostController.currentBackStackEntryAsState()

    val isMainProductCatalogScreenVisible =
        backStackEntry.value?.destination?.route == LazyPizzaScreen.MainProductCatalog::class.qualifiedName

    val shouldDisplayTitleTopBar =
        backStackEntry.value?.destination?.route == LazyPizzaScreen.OrderHistory::class.qualifiedName


    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    LaunchedEffect(backStackEntry) {
        val activeScreenIdx =
            navItems.indexOfFirst { it.screen::class.qualifiedName == backStackEntry.value?.destination?.route }

        navItems = navItems.mapIndexed { index, item ->
            if (activeScreenIdx == index) {
                item.copy(selected = true)
            } else item
        }
    }

    val adaptiveWindow = currentWindowAdaptiveInfo()
    val isExpanded = adaptiveWindow.windowSizeClass
        .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

    val isNavBarAllowed =
        navBarAllowedScreens.any { it::class.qualifiedName == backStackEntry.value?.destination?.route }

    AppTheme {
        CompositionLocalProvider(
            LocalLazyPizzaNavItems provides navItems
        ) {
            Scaffold(
                topBar = {
                    if (isMainProductCatalogScreenVisible) {
                        MainProductCatalogTopBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        )
                    } else if (shouldDisplayTitleTopBar) {
                        TitleTopBar(screenTitle = stringResource(Res.string.order_history))
                    }
                },
                bottomBar = {
                    if (!isExpanded && isNavBarAllowed) {
                        BottomNavBar(
                            navHostController = navHostController
                        )
                    }
                }
            ) { padding ->
                var padding = padding

                if (isExpanded && isNavBarAllowed) {
                    val railWidth = 78.dp

                    padding = PaddingValues(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding(),
                        start = padding.calculateStartPadding(LayoutDirection.Ltr) + railWidth,
                        end = padding.calculateEndPadding(LayoutDirection.Ltr),
                    )

                    RailNavBar(
                        modifier = Modifier.width(railWidth),
                        navHostController = navHostController
                    )
                }

                AppNavigation(
                    navHostController = navHostController,
                    modifier = Modifier.padding(padding)
                )
            }
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