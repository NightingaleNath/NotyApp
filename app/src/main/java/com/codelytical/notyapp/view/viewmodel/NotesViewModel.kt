package com.codelytical.notyapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelytical.core.preference.PreferenceManager
import com.codelytical.core.repository.NotyNoteRepository
import com.codelytical.core.session.SessionManager
import com.codelytical.notyapp.di.LocalRepository
import com.codelytical.notyapp.store.StateStore
import com.codelytical.notyapp.view.state.MutableNotesState
import com.codelytical.notyapp.view.state.NotesState
import com.codelytical.notyapp.view.state.mutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    @LocalRepository private val notyNoteRepository: NotyNoteRepository,
    private val sessionManager: SessionManager,
    val preferenceManager: PreferenceManager,
) : BaseViewModel<NotesState>() {

    private val stateStore = StateStore(initialState = NotesState.initialState.mutable())

    override val state: StateFlow<NotesState> = stateStore.state

    init {
        checkUserSession()
        observeNotes()
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.saveToken(null)
            setState { isUserLoggedIn = false }
        }
    }

    fun setDarkMode(enable: Boolean) {
        viewModelScope.launch {
            println("RESULT HERE: $enable")
            preferenceManager.setDarkMode(enable)
        }
    }

    private fun checkUserSession() {
        setState { isUserLoggedIn = sessionManager.getToken() != null }
    }

    private fun observeNotes() {
        notyNoteRepository.getAllNotes()
            .distinctUntilChanged()
            .onEach { response ->
                response.onSuccess { notes ->
                    setState {
                        this.isLoading = false
                        this.notes = notes
                    }
                }.onFailure { message ->
                    setState {
                        isLoading = false
                        error = message
                    }
                }
            }.onStart { setState { isLoading = true } }
            .launchIn(viewModelScope)
    }

    private fun setState(update: MutableNotesState.() -> Unit) = stateStore.setState(update)

    companion object {
        private const val TAG = "NotesViewModel"
    }
}
