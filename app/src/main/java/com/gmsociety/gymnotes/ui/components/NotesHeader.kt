package com.gmsociety.gymnotes.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun NotesHeader(
    isSearchVisible: Boolean,
    onSearchVisibilityChange: (Boolean) -> Unit,
    onAddNote: () -> Unit
) {
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
                onSearchVisibilityChange(!isSearchVisible)
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    }
}