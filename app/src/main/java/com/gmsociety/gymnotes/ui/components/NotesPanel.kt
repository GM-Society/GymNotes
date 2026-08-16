package com.gmsociety.gymnotes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.gmsociety.gymnotes.data.local.Note
import com.gmsociety.gymnotes.ui.theme.notesBackgroudColor

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

    val isAddNotePressed = remember {
        mutableStateOf(false)
    }

    val addNoteScale = animateFloatAsState(
        targetValue = if (isAddNotePressed.value) {
            0.95f
        } else {
            1f
        },
        label = "addNoteScale"
    )

    val filteredNotes = notes.filter { note ->
        note.title.contains(
            searchQuery.value,
            ignoreCase = true
        ) || note.content.contains(
            searchQuery.value,
            ignoreCase = true
        )
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

//            ADD NOTE + SEARCH
            item(
                span = {
                    GridItemSpan(2)
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Button(
                            onClick = onAddNote,
                            modifier = Modifier
                                .weight(1f)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            isAddNotePressed.value = true

                                            try {
                                                awaitRelease()
                                            } finally {
                                                isAddNotePressed.value = false
                                            }
                                        }
                                    )
                                }
                                .graphicsLayer {
                                    scaleX = addNoteScale.value
                                    scaleY = addNoteScale.value
                                }
                        ) {
                            Text("+ Add note")
                        }

                        IconButton(
                            onClick = {
                                isSearchVisible.value =
                                    !isSearchVisible.value

                                if (!isSearchVisible.value) {
                                    searchQuery.value = ""
                                }
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }

//                    SEARCH INPUT
                    AnimatedVisibility(
                        visible = isSearchVisible.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        BasicTextField(
                            value = searchQuery.value,
                            onValueChange = {
                                searchQuery.value = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .drawBehind {
                                    val strokeWidth = 1.dp.toPx()
                                    val y = size.height - strokeWidth / 2

                                    drawLine(
                                        color = Color.Gray,
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {

                                    if (searchQuery.value.isEmpty()) {
                                        Text(
                                            text = "Search notes...",
                                            color = Color.Gray
                                        )
                                    }

                                    innerTextField()
                                }
                            }
                        )
                    }
                }
            }

//            NOTES
            items(filteredNotes) { note ->
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
