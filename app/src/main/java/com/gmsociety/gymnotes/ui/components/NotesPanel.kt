package com.gmsociety.gymnotes.ui.components

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.gmsociety.gymnotes.data.local.Note
import com.gmsociety.gymnotes.ui.theme.notesBackgroudColor
import androidx.compose.material3.Button

@Composable
fun NotesPanel(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onProgressChange: (Float) -> Unit,
    onAddNote: () -> Unit,
    onNoteClick: (Note) -> Unit
) {
    Surface(
        modifier = modifier.pointerInput(Unit) {
            detectVerticalDragGestures(
                onVerticalDrag = { _, dragAmount ->
                    val change = -dragAmount / 1000f
                    onProgressChange(change)
                }
            )
        },
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        color = notesBackgroudColor
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            item {
                Button(
                    onClick = onAddNote
                ) {
                    Text("+ Add note")
                }
            }
            items(notes) { note ->
                NoteCard(
                    note = note,
                    onClick = {
                        onNoteClick(note)
                    }
                )
            }


        }
    }
}
