package com.codelytical.notyapp.component.text

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.codelytical.notyapp.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String = "Password",
    value: String = "",
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    NotyTextField(
        value = value,
        label = label,
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = { Icon(Icons.Outlined.Password, label) },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = isError,
        helperText = stringResource(R.string.message_field_password_invalid),
        trailingIcon = {
            val image = if (isPasswordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (isPasswordVisible) "Hide password" else "Show password"

            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = image,
                    contentDescription = description,
                    modifier = Modifier.testTag("TogglePasswordVisibility")
                )
            }
        }
    )
}
