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
    bold_24 = SpoqaBold.copy(
        fontSize = 24.sp
    ),
    bold_22 = SpoqaBold.copy(
        fontSize = 22.sp
    ),
    bold_20 = SpoqaBold.copy(
        fontSize = 20.sp
    ),
    bold_18 = SpoqaBold.copy(
        fontSize = 18.sp
    ),
    bold_16 = SpoqaBold.copy(
        fontSize = 16.sp
    ),
    bold_14 = SpoqaBold.copy(
        fontSize = 14.sp
    ),
    bold_12 = SpoqaBold.copy(
        fontSize = 12.sp
    ),
    bold_10 = SpoqaBold.copy(
        fontSize = 10.sp
    ),
    medium_24 = SpoqaMedium.copy(
        fontSize = 24.sp
    ),
    medium_22 = SpoqaMedium.copy(
        fontSize = 22.sp
    ),
    medium_20 = SpoqaMedium.copy(
        fontSize = 20.sp
    ),
    medium_18 = SpoqaMedium.copy(
        fontSize = 18.sp
    ),
    medium_16 = SpoqaMedium.copy(
        fontSize = 16.sp
    ),
    medium_14 = SpoqaMedium.copy(
        fontSize = 14.sp
    ),
    medium_12 = SpoqaMedium.copy(
        fontSize = 12.sp
    ),
    medium_10 = SpoqaMedium.copy(
        fontSize = 10.sp
    ),
    regular_24 = SpoqaRegular.copy(
        fontSize = 24.sp
    ),
    regular_22 = SpoqaRegular.copy(
        fontSize = 22.sp
    ),
    regular_20 = SpoqaRegular.copy(
        fontSize = 20.sp
    ),
    regular_18 = SpoqaRegular.copy(
        fontSize = 18.sp
    ),
    regular_16 = SpoqaRegular.copy(
        fontSize = 16.sp
    ),
    regular_14 = SpoqaRegular.copy(
        fontSize = 14.sp
    ),
    regular_12 = SpoqaRegular.copy(
        fontSize = 12.sp
    ),
    regular_10 = SpoqaRegular.copy(
        fontSize = 10.sp
    ),
)

@Immutable
data class TodoTypography(
    val bold_24: TextStyle,
    val bold_22: TextStyle,
    val bold_20: TextStyle,
    val bold_18: TextStyle,
    val bold_16: TextStyle,
    val bold_14: TextStyle,
    val bold_12: TextStyle,
    val bold_10: TextStyle,
    val medium_24: TextStyle,
    val medium_22: TextStyle,
    val medium_20: TextStyle,
    val medium_18: TextStyle,
    val medium_16: TextStyle,
    val medium_14: TextStyle,
    val medium_12: TextStyle,
    val medium_10: TextStyle,
    val regular_24: TextStyle,
    val regular_22: TextStyle,
    val regular_20: TextStyle,
    val regular_18: TextStyle,
    val regular_16: TextStyle,
    val regular_14: TextStyle,
    val regular_12: TextStyle,
    val regular_10: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    TodoTypography(
        bold_24 = SpoqaBold,
        bold_22 = SpoqaBold,
        bold_20 = SpoqaBold,
        bold_18 = SpoqaBold,
        bold_16 = SpoqaBold,
        bold_14 = SpoqaBold,
        bold_12 = SpoqaBold,
        bold_10 = SpoqaBold,
        medium_24 = SpoqaMedium,
        medium_22 = SpoqaMedium,
        medium_20 = SpoqaMedium,
        medium_18 = SpoqaMedium,
        medium_16 = SpoqaMedium,
        medium_14 = SpoqaMedium,
        medium_12 = SpoqaMedium,
        medium_10 = SpoqaMedium,
        regular_24 = SpoqaRegular,
        regular_22 = SpoqaRegular,
        regular_20 = SpoqaRegular,
        regular_18 = SpoqaRegular,
        regular_16 = SpoqaRegular,
        regular_14 = SpoqaRegular,
        regular_12 = SpoqaRegular,
        regular_10 = SpoqaRegular,
    )
}