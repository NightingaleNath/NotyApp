package com.codelytical.notyapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelytical.notyapp.R
import com.codelytical.notyapp.component.button.NotyFullWidthButton
import com.codelytical.notyapp.component.dialog.FailureDialog
import com.codelytical.notyapp.component.dialog.LoaderDialog
import com.codelytical.notyapp.component.text.PasswordTextField
import com.codelytical.notyapp.component.text.UsernameTextField
import com.codelytical.notyapp.ui.theme.typography
import com.codelytical.notyapp.utils.NotyPreview
import com.codelytical.notyapp.utils.collectState
import com.codelytical.notyapp.view.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToSignup: () -> Unit,
    onNavigateToNotes: () -> Unit
) {
    val state by viewModel.collectState()

    LoginContent(
        isLoading = state.isLoading,
        username = state.username,
        password = state.password,
        isValidUsername = state.isValidUsername ?: true,
        isValidPassword = state.isValidPassword ?: true,
        onUsernameChange = viewModel::setUsername,
        onPasswordChange = viewModel::setPassword,
        onLoginClick = viewModel::login,
        onSignupClick = onNavigateToSignup,
        onDialogDismiss = viewModel::clearError,
        error = state.error
    )

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onNavigateToNotes()
        }
    }
}

@Composable
fun LoginContent(
    isLoading: Boolean,
    username: String,
    isValidUsername: Boolean,
    password: String,
    isValidPassword: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit,
    onDialogDismiss: () -> Unit,
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
        TopGreeting()

        LoginForm(
            username = username,
            isValidUsername = isValidUsername,
            onUsernameChange = onUsernameChange,
            password = password,
            isValidPassword = isValidPassword,
            onPasswordChange = onPasswordChange,
            onLoginClick = onLoginClick
        )

        SignUpLink(Modifier.align(Alignment.CenterHorizontally), onSignupClick = onSignupClick)
    }
}

@Composable
private fun TopGreeting() {
    Column(Modifier.fillMaxWidth()) {
        Image(
            contentDescription = "App Logo",
            painter = painterResource(id = R.drawable.noty_app_logo),
            modifier = Modifier
                .padding(top = 60.dp)
                .requiredSize(92.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "Welcome\nback",
            style = typography.h4,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 30.dp)
        )
    }
}

@Composable
private fun LoginForm(
    username: String,
    isValidUsername: Boolean,
    password: String,
    isValidPassword: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    UsernameTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(MaterialTheme.colors.background),
        value = username,
        onValueChange = onUsernameChange,
        isError = !isValidUsername
    )

    PasswordTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(MaterialTheme.colors.background),
        value = password,
        onValueChange = onPasswordChange,
        isError = !isValidPassword
    )

    NotyFullWidthButton(
        text = "Login",
        onClick = onLoginClick,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
    )
}

@Composable
private fun SignUpLink(modifier: Modifier, onSignupClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            append("Don't have an account? Signup")
            addStyle(SpanStyle(color = MaterialTheme.colors.primary), 23, this.length)
            toAnnotatedString()
        },
        style = typography.subtitle1,
        modifier = modifier
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .clickable(onClick = onSignupClick)
    )
}

@Preview
@Composable
fun PreviewLoginContent() = NotyPreview {
    LoginContent(
        isLoading = false,
        username = "johndoe",
        onUsernameChange = {},
        password = "password",
        onPasswordChange = {},
        onLoginClick = {},
        onSignupClick = {},
        isValidPassword = false,
        isValidUsername = false,
        onDialogDismiss = {},
        error = null
    )
}
