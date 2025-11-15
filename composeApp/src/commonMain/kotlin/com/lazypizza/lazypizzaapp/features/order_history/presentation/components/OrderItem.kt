package com.lazypizza.lazypizzaapp.features.order_history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.core.presentation.utils.getFormattedCurrentDateTime
import com.lazypizza.lazypizzaapp.core.presentation.utils.toDisplayColor
import com.lazypizza.lazypizzaapp.core.presentation.utils.toDisplayText
import com.lazypizza.lazypizzaapp.core.presentation.utils.toPrice
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.TextBlack
import com.lazypizza.lazypizzaapp.features.order_history.domain.Order
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.order_grid
import lazypizza.composeapp.generated.resources.total_amount
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderItem(orderItem: Order, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .dropShadow(
                shape = RoundedCornerShape(size = 12.dp),
                shadow = Shadow(
                    radius = 4.dp,
                    spread = 4.dp,
                    color = Color.Black,
                )
            )
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.order_grid) + orderItem.id.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1.4f)
                )


                Row(modifier = Modifier.weight(0.6f), horizontalArrangement = Arrangement.End){

                    Text(
                        text = orderItem.orderState.toDisplayText(),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.wrapContentSize().background(
                            orderItem.orderState.toDisplayColor(), RoundedCornerShape(16.dp)
                        ).padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }


            }

            Text(
                text = getFormattedCurrentDateTime(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start

            )
        }


        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End
        ) {


            Column(modifier = Modifier.weight(1.4f)) {

              orderItem.shoppingCartItems.forEach { order ->
                  Text(
                      text = "${order.quantity} x ${order.product.name}",
                      style = MaterialTheme.typography.labelSmall,
                      color = TextBlack
                  )

              }

            }

            Column(modifier = Modifier.weight(0.6f)) {
                Text(
                    text = stringResource(Res.string.total_amount),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End

                )

                Text(
                    text = "$${orderItem.getOverallPrice().toPrice()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }


        }


    }
}