package com.lazypizza.lazypizzaapp.navigation.nav_bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lazypizza.lazypizzaapp.navigation.components.NavigationRailItem
import com.lazypizza.lazypizzaapp.navigation.locals.LocalLazyPizzaNavItems
import org.jetbrains.compose.resources.painterResource

@Composable
fun RailNavBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val items = LocalLazyPizzaNavItems.current
    val outline = MaterialTheme.colorScheme.outline
    NavigationRail(
        modifier = modifier
            .fillMaxHeight()
            .drawBehind {
                drawRect(
                    color = outline,
                    topLeft = Offset(x = size.width - 100f, y = 0f),
                    size = Size(width = 100f, height = size.height)
                )
            },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Spacer(Modifier.weight(1f))
        items.forEach { item ->
            NavigationRailItem(
                onClick = {
                    navHostController.navigate(item.screen) {
                        popUpTo(0)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = "${item.title} icon",
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(
                                color = if (item.selected) {
                                    MaterialTheme.colorScheme.primary.copy(alpha = .08f)
                                } else Color.Transparent
                            )
                            .padding(6.dp),
                        tint = if (item.selected) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.displaySmall,
                        color = if (item.selected) {
                            MaterialTheme.colorScheme.onSurface
                        } else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                badge = {
                    if (item.badge != null) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.badge,
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.weight(1f))
    }
}