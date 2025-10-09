package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.design_systems.AppShapes
import com.lazypizza.lazypizzaapp.design_systems.OrangeSelected
import com.lazypizza.lazypizzaapp.design_systems.components.PizzaSearchBar
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.ProductCategory
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.cd_main_pizza_background
import lazypizza.composeapp.generated.resources.pizza
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainProductCatalogScreen(onNavigateToProductDetail: () -> Unit) {

    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp).clickable {
                onNavigateToProductDetail()
            }, horizontalAlignment = Alignment.CenterHorizontally
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

        LazyRow(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(bottom = 8.dp)) {
            items(
                items = ProductCategory.entries,
                key = { productCategory -> productCategory.name },
                itemContent = { productCategory ->

                    val isSelected = selectedCategory == productCategory
                    val borderColor =
                        if (isSelected) OrangeSelected else MaterialTheme.colorScheme.outline
                    val labelColor =
                        if (isSelected) OrangeSelected else MaterialTheme.colorScheme.onSurface


                    SuggestionChip(
                        onClick = {

                            if (selectedCategory != productCategory)
                            selectedCategory = productCategory
                            else
                                selectedCategory = null
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

        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.Yellow)) {

        }

    }
}