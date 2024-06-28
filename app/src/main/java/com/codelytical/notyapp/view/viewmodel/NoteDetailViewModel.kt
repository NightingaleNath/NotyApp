package com.codelytical.notyapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.codelytical.core.model.Note
import com.codelytical.core.repository.NotyNoteRepository
import com.codelytical.notyapp.di.LocalRepository
import com.codelytical.notyapp.store.StateStore
import com.codelytical.notyapp.utils.NoteValidator
import com.codelytical.notyapp.view.state.MutableNoteDetailState
import com.codelytical.notyapp.view.state.NoteDetailState
import com.codelytical.notyapp.view.state.mutable
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class NoteDetailViewModel @AssistedInject constructor(
    @LocalRepository private val noteRepository: NotyNoteRepository,
    @Assisted private val noteId: String
) : BaseViewModel<NoteDetailState>() {

    private val stateStore = StateStore(initialState = NoteDetailState.initialState.mutable())

    override val state: StateFlow<NoteDetailState> = stateStore.state

    private var job: Job? = null
    private lateinit var currentNote: Note

    init {
        loadNote()
    }

    fun setTitle(title: String) {
        setState { this.title = title }
        validateNote()
    }

    fun setNote(note: String) {
        setState { this.note = note }
        validateNote()
    }

    private fun loadNote() {
        viewModelScope.launch {
            setState { isLoading = true }
            val note = noteRepository.getNoteById(noteId).firstOrNull()
            if (note != null) {
                currentNote = note
                setState {
                    this.isLoading = false
                    this.title = note.title
                    this.note = note.note
                    this.isPinned = note.isPinned
                }
            } else {
                setState {
                    isLoading = false
                    finished = true
                }
            }
        }
    }

    fun save() {
        val title = currentState.title?.trim() ?: return
        val note = currentState.note?.trim() ?: return

        job?.cancel()
        job = viewModelScope.launch {
            setState { isLoading = true }

            val response = noteRepository.updateNote(noteId, title, note)

            setState { isLoading = false }

            response.onSuccess { _ ->
                setState { finished = true }
            }.onFailure { message ->
                setState { error = message }
            }
        }
    }

    fun delete() {
        job?.cancel()
        job = viewModelScope.launch {
            setState { isLoading = true }

            val response = noteRepository.deleteNote(noteId)

            setState { isLoading = false }

            response.onSuccess { _ ->
                setState { finished = true }
            }.onFailure { message ->
                setState { error = message }
            }
        }
    }

    fun togglePin() {
        job?.cancel()
        job = viewModelScope.launch {
            setState { isLoading = true }

            val response = noteRepository.pinNote(noteId, !currentState.isPinned)

            setState {
                isLoading = false
                isPinned = !currentState.isPinned
            }

            response.onSuccess { noteId ->
                Log.d("TAG", "togglePin: $noteId")
            }.onFailure { message ->
                setState { error = message }
            }
        }
    }

    private fun validateNote() {
        try {
            val oldTitle = currentNote.title
            val oldNote = currentNote.note

            val title = currentState.title
            val note = currentState.note

            val isValid = title != null && note != null && NoteValidator.isValidNote(title, note)
            val areOldAndUpdatedNoteSame = oldTitle == title?.trim() && oldNote == note?.trim()

            setState { showSave = isValid && !areOldAndUpdatedNoteSame }
        } catch (_: Throwable) {
        }
    }

    private fun setState(update: MutableNoteDetailState.() -> Unit) = stateStore.setState(update)

    @AssistedFactory
    interface Factory {
        fun create(noteId: String): NoteDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            noteId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(noteId) as T
            }
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AssistedInjectModule
