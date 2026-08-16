package com.gmsociety.gymnotes.ui.theme.screens.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.gmsociety.gymnotes.ui.components.NotesPanel
import com.gmsociety.gymnotes.viewmodel.NotesViewModel
import androidx.compose.runtime.mutableStateOf
import com.gmsociety.gymnotes.data.local.Note


@Composable
fun NotesScreen(
    viewModel: NotesViewModel
) {
//    Vars

    val notes = viewModel.notes.collectAsState()


//    Panel Progress States
    var panelProgress by remember {
        mutableFloatStateOf(0f)
    }

//    Note states
    var selectedNote by remember {
        mutableStateOf<Note?>(null)
    }

//    Editor State
    var showEditor by remember {
        mutableStateOf(false)
    }

    if (showEditor) {
        NoteEditorScreen(
            note = selectedNote,

            onBack = {
                showEditor = false
                selectedNote = null
            },

            onAutoSave = { title, content ->

                if (selectedNote == null) {
                    viewModel.addNote(
                        title = title,
                        content = content
                    )
                } else {
                    viewModel.updateNote(
                        note = selectedNote!!,
                        title = title,
                        content = content
                    )
                }
            },

            onSave = { title, content ->

                if (selectedNote == null) {
                    viewModel.addNote(
                        title = title,
                        content = content
                    )
                } else {
                    viewModel.updateNote(
                        note = selectedNote!!,
                        title = title,
                        content = content
                    )
                }

                showEditor = false
                selectedNote = null
            }
        )

        return
    }




    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("Gym")
                        }

                        withStyle(
                            SpanStyle(
                                color = Color.White
                            )
                        ) {
                            append("Notes")
                        }
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "The only notebook app You need",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            NotesPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .align(Alignment.BottomCenter),

                notes = notes.value,

                onAddNote = {
                    showEditor = true
                },

                onNoteClick = { note ->
                    selectedNote = note
                    showEditor = true
                },

                onProgressChange = { change ->
                    panelProgress = (panelProgress + change)
                        .coerceIn(0f, 1f)
                }
            )




        }
    }
}