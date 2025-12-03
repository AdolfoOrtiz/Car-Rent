package com.example.carrentv1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color // Asegúrate de que esta importación esté presente
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

// Define los colores para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1565C0), // Azul principal para Dark Theme
    onPrimary = Color.White,
    secondary = Color(0xFFBBDEFB), // Un azul claro como secundario
    onSecondary = Color.Black,
    tertiary = Color(0xFF0D47A1), // Un azul más oscuro
    onTertiary = Color.White,
    background = Color.Black, // Fondo negro
    onBackground = Color.White, // Texto blanco en fondo negro
    surface = Color(0xFF1C1C1E), // Superficie muy oscura
    onSurface = Color.White, // Texto blanco en superficie oscura
    error = Color(0xFFCF6679),
    onError = Color.Black
)

// Define los colores para el tema claro
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1565C0), // Azul principal para Light Theme
    onPrimary = Color.White,
    secondary = Color(0xFFBBDEFB), // Un azul claro como secundario
    onSecondary = Color.Black,
    tertiary = Color(0xFF0D47A1), // Un azul más oscuro
    onTertiary = Color.White,
    background = Color.White, // Fondo blanco
    onBackground = Color.Black, // Texto negro en fondo blanco
    surface = Color(0xFFF5F6FA), // Superficie gris claro (como el de tus Cards)
    onSurface = Color.Black, // Texto negro en superficie clara
    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun CarRentV1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Ya no necesitas configurar el color de la barra de estado aquí si lo haces en TopAppBar
    // val view = LocalView.current
    // if (!view.isInEditMode) {
    //     SideEffect {
    //         val window = (view.context as Activity).window
    //         window.statusBarColor = colorScheme.primary.toArgb()
    //         WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    //     }
    // }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Asumo que tienes una definición de Typography en tu proyecto
        content = content
    )
}