package com.gmsociety.gymnotes.data.repository

import com.gmsociety.gymnotes.data.local.Note
import com.gmsociety.gymnotes.data.local.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: NoteDao
) {

    val allNotes: Flow<List<Note>> =
        noteDao.getAllNotes()

    suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)
    }

    suspend fun getNoteById(id: Long): Note? {
        return noteDao.getNoteById(id)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}
