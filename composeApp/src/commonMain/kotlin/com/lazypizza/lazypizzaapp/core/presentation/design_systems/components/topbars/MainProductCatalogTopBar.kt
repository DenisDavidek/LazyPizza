package com.lazypizza.lazypizzaapp.core.presentation.design_systems.components.topbars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.core.presentation.locals.LocalUser
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.cd_contact_phone_number
import lazypizza.composeapp.generated.resources.cd_lazy_pizza_logo
import lazypizza.composeapp.generated.resources.example_phone_number
import lazypizza.composeapp.generated.resources.ic_app
import lazypizza.composeapp.generated.resources.lazy_pizza
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainProductCatalogTopBar(
    onLogoutClick: () -> Unit,
    onAuthenticateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = LocalUser.current
    val isUserLoggedIn = user != null

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(Res.drawable.ic_app),
                    contentDescription = stringResource(Res.string.cd_lazy_pizza_logo),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(Res.string.lazy_pizza),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = stringResource(Res.string.cd_contact_phone_number)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(Res.string.example_phone_number),
                    style = MaterialTheme.typography.bodyMedium
                )

                IconButton(
                    onClick = {
                        if (isUserLoggedIn) {
                            onLogoutClick()
                        } else {
                            onAuthenticateClick()
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = if (isUserLoggedIn) {
                            MaterialTheme.colorScheme.primary.copy(alpha = .08f)
                        } else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .08f),
                        contentColor = if (isUserLoggedIn) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                ) {
                    Icon(
                        imageVector = if (isUserLoggedIn) {
                            Icons.AutoMirrored.Filled.Logout
                        } else Icons.Default.Person,
                        contentDescription = if (isUserLoggedIn) {
                            "Logout"
                        } else "Authenticate"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background, // Set background to match screen
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
    )
}