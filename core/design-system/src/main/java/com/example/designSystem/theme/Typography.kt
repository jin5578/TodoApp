package com.example.designSystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.designSystem.R

private val Spoqa = FontFamily(
    Font(R.font.spoqa_light, FontWeight.Light),
    Font(R.font.spoqa_regular, FontWeight.Normal),
    Font(R.font.spoqa_medium, FontWeight.Medium),
    Font(R.font.spoqa_bold, FontWeight.SemiBold),
)

private val SpoqaLight = TextStyle(
    fontFamily = Spoqa,
    fontWeight = FontWeight.Light,
)

private val SpoqaRegular = TextStyle(
    fontFamily = Spoqa,
    fontWeight = FontWeight.Normal,
)

private val SpoqaMedium = TextStyle(
    fontFamily = Spoqa,
    fontWeight = FontWeight.Medium,
)

private val SpoqaBold = TextStyle(
    fontFamily = Spoqa,
    fontWeight = FontWeight.Bold,
)

internal val Typography = TodoTypography(
    bold_32 = SpoqaBold.copy(
        fontSize = 32.sp,
    ),
    bold_28 = SpoqaBold.copy(
        fontSize = 28.sp,
    ),
    bold_24 = SpoqaBold.copy(
        fontSize = 24.sp,
    ),
    bold_20 = SpoqaBold.copy(
        fontSize = 20.sp,
    ),
    bold_16 = SpoqaBold.copy(
        fontSize = 16.sp,
    ),
    bold_12 = SpoqaBold.copy(
        fontSize = 12.sp,
    ),
    bold_08 = SpoqaBold.copy(
        fontSize = 8.sp,
    ),
    medium_32 = SpoqaMedium.copy(
        fontSize = 32.sp,
    ),
    medium_28 = SpoqaMedium.copy(
        fontSize = 28.sp,
    ),
    medium_24 = SpoqaMedium.copy(
        fontSize = 24.sp,
    ),
    medium_20 = SpoqaMedium.copy(
        fontSize = 20.sp,
    ),
    medium_16 = SpoqaMedium.copy(
        fontSize = 16.sp,
    ),
    medium_12 = SpoqaMedium.copy(
        fontSize = 12.sp,
    ),
    medium_08 = SpoqaMedium.copy(
        fontSize = 8.sp,
    ),
    regular_32 = SpoqaRegular.copy(
        fontSize = 32.sp,
    ),
    regular_28 = SpoqaRegular.copy(
        fontSize = 28.sp,
    ),
    regular_24 = SpoqaRegular.copy(
        fontSize = 24.sp,
    ),
    regular_20 = SpoqaRegular.copy(
        fontSize = 20.sp,
    ),
    regular_16 = SpoqaRegular.copy(
        fontSize = 16.sp,
    ),
    regular_12 = SpoqaRegular.copy(
        fontSize = 12.sp,
    ),
    regular_08 = SpoqaRegular.copy(
        fontSize = 8.sp,
    ),
)

@Immutable
data class TodoTypography(
    val bold_32: TextStyle,
    val bold_28: TextStyle,
    val bold_24: TextStyle,
    val bold_20: TextStyle,
    val bold_16: TextStyle,
    val bold_12: TextStyle,
    val bold_08: TextStyle,
    val medium_32: TextStyle,
    val medium_28: TextStyle,
    val medium_24: TextStyle,
    val medium_20: TextStyle,
    val medium_16: TextStyle,
    val medium_12: TextStyle,
    val medium_08: TextStyle,
    val regular_32: TextStyle,
    val regular_28: TextStyle,
    val regular_24: TextStyle,
    val regular_20: TextStyle,
    val regular_16: TextStyle,
    val regular_12: TextStyle,
    val regular_08: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    TodoTypography(
        bold_32 = SpoqaBold,
        bold_28 = SpoqaBold,
        bold_24 = SpoqaBold,
        bold_20 = SpoqaBold,
        bold_16 = SpoqaBold,
        bold_12 = SpoqaBold,
        bold_08 = SpoqaBold,
        medium_32 = SpoqaMedium,
        medium_28 = SpoqaMedium,
        medium_24 = SpoqaMedium,
        medium_20 = SpoqaMedium,
        medium_16 = SpoqaMedium,
        medium_12 = SpoqaMedium,
        medium_08 = SpoqaMedium,
        regular_32 = SpoqaRegular,
        regular_28 = SpoqaRegular,
        regular_24 = SpoqaRegular,
        regular_20 = SpoqaRegular,
        regular_16 = SpoqaRegular,
        regular_12 = SpoqaRegular,
        regular_08 = SpoqaRegular,
    )
}
