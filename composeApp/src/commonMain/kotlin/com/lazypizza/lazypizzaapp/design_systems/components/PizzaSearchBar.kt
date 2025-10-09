package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.cd_search_for_food
import lazypizza.composeapp.generated.resources.search_for_delicious_food
import lazypizza.composeapp.generated.resources.search_refraction
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PizzaSearchBar(modifier: Modifier, onValueChange: (String) -> Unit) {

    var enteredText by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


    TextField(value = enteredText, onValueChange = { changeValue ->
        enteredText = changeValue
        onValueChange(enteredText)
    }, modifier = modifier, singleLine = true ,placeholder = {
        Text(stringResource(Res.string.search_for_delicious_food), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }, leadingIcon = {
        Image(painter = painterResource(Res.drawable.search_refraction), contentDescription = stringResource(
            Res.string.cd_search_for_food), modifier = Modifier.size(24.dp))
    }, shape = AppShapes.large,
        colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surface, unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
        }))
}