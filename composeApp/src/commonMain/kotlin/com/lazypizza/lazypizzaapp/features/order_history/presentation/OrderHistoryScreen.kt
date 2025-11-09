package com.lazypizza.lazypizzaapp.features.order_history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OrderHistoryScreen(state: OrderState, modifier: Modifier) {

    LazyColumn(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        items(state.orders, key = { order -> order.id }, itemContent = { order ->
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).padding(16.dp).background(Color.Green))

        })
    }
}