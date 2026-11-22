package com.erodriguez.sistema_tickets_si_cobra.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AzulPrincipal,
    onPrimary = Color.White,
    primaryContainer = AzulContenedor,
    onPrimaryContainer = AzulPrincipal,
    secondary = AzulOscuro,
    onSecondary = Color.White,
    background = FondoPantalla,
    onBackground = TextoPrincipal,
    surface = Color.White,
    onSurface = TextoPrincipal,
    onSurfaceVariant = TextoSecundario,
    error = RojoError,
    onError = Color.White,
    errorContainer = RojoFondo,
    onErrorContainer = RojoError
)

@Composable
fun Sistema_tickets_si_cobraTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
