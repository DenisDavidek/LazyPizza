package com.lazypizza.lazypizzaapp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import co.touchlab.kermit.Logger
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.lazypizza.lazypizzaapp.core.presentation.components.LogoutDialog
import com.lazypizza.lazypizzaapp.core.presentation.components.MainProductCatalogTopBar
import com.lazypizza.lazypizzaapp.core.presentation.components.TitleTopBar
import com.lazypizza.lazypizzaapp.core.presentation.locals.LocalLazyPizzaNavItems
import com.lazypizza.lazypizzaapp.core.presentation.locals.LocalUser
import com.lazypizza.lazypizzaapp.core.utils.showSnackBar
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.features.cart.presentation.CartAction
import com.lazypizza.lazypizzaapp.features.cart.presentation.CartViewModel
import com.lazypizza.lazypizzaapp.features.order_history.presentation.OrderViewModel
import com.lazypizza.lazypizzaapp.navigation.AppNavigation
import com.lazypizza.lazypizzaapp.navigation.LazyPizzaScreen
import com.lazypizza.lazypizzaapp.navigation.model.NavItem
import com.lazypizza.lazypizzaapp.navigation.nav_bars.BottomNavBar
import com.lazypizza.lazypizzaapp.navigation.nav_bars.RailNavBar
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.cart
import lazypizza.composeapp.generated.resources.ic_cart
import lazypizza.composeapp.generated.resources.ic_history
import lazypizza.composeapp.generated.resources.ic_menu
import lazypizza.composeapp.generated.resources.order_history
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {

    val cartViewModel: CartViewModel = koinViewModel()
    val cartState by cartViewModel.cartState.collectAsStateWithLifecycle()
    val currentUser = rememberUser()
    var isLogoutVisible by rememberSaveable { mutableStateOf(false) }

    val navItems = remember {
        mutableStateListOf(
            NavItem(
                title = "Menu",
                icon = Res.drawable.ic_menu,
                screen = LazyPizzaScreen.MainProductCatalog,
                selected = true
            ),
            NavItem(
                title = "Cart",
                icon = Res.drawable.ic_cart,
                screen = LazyPizzaScreen.Cart,
                badge = null,
                selected = false
            ),
            NavItem(
                title = "History",
                icon = Res.drawable.ic_history,
                screen = LazyPizzaScreen.OrderHistory,
                selected = false
            ),
        )
    }

    LaunchedEffect(cartState.items.sumOf { it.quantity }) {
        val cartItemIndex = navItems.indexOfFirst { it.screen == LazyPizzaScreen.Cart }
        if (cartItemIndex != -1) {
            navItems[cartItemIndex] = navItems[cartItemIndex].copy(badge = cartViewModel.countNewBadgeValue())
        }
    }

    val navBarAllowedScreens = listOf(
        LazyPizzaScreen.MainProductCatalog,
        LazyPizzaScreen.OrderHistory,
        LazyPizzaScreen.Cart
    )

    val navHostController = rememberNavController()
    val backStackEntry = navHostController.currentBackStackEntryAsState()

    val isMainProductCatalogScreenVisible =
        backStackEntry.value?.destination?.route == LazyPizzaScreen.MainProductCatalog::class.qualifiedName

    val shouldDisplayTitleTopBar =
        backStackEntry.value?.destination?.route == LazyPizzaScreen.OrderHistory::class.qualifiedName || backStackEntry.value?.destination?.route == LazyPizzaScreen.Cart::class.qualifiedName


    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    LaunchedEffect(backStackEntry.value?.destination) {
        Logger.d { "Triggered" }

        val activeScreenIdx =
            navItems.indexOfLast { it.screen::class.qualifiedName == backStackEntry.value?.destination?.route }

        if (activeScreenIdx != -1) {
            navItems.forEachIndexed { index, item ->
                navItems[index] = item.copy(selected = false)
            }

            navItems[activeScreenIdx] = navItems[activeScreenIdx].copy(selected = true)
        }
    }


    val adaptiveWindow = currentWindowAdaptiveInfo()
    val isExpanded = adaptiveWindow.windowSizeClass
        .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

    val isNavBarAllowed =
        navBarAllowedScreens.any { it::class.qualifiedName == backStackEntry.value?.destination?.route }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val orderViewModel: OrderViewModel = koinViewModel()

    AppTheme {
        CompositionLocalProvider(
            LocalLazyPizzaNavItems provides navItems
        ) {
            CompositionLocalProvider(
                LocalUser provides currentUser
            ) {
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                    topBar = {
                        if (isMainProductCatalogScreenVisible) {
                            MainProductCatalogTopBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                onAuthenticateClick = {
                                    navHostController.navigate(LazyPizzaScreen.Authentication)
                                },
                                onLogoutClick = {
                                    isLogoutVisible = true
                                }
                            )
                        } else if (shouldDisplayTitleTopBar) {
                            TitleTopBar(screenTitle = getToolbarTitle(backStackEntry.value?.destination?.route.toString()))
                        }
                    },
                    bottomBar = {
                        if (!isExpanded && isNavBarAllowed) {
                            BottomNavBar(
                                navHostController = navHostController
                            )
                        }
                    }
                ) { innerPadding ->
                    val padding = if (isExpanded && isNavBarAllowed) {
                        val railWidth = 78.dp
                        RailNavBar(
                            modifier = Modifier.width(railWidth),
                            navHostController = navHostController
                        )

                        PaddingValues(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding(),
                            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + railWidth,
                            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                        )
                    } else innerPadding

                    AppNavigation(
                        navHostController = navHostController,
                        modifier = Modifier.padding(padding),
                        cartViewModel = cartViewModel,
                        orderViewModel = orderViewModel,
                        onShowSnackBar = { product ->
                            scope.showSnackBar(
                                snackBarHostState = snackBarHostState,
                                message = "The ${product.name} has been successfully added into your cart"
                            )
                        }
                    )
                }

                if (isLogoutVisible) {
                    LogoutDialog(
                        onLogoutClick = {
                            logoutUser()
                            cartViewModel.onAction(CartAction.OnClearCart())

                            isLogoutVisible = false
                        },
                        onDismissRequest = {
                            isLogoutVisible = false
                        }
                    )
                }
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

@Composable
fun getToolbarTitle(currentDestination: String): String {
    return if (currentDestination == LazyPizzaScreen.OrderHistory::class.qualifiedName) {
        stringResource(Res.string.order_history)
    } else
        stringResource(Res.string.cart)


}