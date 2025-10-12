package com.lazypizza.lazypizzaapp.pizza_product.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.pizza_product.presentation.models.ToppingsUI
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.storage.storage
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.hawaiian
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToppingsCard(
    modifier: Modifier = Modifier,
    toppingsUI: ToppingsUI,
    increaseClick: () -> Unit,
    decreaseClick: () -> Unit
) {
    Card(
        modifier = modifier.size(height = 142.dp, width = 124.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        val image by remember {
            mutableStateOf("")
        }
        val storage = Firebase.storage
        val imageRef = storage.getReferenceFromUrl("gs://lazypizza-1999a.firebasestorage.app/drink/7-up.png")

        LaunchedEffect(imageRef) {
            val image = imageRef.getDownloadUrl()
        }

        SubcomposeAsyncImage(
            model = image,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentDescription = toppingsUI.name,
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier.size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            },
            error = {
                // Fallback to the resource image if network loading fails

            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium,
            text = toppingsUI.name
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            text = toppingsUI.price
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
fun ToppingsCardPreview() {
    AppTheme {
        ToppingsCard(
            toppingsUI = ToppingsUI(
                image = painterResource(Res.drawable.hawaiian),
                name = "Hawaiian",
                price = "$10.99"
            ),
            increaseClick = {},
            decreaseClick = {}
        )
    }
}