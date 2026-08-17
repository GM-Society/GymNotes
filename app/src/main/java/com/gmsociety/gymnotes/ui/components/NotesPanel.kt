package com.gmsociety.gymnotes.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import com.gmsociety.gymnotes.data.local.Note
import com.gmsociety.gymnotes.ui.theme.notesBackgroudColor
import java.time.Instant
import java.time.ZoneId
import java.time.YearMonth

@Composable
fun NotesPanel(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onProgressChange: (Float) -> Unit,
    onAddNote: () -> Unit,
    onNoteClick: (Note) -> Unit
) {
    val isSearchVisible = remember {
        mutableStateOf(false)
    }

    val searchQuery = remember {
        mutableStateOf("")
    }

    val filteredNotes = notes.filter { note ->
        note.title.contains(
            searchQuery.value,
            ignoreCase = true
        ) || note.content.contains(
            searchQuery.value,
            ignoreCase = true
        )
    }

    val notesByMonth = filteredNotes
        .sortedByDescending {
            it.createdAt
        }
        .groupBy {
            Instant.ofEpochMilli(it.createdAt)
                .atZone(ZoneId.systemDefault())
                .let {
                    YearMonth.from(it)
                }
        }

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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ADD NOTE + SEARCH
            item(
                span = {
                    GridItemSpan(2)
                }
            ) {
                NotesHeader(
                    isSearchVisible = isSearchVisible.value,
                    onSearchVisibilityChange = { visible ->
                        isSearchVisible.value = visible

                        if (!visible) {
                            searchQuery.value = ""
                        }
                    },
                    onAddNote = onAddNote
                )
            }

            // SEARCH INPUT
            item(
                span = {
                    GridItemSpan(2)
                }
            ) {
                NotesSearch(
                    visible = isSearchVisible.value,
                    query = searchQuery.value,
                    onQueryChange = {
                        searchQuery.value = it
                    }
                )
            }

            // NOTES BY MONTH
            notesByMonth.forEach { (month, monthNotes) ->

                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    NotesMonthHeader(
                        month = month
                    )
                }

                items(monthNotes) { note ->
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
}