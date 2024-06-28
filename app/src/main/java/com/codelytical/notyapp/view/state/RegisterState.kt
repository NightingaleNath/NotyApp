package com.codelytical.notyapp.view.state

import androidx.compose.runtime.Immutable
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel

@GenerateMutableModel
@Immutable
interface RegisterState : State {
    val isLoading: Boolean
    val isLoggedIn: Boolean
    val username: String
    val password: String
    val confirmPassword: String
    val isValidUsername: Boolean?
    val isValidPassword: Boolean?
    val isValidConfirmPassword: Boolean?
    val error: String?

    companion object {
        val initialState = RegisterState(
            isLoading = false,
            isLoggedIn = false,
            username = "",
            password = "",
            confirmPassword = "",
            isValidUsername = null,
            isValidPassword = null,
            isValidConfirmPassword = null,
            error = null
        )
    }
}
