package com.gmsociety.gymnotes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmsociety.gymnotes.data.local.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat(
        "dd.MM.yyyy HH:mm",
        Locale.getDefault()
    )

    return formatter.format(Date(timestamp))
}

@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black)
        ) {
            Text(
                text = note.content,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                color = Color.White
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
            )
        }

        Text(
            text =  if (note.title.isBlank()) {
                        "Title"
                    } else {
                        if (note.title.lines().first().length > 8) {
                            note.title.lines().first().take(8).trim() + "..."
                        } else {
                            note.title.lines().first().trim()
                        }
                    },
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(1f),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = formatDate(note.createdAt),
            color = Color.Gray,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )
    }
}
