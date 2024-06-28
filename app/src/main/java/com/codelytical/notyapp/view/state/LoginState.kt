package com.codelytical.notyapp.view.state

import androidx.compose.runtime.Immutable
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel

@GenerateMutableModel
@Immutable
interface LoginState : State {
    val isLoading: Boolean
    val isLoggedIn: Boolean
    val error: String?
    val username: String
    val password: String
    val isValidUsername: Boolean?
    val isValidPassword: Boolean?

    companion object {
        val initialState = LoginState(
            isLoading = false,
            isLoggedIn = false,
            error = null,
            username = "",
            password = "",
            isValidUsername = null,
            isValidPassword = null
        )
    }
}
