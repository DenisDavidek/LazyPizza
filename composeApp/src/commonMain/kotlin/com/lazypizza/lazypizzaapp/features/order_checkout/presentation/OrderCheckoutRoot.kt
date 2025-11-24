package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.core.utils.toPrice
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.components.AppDatePicker
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.components.AppTextButton
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.components.AppTimePicker
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.components.GradientButton
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.components.RadioGroup
import com.lazypizza.lazypizzaapp.features.cart.presentation.components.AddonItem
import com.lazypizza.lazypizzaapp.features.cart.presentation.components.CartItem
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model.BottomBarOrientation
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model.PickupTime
import com.lazypizza.lazypizzaapp.features.order_history.presentation.OrderAction
import com.lazypizza.lazypizzaapp.features.order_history.presentation.OrderViewModel
import com.lazypizza.lazypizzaapp.features.order_history.presentation.utils.generateOrderNumber
import com.lazypizza.lazypizzaapp.rememberUser
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OrderCheckoutRoot(
    onNavigateBack: () -> Unit,
    onNavigateToMain: () -> Unit,
    viewModel: OrderCheckoutViewModel = koinViewModel(),
    orderViewModel: OrderViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OrderCheckoutScreen(
        state = state,
        onAction = { action ->
            when (action) {
                OrderCheckoutAction.OnBackClick -> {
                    onNavigateBack()
                }

                OrderCheckoutAction.OnBackToMenuClick -> {
                    onNavigateToMain()
                }

                else -> {
                    viewModel.onAction(action)
                }
            }
        },
        onOrderAction = { action ->
            orderViewModel.onAction(action)
        }
    )

    if (state.isPickDateDialogVisible) {
        AppDatePicker(
            value = null,
            onConfirm = { dateMillis ->
                viewModel.onAction(OrderCheckoutAction.OnDatePickerDateSelected(dateMillis ?: 0L))
            },
            onDismiss = {
                viewModel.onAction(OrderCheckoutAction.OnDatePickerClose)
            }
        )

    }

    if (state.isPickTimeDialogVisible) {
        AppTimePicker(
            value = state.datePickerSelectedDateMillis,
            onConfirm = { timeMillis ->
                Logger.d("Order checkout") { timeMillis.toString() }
                viewModel.onAction(OrderCheckoutAction.OnTimePickerDateSelected(timeMillis))
            },
            onDismiss = {
                viewModel.onAction(OrderCheckoutAction.OnTimePickerClose)
            },
            error = state.pickTimeError
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCheckoutScreen(
    state: OrderCheckoutState,
    onAction: (OrderCheckoutAction) -> Unit,
    onOrderAction: (OrderAction) -> Unit
) {
    val windowSize = LocalWindowInfo.current
    val density = LocalDensity.current
    val widthDp = with(density) { windowSize.containerSize.width.toDp() }

    val currentUser = rememberUser()
    var nextOrderId by remember {
        mutableIntStateOf(-1)
    }
    LaunchedEffect(state.isConfirmingOrder){
        if (state.isConfirmingOrder)
            onOrderAction(OrderAction.OnCreateOrder(orderCheckoutState = state, currentUser = currentUser, nextOrderId = nextOrderId))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Order Checkout",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onAction(OrderCheckoutAction.OnBackClick)
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
            if (!state.isConfirmingOrder) {
                val bottomNavOrientation = when {
                    widthDp < 600.dp -> BottomBarOrientation.Vertical
                    widthDp < 840.dp -> BottomBarOrientation.Horizontal
                    else -> BottomBarOrientation.Horizontal
                }

                when (bottomNavOrientation) {
                    BottomBarOrientation.Horizontal -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
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

                            GradientButton(
                                onClick = {
                                    nextOrderId = generateOrderNumber()
                                    onAction(OrderCheckoutAction.OnPlaceOrderClick(
                                        nextOrderId
                                    ))
                                },
                                colors = listOf(
                                    Color(0xffF9966F),
                                    Color(0xffF36B50),
                                ),
                                buttonText = "Place Order",
                                shadowColor = MaterialTheme.colorScheme.primary.copy(.25f),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    BottomBarOrientation.Vertical -> {
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
                                    nextOrderId = generateOrderNumber()
                                    onAction(OrderCheckoutAction.OnPlaceOrderClick(generateOrderNumber()))
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
                    }
                }
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        if (state.isConfirmingOrder) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your order has been placed!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Thank you for your order! Please come at\n" +
                            "the indicated time.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ORDER NUMBER:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = state.orderId,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PICKUP TIME:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = state.earliestPickupTime,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                AppTextButton(
                    text = "Back to Menu",
                    onClick = {
                        onAction(OrderCheckoutAction.OnBackToMenuClick)
                    }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                item {
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
                                imageVector = if (state.isOrderDetailsExpanded) {
                                    Icons.Default.KeyboardArrowUp
                                } else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (state.isOrderDetailsExpanded) {
                                    "Collapse order details"
                                } else "Expand order details",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                }

                if (state.isOrderDetailsExpanded) {
                    item {
                        val gridCells = when {
                            widthDp < 600.dp -> 1
                            widthDp < 840.dp -> 2
                            else -> 2
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(gridCells),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 2000.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                items = state.products,
                                key = { it.product.id }
                            ) { cartItem ->
                                CartItem(
                                    modifier = Modifier.animateItem(),
                                    shoppingCartItem = cartItem,
                                    onCartItemDeleteClick = { shoppingCartItem ->
                                        onAction(
                                            OrderCheckoutAction.OnDeleteProductFromCart(
                                                shoppingCartItem
                                            )
                                        )
                                    },
                                    onIncrement = { shoppingCartItem ->
                                        onAction(
                                            OrderCheckoutAction.OnIncreaseQuantity(
                                                shoppingCartItem
                                            )
                                        )
                                    },
                                    onDecrement = { shoppingCartItem ->
                                        onAction(
                                            OrderCheckoutAction.OnDecreaseQuantity(
                                                shoppingCartItem
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                item {
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
                    ) {
                        items(
                            items = state.addOns,
                            key = { it.id }
                        ) { addOn ->
                            AddonItem(
                                product = addOn,
                                onProductAddClick = { product ->
                                    onAction(OrderCheckoutAction.OnAddOnPlusClick(product))
                                },
                                modifier = Modifier
                                    .width(160.dp)
                                    .dropShadow(
                                        RoundedCornerShape(12.dp),
                                        Shadow(
                                            radius = 12.dp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(.06f),
                                            spread = 0.dp,
                                            offset = DpOffset(x = 0.dp, y = 4.dp)
                                        )
                                    )
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

                    Spacer(Modifier.height(12.dp))

                    TextField(
                        value = state.comment,
                        onValueChange = { value ->
                            onAction(OrderCheckoutAction.OnCommentChange(value))
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
            onAction = { _ ->

            },
            onOrderAction = { _ ->
            }
        )
    }
}
