package com.example.knowledgetree.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.knowledgetree.R

// Set of Material typography styles to start with

val PingFang = FontFamily(
    Font(R.font.pingfang_extralight, FontWeight.ExtraLight),
    Font(R.font.pingfang_regular, FontWeight.Normal),
    Font(R.font.pingfang_bold, FontWeight.Bold),
    Font(R.font.pingfang_heavy, FontWeight.ExtraBold),
    Font(R.font.pingfang_light, FontWeight.Light),
    Font(R.font.pingfang_medium, FontWeight.Medium),
)


val treeTypography = Typography(
    defaultFontFamily = PingFang,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        color = Color(0f, 0f, 0f, alpha = 0.6f)
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Light,
        color = Color(0f, 0f, 0f, alpha = 0.6f)
    )



    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
val Typography.customTitle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 100.sp,
            color = Color.Red
        )
    }