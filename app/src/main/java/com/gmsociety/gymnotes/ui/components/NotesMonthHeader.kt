package com.gmsociety.gymnotes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NotesMonthHeader(
    month: YearMonth
) {
    val monthFormatter = DateTimeFormatter.ofPattern(
        "LLLL yyyy",
        Locale.getDefault()
    )

    Text(
        text = month
            .format(monthFormatter)
            .replaceFirstChar {
                it.uppercase()
            },
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 4.dp
            ),
        style = MaterialTheme
            .typography
            .titleMedium
    )
}