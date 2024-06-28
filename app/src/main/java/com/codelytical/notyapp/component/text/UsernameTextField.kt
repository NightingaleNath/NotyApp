package com.codelytical.notyapp.component.text

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codelytical.notyapp.R

@Composable
fun UsernameTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    NotyTextField(
        value = value,
        label = "Username",
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = { Icon(Icons.Outlined.Person, "User") },
        isError = isError,
        helperText = stringResource(R.string.message_field_username_invalid)
    )
}
