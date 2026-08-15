package com.gmsociety.gymnotes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gmsociety.gymnotes.R

// Main font: Satoshi
@OptIn(ExperimentalTextApi::class)
private val Satoshi = FontFamily(
    Font(
        R.font.satoshi_variable,
        weight = FontWeight.Normal
    ),
    Font(
        R.font.satoshi_bold,
        weight = FontWeight.Bold
    )
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Satoshi,
        fontWeight = FontWeight.Bold,
        fontSize = 41.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.sp
    ),

    titleSmall = TextStyle(
        fontFamily = Satoshi,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.sp
    )
)