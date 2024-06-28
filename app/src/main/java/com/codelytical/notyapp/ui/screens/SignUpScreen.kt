package com.codelytical.notyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelytical.notyapp.component.dialog.FailureDialog
import com.codelytical.notyapp.component.dialog.LoaderDialog
import com.codelytical.notyapp.component.text.PasswordTextField
import com.codelytical.notyapp.component.text.UsernameTextField
import com.codelytical.notyapp.ui.theme.typography
import com.codelytical.notyapp.utils.NotyPreview
import com.codelytical.notyapp.utils.collectState
import com.codelytical.notyapp.view.viewmodel.RegisterViewModel

@Composable
fun SignUpScreen(
    viewModel: RegisterViewModel,
    onNavigateUp: () -> Unit,
    onNavigateToNotes: () -> Unit
) {
    val state by viewModel.collectState()

    SignUpContent(
        isLoading = state.isLoading,
        username = state.username,
        password = state.password,
        confirmPassword = state.confirmPassword,
        isValidUsername = state.isValidUsername ?: true,
        isValidPassword = state.isValidPassword ?: true,
        isValidConfirmPassword = state.isValidConfirmPassword ?: true,
        onUsernameChange = viewModel::setUsername,
        onPasswordChange = viewModel::setPassword,
        onConfirmPasswordChanged = viewModel::setConfirmPassword,
        onSignUpClick = viewModel::register,
        onDialogDismiss = viewModel::clearError,
        onNavigateUp = onNavigateUp,
        error = state.error
    )

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onNavigateToNotes()
        }
    }
}

@Composable
fun SignUpContent(
    isLoading: Boolean,
    username: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    password: String,
    confirmPassword: String,
    onConfirmPasswordChanged: (String) -> Unit,
    isValidConfirmPassword: Boolean,
    onNavigateUp: () -> Unit,
    onSignUpClick: () -> Unit,
    onDialogDismiss: () -> Unit,
    isValidUsername: Boolean,
    isValidPassword: Boolean,
    error: String?
) {
    if (isLoading) {
        LoaderDialog()
    }

    if (error != null) {
        FailureDialog(error, onDialogDismiss = onDialogDismiss)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Create\naccount",
            style = typography.h4,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 16.dp)
        )

        SignUpForm(
            username = username,
            onUsernameChange = onUsernameChange,
            isValidUsername = isValidUsername,
            password = password,
            onPasswordChange = onPasswordChange,
            isValidPassword = isValidPassword,
            confirmPassword = confirmPassword,
            onConfirmPasswordChanged = onConfirmPasswordChanged,
            isValidConfirmPassword = isValidConfirmPassword,
            onSignUpClick = onSignUpClick
        )

        LoginLink(Modifier.align(Alignment.CenterHorizontally), onLoginClick = onNavigateUp)
    }
}

@Composable
private fun SignUpForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    isValidUsername: Boolean,
    password: String,
    onPasswordChange: (String) -> Unit,
    isValidPassword: Boolean,
    confirmPassword: String,
    onConfirmPasswordChanged: (String) -> Unit,
    isValidConfirmPassword: Boolean,
    onSignUpClick: () -> Unit
) {
    Column(
        Modifier.padding(
            start = 16.dp,
            top = 32.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        UsernameTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colors.background),
            value = username,
            onValueChange = onUsernameChange,
            isError = !isValidUsername
        )

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colors.background),
            value = password,
            onValueChange = onPasswordChange,
            isError = !isValidPassword
        )

        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colors.background),
            value = confirmPassword,
            label = "Confirm Password",
            onValueChange = onConfirmPasswordChanged,
            isError = !isValidConfirmPassword
        )

        Button(
            onClick = onSignUpClick,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(style = typography.subtitle1, color = Color.White, text = "Create account")
        }
    }
}

@Composable
private fun LoginLink(modifier: Modifier, onLoginClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            append("Already have an account? Login")
            addStyle(SpanStyle(color = MaterialTheme.colors.primary), 24, this.length)
        },
        style = typography.subtitle1,
        modifier = modifier
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .clickable(onClick = onLoginClick)
    )
}

@Preview
@Composable
fun PreviewSignupContent() = NotyPreview {
    SignUpContent(
        isLoading = false,
        username = "johndoe",
        onUsernameChange = {},
        onPasswordChange = {},
        password = "password",
        confirmPassword = "password",
        onConfirmPasswordChanged = {},
        isValidConfirmPassword = false,
        onNavigateUp = {},
        onSignUpClick = {},
        onDialogDismiss = {},
        isValidUsername = false,
        isValidPassword = false,
        error = null
    )
}
