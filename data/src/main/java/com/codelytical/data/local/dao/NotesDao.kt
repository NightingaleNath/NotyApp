package com.codelytical.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codelytical.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    fun getNoteById(noteId: String): Flow<NoteEntity?>

    @Query("SELECT * FROM notes ORDER BY isPinned = 1 DESC, created DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert
    suspend fun addNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotes(notes: List<NoteEntity>)

    @Query("UPDATE notes SET title = :title, note = :note WHERE noteId = :noteId")
    suspend fun updateNoteById(noteId: String, title: String, note: String)

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    suspend fun deleteNoteById(noteId: String)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("UPDATE notes SET noteId = :newNoteId WHERE noteId = :oldNoteId")
    fun updateNoteId(oldNoteId: String, newNoteId: String)

    @Query("UPDATE notes SET isPinned = :isPinned WHERE noteId = :noteId")
    suspend fun updateNotePin(noteId: String, isPinned: Boolean)
}
