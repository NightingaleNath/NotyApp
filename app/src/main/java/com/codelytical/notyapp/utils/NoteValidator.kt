package com.codelytical.notyapp.utils

object NoteValidator {
    fun isValidNote(title: String, note: String) = (title.trim().length >= 4 && note.isNotBlank())
}
