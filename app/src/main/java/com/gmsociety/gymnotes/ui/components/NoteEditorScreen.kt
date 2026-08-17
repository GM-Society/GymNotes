package com.gmsociety.gymnotes.ui.theme.screens.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gmsociety.gymnotes.data.local.Note
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteEditorScreen(
    note: Note? = null,
    onBack: () -> Unit,
    onSave: (title: String, content: String) -> Unit,
    onAutoSave: (title: String, content: String) -> Unit
) {
    var title by remember {
        mutableStateOf(
            TextFieldValue(
                text = note?.title ?: "",
                selection = TextRange(
                    (note?.title ?: "").length
                )
            )
        )
    }

    var content by remember {
        mutableStateOf(note?.content ?: "")
    }

    var isEditingTitle by remember {
        mutableStateOf(false)
    }

    var isVisible by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val isKeyboardVisible = WindowInsets.isImeVisible

    val arrowRotation by animateFloatAsState(
        targetValue = if (isEditingTitle) 90f else 0f,
        label = "arrowRotation"
    )

    LaunchedEffect(Unit) {
        isVisible = true
    }

    LaunchedEffect(isEditingTitle) {
        if (isEditingTitle) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible && isEditingTitle) {
            focusManager.clearFocus()
            isEditingTitle = false
        }
    }

    LaunchedEffect(title.text, content) {
        if (
            title.text.isBlank() &&
            content.isBlank()
        ) {
            return@LaunchedEffect
        }

        delay(500)

        onAutoSave(
            title.text,
            content
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                animationSpec = tween(300)
            ) + slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { 30 }
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 40.dp,
                            bottom = 10.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            if (isEditingTitle) {
                                focusManager.clearFocus()
                                isEditingTitle = false
                            } else {
                                onBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.rotate(
                                arrowRotation
                            )
                        )
                    }

                    if (isEditingTitle) {

                        BasicTextField(
                            value = title,
                            onValueChange = {
                                title = it
                            },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester)
                                .padding(start = 10.dp),
                            textStyle = MaterialTheme
                                .typography
                                .titleMedium
                                .copy(
                                    color = Color.White
                                ),
                            cursorBrush = SolidColor(
                                Color.White
                            ),
                            singleLine = true
                        )

                    } else {

                        Text(
                            text = if (title.text.isBlank()) {
                                "Title"
                            } else {
                                if (title.text.length > 12) {
                                    title.text
                                        .lines()
                                        .first()
                                        .take(12) + "..."
                                } else {
                                    title.text
                                        .lines()
                                        .first()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    isEditingTitle = true
                                }
                                .padding(start = 10.dp),
                            style = MaterialTheme
                                .typography
                                .titleMedium,
                            color = Color.White,
                            maxLines = 1
                        )
                    }

                    IconButton(
                        onClick = {
                            onSave(
                                title.text,
                                content
                            )
                        },
                        enabled = title.text.isNotBlank() ||
                                content.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.4f),
                    thickness = 1.dp
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    BasicTextField(
                        value = content,
                        onValueChange = {
                            content = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(700.dp)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    isEditingTitle = false
                                }
                            },
                        textStyle = MaterialTheme
                            .typography
                            .bodyMedium
                            .copy(
                                color = Color.White
                            ),
                        cursorBrush = SolidColor(
                            Color.White
                        ),
                        decorationBox = { innerTextField ->

                            if (content.isEmpty()) {
                                Text(
                                    text = "Nasz przysmak to wafle low fat, ale trening tez jest, po nim dobre wheyy",
                                    style = MaterialTheme
                                        .typography
                                        .bodyLarge,
                                    color = Color.Gray
                                )
                            }

                            innerTextField()
                        }
                    )
                }
            }
        }
    }
}