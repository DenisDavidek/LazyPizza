package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lazypizza.lazypizzaapp.core.utils.toPrice
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.design_systems.components.GradientButton
import com.lazypizza.lazypizzaapp.design_systems.components.RadioGroup
import com.lazypizza.lazypizzaapp.features.cart.presentation.components.AddonItem
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model.PickupTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrderCheckoutRoot(
    viewModel: OrderCheckoutViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OrderCheckoutScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCheckoutScreen(
    state: OrderCheckoutState,
    onAction: (OrderCheckoutAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Order history",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {

                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(.08f),
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back",
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ORDER TOTAL:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "$${state.orderTotal.toPrice()}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(Modifier.height(12.dp))

                GradientButton(
                    onClick = {

                    },
                    colors = listOf(
                        Color(0xffF9966F),
                        Color(0xffF36B50),
                    ),
                    buttonText = "Place Order",
                    shadowColor = MaterialTheme.colorScheme.primary.copy(.25f),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "PICKUP TIME",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(12.dp))

            RadioGroup(
                options = PickupTime.entries.map { it.title },
                selectedOptionIndex = state.selectedOption.index,
                onOptionSelected = { index ->
                    onAction(OrderCheckoutAction.OnPickupTimeSelected(PickupTime.entries[index]))
                }
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "EARLIEST PICKUP TIME:".uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = state.earliestPickupTime,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(Modifier.height(16.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ORDER DETAILS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedIconButton(
                    onClick = {
                        onAction(OrderCheckoutAction.OnProductDetailsToggle)
                    },
                    border = BorderStroke(
                        width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (state.orderDetailsExpanded) {
                            Icons.Default.KeyboardArrowUp
                        } else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (state.orderDetailsExpanded) {
                            "Collapse order details"
                        } else "Expand order details",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "RECOMMENDED ADD-ONS",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.addOns) { addOn ->
                    AddonItem(
                        product = addOn,
                        onProductAddClick = {

                        },
                        modifier = Modifier
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "COMMENTS",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.heightIn(min = 12.dp))

            TextField(
                value = state.comment,
                onValueChange = { value ->

                },
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 92.dp),
                placeholder = {
                    Text(
                        text = "Add Comment",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )

        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        OrderCheckoutScreen(
            state = OrderCheckoutState(
                earliestPickupTime = "12:15"
            ),
            onAction = {}
        )
    }
}