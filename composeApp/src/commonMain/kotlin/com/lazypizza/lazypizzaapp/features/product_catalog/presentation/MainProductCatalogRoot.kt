package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.design_systems.AppShapes
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.design_systems.OrangeSelected
import com.lazypizza.lazypizzaapp.design_systems.components.PizzaSearchBar
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.ProductCategory
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.components.ProductItem
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.cd_main_pizza_background
import lazypizza.composeapp.generated.resources.pizza
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MainProductCatalogRoot(
    onNavigateToProductDetails: () -> Unit,
    viewModel: MainProductCatalogViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainProductCatalogScreen(
        state = state,
        onAction = { action ->
            when (action) {
                MainProductCatalogAction.OnProductClick -> {
                    onNavigateToProductDetails()
                }

                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun MainProductCatalogScreen(
    state: MainProductCatalogState,
    onAction: (MainProductCatalogAction) -> Unit,
) {
    val adaptiveWindow = currentWindowAdaptiveInfo()
    val isExpanded = adaptiveWindow.windowSizeClass
        .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(Res.drawable.pizza),
            contentDescription = stringResource(Res.string.cd_main_pizza_background),
            modifier = Modifier.fillMaxWidth().height(200.dp).clip(shape = AppShapes.large),
            contentScale = ContentScale.Crop
        )

        PizzaSearchBar(
            modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
                .padding(vertical = 12.dp), onValueChange = { changedValue ->
                Logger.e("changedValue $changedValue")
            })

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 8.dp)
        ) {
            items(
                items = ProductCategory.entries,
                key = { productCategory -> productCategory.name },
                itemContent = { productCategory ->

                    val isSelected = state.selectedCategory == productCategory

                    val borderColor =
                        if (isSelected) OrangeSelected else MaterialTheme.colorScheme.outline

                    val labelColor =
                        if (isSelected) OrangeSelected else MaterialTheme.colorScheme.onSurface


                    SuggestionChip(
                        onClick = {
                            onAction(MainProductCatalogAction.OnCategorySelected(productCategory))
                        },
                        label = { Text(productCategory.displayName) },
                        border = BorderStroke(width = 1.dp, color = borderColor),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        shape = AppShapes.medium,
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = Color.Transparent,
                            // 6. Use the dynamic labelColor
                            labelColor = labelColor
                        )
                    )
                })
        }

        if (isExpanded) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                productCatalogGridContent(
                    products = state.products,
                    onAction = onAction
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                productCatalogListContent(
                    products = state.products,
                    onAction = onAction
                )
            }
        }

    }
}

fun LazyListScope.productCatalogListContent(
    products: List<Product>,
    onAction: (MainProductCatalogAction) -> Unit,
) {
    products.groupBy { it.category }.entries.forEach { (category, products) ->
        item {
            if (products.isNotEmpty()) {
                Text(
                    text = category.displayName.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(
            items = products,
            key = { it.id }
        ) { product ->
            ProductItem(
                product = product,
                onClick = {
                    onAction(MainProductCatalogAction.OnProductClick)
                }
            )
        }
    }
}

// For LazyHorizontalGrid
fun LazyGridScope.productCatalogGridContent(
    products: List<Product>,
    onAction: (MainProductCatalogAction) -> Unit,
) {
    products.groupBy { it.category }.entries.forEach { (category, products) ->
        item(span = { GridItemSpan(maxLineSpan) }) {
            if (products.isNotEmpty()) {
                Text(
                    text = category.displayName.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(
            items = products,
            key = { it.id }
        ) { product ->
            ProductItem(
                product = product,
                onClick = {
                    onAction(MainProductCatalogAction.OnProductClick)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        MainProductCatalogScreen(
            state = MainProductCatalogState(),
            onAction = {}
        )
    }
}