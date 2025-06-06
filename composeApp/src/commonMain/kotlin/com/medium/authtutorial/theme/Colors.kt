package com.medium.authtutorial.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val VB_Orange_Accent = Color(0xFFFF6B35)
val VB_Orange_Dark_Variant = Color(0xFFE65100)
val VB_Orange_Light_Variant = Color(0xFFFF8A65)

val VB_Blue_Deep = Color(0xFF0D47A1)
val VB_Blue_Light_Variant = Color(0xFF1976D2)
val VB_Blue_Bright_Dark = Color(0xFF5C9BFF)
val VB_Blue_Dark_Variant = Color(0xFF2962FF)

val VB_Light_Background = Color(0xFFFDFDFD)
val VB_Light_Surface = Color(0xFFFFFFFF)
val VB_Light_Text_Primary = Color(0xFF121212)
val VB_Light_Text_On_Orange = Color(0xFF000000)
val VB_Light_Text_On_Blue = Color(0xFFFFFFFF)

val VB_Dark_Background = Color(0xFF121212)
val VB_Dark_Surface = Color(0xFF1E1E1E) 
val VB_Dark_Text_Primary = Color(0xFFE0E0E0)
val VB_Dark_Text_On_Orange = Color(0xFF000000)
val VB_Dark_Text_On_Blue = Color(0xFF000000)

val VB_Success = Color(0xFF28A745)
val VB_Error_Light = Color(0xFFDC3545)
val VB_Error_Dark = Color(0xFFCF6679)
val VB_Success_Dark = Color(0xFF70D886)



internal val VibrantBurstLightColorScheme = lightColorScheme(
    primary = VB_Blue_Deep,
    onPrimary = VB_Light_Text_On_Blue,
    primaryContainer = VB_Blue_Light_Variant, 
    onPrimaryContainer = VB_Light_Text_Primary, 

    secondary = VB_Orange_Accent,
    onSecondary = VB_Light_Text_On_Orange,
    secondaryContainer = VB_Orange_Dark_Variant, 
    onSecondaryContainer = VB_Light_Text_On_Orange, 

    
    tertiary = VB_Orange_Dark_Variant,
    onTertiary = VB_Light_Text_On_Orange,
    tertiaryContainer = VB_Orange_Accent,
    onTertiaryContainer = VB_Light_Text_On_Orange,

    error = VB_Error_Light,
    onError = VB_Light_Text_On_Blue,
    errorContainer = Color(0xFFF9DEDC), 
    onErrorContainer = Color(0xFF410E0B), 

    background = VB_Light_Background,
    onBackground = VB_Light_Text_Primary,

    surface = VB_Light_Surface,
    onSurface = VB_Light_Text_Primary,
    surfaceVariant = VB_Light_Background, 
    onSurfaceVariant = VB_Light_Text_Primary, 

    outline = VB_Light_Text_Primary.copy(alpha = 0.5f), 
    inverseOnSurface = VB_Dark_Text_Primary,
    inverseSurface = VB_Dark_Background,
    inversePrimary = VB_Blue_Bright_Dark
)


internal val VibrantBurstDarkColorScheme = darkColorScheme(
    primary = VB_Blue_Bright_Dark,
    onPrimary = VB_Dark_Text_On_Blue,
    primaryContainer = VB_Blue_Dark_Variant, 
    onPrimaryContainer = VB_Dark_Text_Primary, 

    secondary = VB_Orange_Light_Variant,
    onSecondary = VB_Dark_Text_On_Orange,
    secondaryContainer = VB_Orange_Accent, 
    onSecondaryContainer = VB_Dark_Text_On_Orange, 

    
    tertiary = VB_Orange_Accent,
    onTertiary = VB_Dark_Text_On_Orange,
    tertiaryContainer = VB_Orange_Light_Variant,
    onTertiaryContainer = VB_Dark_Text_On_Orange,

    error = VB_Error_Dark,
    onError = VB_Dark_Text_On_Blue,
    errorContainer = Color(0xFF93000A), 
    onErrorContainer = Color(0xFFFFDAD6), 

    background = VB_Dark_Background,
    onBackground = VB_Dark_Text_Primary,

    surface = VB_Dark_Background, 
    onSurface = VB_Dark_Text_Primary,
    surfaceVariant = VB_Dark_Surface, 
    onSurfaceVariant = VB_Dark_Text_Primary, 

    outline = VB_Dark_Text_Primary.copy(alpha = 0.5f),
    inverseOnSurface = VB_Light_Text_Primary,
    inverseSurface = VB_Light_Background,
    inversePrimary = VB_Blue_Deep
)