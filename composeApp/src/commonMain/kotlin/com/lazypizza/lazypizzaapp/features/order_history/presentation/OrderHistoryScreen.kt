package com.lazypizza.lazypizzaapp.features.order_history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.features.order_history.presentation.components.OrderItem

@Composable
fun OrderHistoryScreen(
    state: OrderState,
    isExpanded: Boolean,
    isPortrait: Boolean,
    modifier: Modifier
) {

    if (isExpanded && !isPortrait) {

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp
        ){

            items(state.orders, key = { order -> order.id}, itemContent = { order ->
                OrderItem(orderItem = order, modifier = Modifier.fillMaxWidth().wrapContentHeight())

            })
        }


    } else {

        LazyColumn(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
            items(state.orders, key = { order -> order.id }, itemContent = { order ->
                OrderItem(orderItem = order, modifier = Modifier.fillMaxWidth().wrapContentHeight())

            })
        }
    }
}