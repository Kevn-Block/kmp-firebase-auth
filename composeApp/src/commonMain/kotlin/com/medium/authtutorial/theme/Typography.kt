package com.medium.authtutorial.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    displayLarge = TextStyle( 
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp, 
        lineHeight = 32.sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp, 
        lineHeight = 28.sp
    ),
    headlineLarge = TextStyle( 
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = TextStyle( 
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    headlineSmall = TextStyle( 
        fontWeight = FontWeight.Medium, 
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    titleLarge = TextStyle( 
        fontWeight = FontWeight.Medium, 
        fontSize = 18.sp, 
        lineHeight = 22.sp
    ),
    titleMedium = TextStyle( 
        fontWeight = FontWeight.Medium, 
        fontSize = 16.sp,
        lineHeight = 20.sp 
    ),
    titleSmall = TextStyle( 
        fontWeight = FontWeight.Medium, 
        fontSize = 14.sp,
        lineHeight = 18.sp 
    ),
    bodyLarge = TextStyle( 
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle( 
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle( 
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle( 
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle( 
        fontWeight = FontWeight.Normal, 
        fontSize = 12.sp, 
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle( 
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp 
    )
)