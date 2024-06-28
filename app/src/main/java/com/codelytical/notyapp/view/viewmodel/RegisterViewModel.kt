package com.codelytical.notyapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelytical.core.repository.NotyUserRepository
import com.codelytical.core.session.SessionManager
import com.codelytical.notyapp.store.StateStore
import com.codelytical.notyapp.utils.AuthValidator
import com.codelytical.notyapp.view.state.MutableRegisterState
import com.codelytical.notyapp.view.state.RegisterState
import com.codelytical.notyapp.view.state.mutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val notyUserRepository: NotyUserRepository,
    private val sessionManager: SessionManager
) : BaseViewModel<RegisterState>() {

    private val stateStore = StateStore(initialState = RegisterState.initialState.mutable())

    override val state = stateStore.state

    fun setUsername(username: String) {
        setState { this.username = username }
    }

    fun setPassword(password: String) {
        setState { this.password = password }
    }

    fun setConfirmPassword(confirmPassword: String) {
        setState { this.confirmPassword = confirmPassword }
    }

    fun register() {
        if (!validateCredentials()) return

        viewModelScope.launch {
            val username = currentState.username
            val password = currentState.password

            setState { isLoading = true }

            val response = notyUserRepository.createUser(username, password)

            response.onSuccess {
                loginUser(username, password)
            }.onFailure { message ->
                setState {
                    isLoading = false
                    error = message
                    isLoggedIn = false
                }
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val loginResponse = notyUserRepository.login(username, password)

            loginResponse.onSuccess { token ->
                sessionManager.saveToken(token)
                setState {
                    isLoading = false
                    isLoggedIn = true
                    error = null
                }
            }.onFailure { message ->
                setState {
                    isLoading = false
                    error = message
                    isLoggedIn = false
                }
            }
        }
    }

    fun clearError() = setState { error = null }

    private fun validateCredentials(): Boolean {
        val username = currentState.username
        val password = currentState.password
        val confirmPassword = currentState.confirmPassword

        val isValidUsername = AuthValidator.isValidUsername(username)
        val isValidPassword = AuthValidator.isValidPassword(password)
        val arePasswordAndConfirmPasswordSame = AuthValidator.isPasswordAndConfirmPasswordSame(
            password,
            confirmPassword
        )

        setState {
            this.isValidUsername = isValidUsername
            this.isValidPassword = isValidPassword
            this.isValidConfirmPassword = arePasswordAndConfirmPasswordSame
        }

        return isValidUsername && isValidPassword && arePasswordAndConfirmPasswordSame
    }

    private fun setState(update: MutableRegisterState.() -> Unit) = stateStore.setState(update)
}
