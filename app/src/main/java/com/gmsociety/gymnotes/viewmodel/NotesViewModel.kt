package com.gmsociety.gymnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmsociety.gymnotes.data.local.Note
import com.gmsociety.gymnotes.data.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> =
        repository.allNotes.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addNote(
        title: String,
        content: String
    ) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()

            repository.insertNote(
                Note(
                    title = title,
                    content = content,
                    createdAt = now,
                    updatedAt = now
                )
            )
        }
    }

    fun updateNote(
        note: Note,
        title: String,
        content: String
    ) {
        viewModelScope.launch {
            repository.updateNote(
                note.copy(
                    title = title,
                    content = content,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
    }


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}
