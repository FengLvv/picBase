package com.example.knowledgetree.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun KnowledgeTreeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colors = colors,
        typography = treeTypography,
        shapes = Shapes,
        content = content
    )
}




//定义主题色组
//颜色储存到src里面
private val LightColors by lazy {  lightColors(
    primary =remindGreen2,
    primaryVariant = remindGreen1,
    onPrimary = Color.White,
    secondary = lightGreen1,
    secondaryVariant = lightGreen2,
    onSecondary = Color.White,
) }
//深色主题
private val DarkColors = darkColors(
    primary =remindGreen2,
    primaryVariant = remindGreen1,
    onPrimary = Color.White,
    secondary = lightGreen1,
    secondaryVariant = lightGreen2,
    onSecondary = Color.White,
)
