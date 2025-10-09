package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.design_systems.components.AppShapes
import com.lazypizza.lazypizzaapp.design_systems.components.PizzaSearchBar
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.cd_main_pizza_background
import lazypizza.composeapp.generated.resources.pizza
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainProductCatalogScreen(onNavigateToProductDetail:() -> Unit){
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(horizontal = 16.dp, vertical = 8.dp).clickable{
        onNavigateToProductDetail()
    }, horizontalAlignment = Alignment.CenterHorizontally){

        Image(painter = painterResource(Res.drawable.pizza),
            contentDescription = stringResource(Res.string.cd_main_pizza_background),
            modifier = Modifier.fillMaxWidth().height(200.dp).clip(shape = AppShapes.large)
        , contentScale = ContentScale.Crop)

        PizzaSearchBar(modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize().padding(vertical = 16.dp), onValueChange = { changedValue ->
            Logger.e("changedValue $changedValue")
        })

        LazyRow(modifier = Modifier.fillMaxWidth().height(100.dp).padding(bottom = 16.dp).background(Color.Magenta)){

        }

        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.Yellow)){

        }

    }
}