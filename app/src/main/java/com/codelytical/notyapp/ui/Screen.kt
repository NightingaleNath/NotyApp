package com.codelytical.notyapp.ui

sealed class Screen(val route: String, val name: String) {
    data object SignUp : Screen("signup", "Sign Up")
    data object Login : Screen("login", "Login")
    data object Notes : Screen("notes", "Notes")
    data object NotesDetail : Screen("note/{noteId}", "Note details") {
        fun route(noteId: String) = "note/$noteId"

        const val ARG_NOTE_ID: String = "noteId"
    }

    data object AddNote : Screen("note/new", "New note")
    data object About : Screen("about", "About")
}
