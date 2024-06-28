package com.codelytical.notyapp.view.state

import androidx.compose.runtime.Immutable
import com.codelytical.core.model.Note
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel

@GenerateMutableModel
@Immutable
interface NotesState : State {
    val isLoading: Boolean
    val notes: List<Note>
    val error: String?
    val isUserLoggedIn: Boolean?
    val isConnectivityAvailable: Boolean?

    companion object {
        val initialState = NotesState(
            isLoading = false,
            notes = emptyList(),
            error = null,
            isUserLoggedIn = null,
            isConnectivityAvailable = null
        )
    }
}
