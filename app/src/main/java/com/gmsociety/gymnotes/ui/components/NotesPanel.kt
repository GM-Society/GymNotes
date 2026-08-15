package com.gmsociety.gymnotes.ui.components

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.gmsociety.gymnotes.ui.theme.notesBackgroudColor

@Composable
fun NotesPanel(
    modifier: Modifier = Modifier,
    onProgressChange: (Float) -> Unit
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
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
        }
    }
}
