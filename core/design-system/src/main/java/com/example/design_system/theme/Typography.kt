package com.example.design_system.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.design_system.R

private val Spoqa = FontFamily(
    Font(R.font.spoqa_light, FontWeight.Light),
    Font(R.font.spoqa_regular, FontWeight.Normal),
    Font(R.font.spoqa_medium, FontWeight.Medium),
    Font(R.font.spoqa_bold, FontWeight.SemiBold)
)

private val SpoqaLight = TextStyle(
    fontFamily = Spoqa,
    fontWeight = FontWeight.Light,
)

private val SpoqaRegular = TextStyle(
    fontFamily = Spoqa,
    fontWeight = FontWeight.Normal
)

private val SpoqaMedium = TextStyle(
    fontFamily = Spoqa,
    fontWeight = FontWeight.Medium
)

private val SpoqaBold = TextStyle(
    fontFamily = Spoqa,
    fontWeight = FontWeight.Bold
)

internal val Typography = TodoTypography(
    headlineLarge = SpoqaBold.copy(
        fontSize = 24.sp,
    ),
    headlineMedium = SpoqaBold.copy(
        fontSize = 20.sp,
    ),
    headlineSmall = SpoqaBold.copy(
        fontSize = 16.sp,
    ),
    infoTextStyle = SpoqaMedium.copy(
        fontSize = 18.sp,
    ),
    infoDescTextStyle = SpoqaRegular.copy(
        fontSize = 14.sp,
    ),
    taskTextStyle = SpoqaRegular.copy(
        fontSize = 16.sp,
    ),
    taskDescTextStyle = SpoqaRegular.copy(
        fontSize = 12.sp,
    ),
    timerTextStyle = SpoqaRegular.copy(
        fontSize = 42.sp,
    ),
    settingItemTextStyle = SpoqaRegular.copy(
        fontSize = 18.sp,
    ),
    durationTextStyle = SpoqaRegular.copy(
        fontSize = 20.sp,
    ),
)

@Immutable
data class TodoTypography(
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,

    val infoTextStyle: TextStyle,
    val infoDescTextStyle: TextStyle,

    val taskTextStyle: TextStyle,
    val taskDescTextStyle: TextStyle,

    val timerTextStyle: TextStyle,
    val settingItemTextStyle: TextStyle,
    val durationTextStyle: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    TodoTypography(
        headlineLarge = SpoqaBold,
        headlineMedium = SpoqaBold,
        headlineSmall = SpoqaBold,
        infoTextStyle = SpoqaMedium,
        infoDescTextStyle = SpoqaRegular,
        taskTextStyle = SpoqaRegular,
        taskDescTextStyle = SpoqaRegular,
        timerTextStyle = SpoqaRegular,
        settingItemTextStyle = SpoqaRegular,
        durationTextStyle = SpoqaRegular,
    )
}