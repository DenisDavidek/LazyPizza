package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.components.AppShapes
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.cd_main_pizza_background
import lazypizza.composeapp.generated.resources.pizza
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainProductCatalogScreen(onNavigateToProductDetail:() -> Unit){
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(horizontal = 16.dp, vertical = 8.dp).clickable{
        onNavigateToProductDetail()
    }){
        Image(painter = painterResource(Res.drawable.pizza),
            contentDescription = stringResource(Res.string.cd_main_pizza_background),
            modifier = Modifier.fillMaxWidth().height(200.dp).clip(shape = AppShapes.large)
        , contentScale = ContentScale.Crop)
    }
}