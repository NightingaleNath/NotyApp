package com.codelytical.notyapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelytical.core.repository.NotyNoteRepository
import com.codelytical.notyapp.di.LocalRepository
import com.codelytical.notyapp.store.StateStore
import com.codelytical.notyapp.utils.NoteValidator
import com.codelytical.notyapp.view.state.AddNoteState
import com.codelytical.notyapp.view.state.MutableAddNoteState
import com.codelytical.notyapp.view.state.mutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    @LocalRepository private val noteRepository: NotyNoteRepository,
) : BaseViewModel<AddNoteState>() {

    private val stateStore = StateStore(AddNoteState.initialState.mutable())

    override val state: StateFlow<AddNoteState> = stateStore.state

    private var job: Job? = null

    fun setTitle(title: String) {
        setState { this.title = title }
        validateNote()
    }

    fun setNote(note: String) {
        setState { this.note = note }
        validateNote()
    }

    fun add() {
        job?.cancel()
        job = viewModelScope.launch {
            val title = state.value.title.trim()
            val note = state.value.note.trim()

            setState { isAdding = true }

            val result = noteRepository.addNote(title, note)

            result.onSuccess { _ ->
                setState {
                    isAdding = false
                    added = true
                }
            }.onFailure { message ->
                setState {
                    isAdding = false
                    added = false
                    errorMessage = message
                }
            }
        }
    }

    private fun validateNote() {
        val isValid = NoteValidator.isValidNote(currentState.title, currentState.note)
        setState { showSave = isValid }
    }

    /**
     * In simpleapp module, ViewModel's instance is created using Hilt NavGraph ViewModel so it
     * doesn't clears the ViewModel when the Fragment's onDestroy() lifecycle is invoked and
     * thus it holds the stale state when the same fragment is relaunched. So this method is
     * simply a way for Fragment to ask ViewModel to reset the state.
     */
    fun resetState() {
        setState {
            note = ""
            title = ""
            note = ""
            showSave = false
            isAdding = false
            added = false
            errorMessage = null
        }
    }

    private fun setState(update: MutableAddNoteState.() -> Unit) = stateStore.setState(update)
}
