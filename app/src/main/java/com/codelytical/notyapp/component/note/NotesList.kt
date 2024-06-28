package com.codelytical.notyapp.component.note

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.codelytical.core.model.Note
import com.codelytical.notyapp.utils.collection.ComposeImmutableList

@Composable
fun NotesList(notes: ComposeImmutableList<Note>, onClick: (Note) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 4.dp),
        modifier = Modifier.testTag("notesList")
    ) {
        items(
            items = notes,
            itemContent = { note ->
                NoteCard(
                    title = note.title,
                    note = note.note,
                    isPinned = note.isPinned,
                    onNoteClick = { onClick(note) }
                )
            },
            key = { Triple(it.id, it.title, it.note) }
        )
    }
}
