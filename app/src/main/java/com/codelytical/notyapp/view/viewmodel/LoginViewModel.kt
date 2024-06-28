package com.codelytical.notyapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.codelytical.core.repository.NotyUserRepository
import com.codelytical.core.session.SessionManager
import com.codelytical.notyapp.store.StateStore
import com.codelytical.notyapp.utils.AuthValidator
import com.codelytical.notyapp.view.state.LoginState
import com.codelytical.notyapp.view.state.MutableLoginState
import com.codelytical.notyapp.view.state.mutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val notyUserRepository: NotyUserRepository,
    private val sessionManager: SessionManager
) : BaseViewModel<LoginState>() {

    private val stateStore = StateStore(initialState = LoginState.initialState.mutable())

    override val state: StateFlow<LoginState> = stateStore.state

    fun setUsername(username: String) {
        setState { this.username = username }
    }

    fun setPassword(password: String) {
        setState { this.password = password }
    }

    fun login() {
        if (!validateCredentials()) return

        viewModelScope.launch {
            val username = currentState.username
            val password = currentState.password

            setState { isLoading = true }

            val loginResponse = notyUserRepository.login(username, password)

            loginResponse.onSuccess { token ->
                sessionManager.saveToken(token)
                setState {
                    isLoading = false
                    isLoggedIn = true
                    error = null
                }
            }.onFailure { message ->
                Log.d("TAG", "login-onFailure: $message")
                setState {
                    isLoading = false
                    isLoggedIn = false
                    error = message
                }
            }
        }
    }

    fun clearError() = setState { error = null }

    private fun validateCredentials(): Boolean {
        val isValidUsername = AuthValidator.isValidUsername(currentState.username)
        val isValidPassword = AuthValidator.isValidPassword(currentState.password)

        setState {
            this.isValidUsername = isValidUsername
            this.isValidPassword = isValidPassword
        }

        return isValidUsername && isValidPassword
    }

    private fun setState(update: MutableLoginState.() -> Unit) = stateStore.setState(update)
}
