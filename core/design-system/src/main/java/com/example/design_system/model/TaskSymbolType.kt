package com.example.design_system.model

import androidx.compose.ui.graphics.Color
import com.example.design_system.R
import com.example.design_system.theme.flagColors
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal enum class TaskSymbolType(
    val titleResId: Int,
    val taskSymbols: ImmutableList<TaskSymbol>,
) {
    FLAG(
        titleResId = R.string.flag,
        taskSymbols = persistentListOf(
            TaskSymbol(
                id = 0,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_flag,
                    iconColor = flagColors[0],
                ),
            ),
            TaskSymbol(
                id = 1,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_flag,
                    iconColor = flagColors[1],
                ),
            ),
            TaskSymbol(
                id = 2,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_flag,
                    iconColor = flagColors[2],
                ),
            ),
            TaskSymbol(
                id = 3,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_flag,
                    iconColor = flagColors[3],
                ),
            ),
            TaskSymbol(
                id = 4,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_flag,
                    iconColor = flagColors[4],
                ),
            ),
        ),
    ),
    NUMBER(
        titleResId = R.string.number,
        taskSymbols = persistentListOf(
            TaskSymbol(
                id = 5,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_circle_one,
                    iconColor = flagColors[0],
                ),
            ),
            TaskSymbol(
                id = 6,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_circle_two,
                    iconColor = flagColors[1],
                ),
            ),
            TaskSymbol(
                id = 7,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_circle_three,
                    iconColor = flagColors[2],
                ),
            ),
            TaskSymbol(
                id = 8,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_circle_four,
                    iconColor = flagColors[3],
                ),
            ),
            TaskSymbol(
                id = 9,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_circle_five,
                    iconColor = flagColors[4],
                ),
            ),
        ),
    ),
    PROGRESS(
        titleResId = R.string.progress,
        taskSymbols = persistentListOf(
            TaskSymbol(
                id = 10,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_percent_20,
                    iconColor = flagColors[0],
                ),
            ),
            TaskSymbol(
                id = 11,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_percent_40,
                    iconColor = flagColors[1],
                ),
            ),
            TaskSymbol(
                id = 12,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_percent_60,
                    iconColor = flagColors[2],
                ),
            ),
            TaskSymbol(
                id = 13,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_percent_80,
                    iconColor = flagColors[3],
                ),
            ),
            TaskSymbol(
                id = 14,
                symbolIcon = TaskSymbolIcon(
                    iconResId = R.drawable.svg_percent_100,
                    iconColor = flagColors[4],
                ),
            ),
        ),
    ),
    MOOD(
        titleResId = R.string.mood,
        taskSymbols = persistentListOf(
            TaskSymbol(
                id = 15,
                emojiIcon = "😀",
            ),
            TaskSymbol(
                id = 16,
                emojiIcon = "😊",
            ),
            TaskSymbol(
                id = 17,
                emojiIcon = "😐",
            ),
            TaskSymbol(
                id = 18,
                emojiIcon = "😔",
            ),
            TaskSymbol(
                id = 19,
                emojiIcon = "😖",
            ),
        ),
    ),
}

internal data class TaskSymbol(
    val id: Int,
    val symbolIcon: TaskSymbolIcon? = null,
    val emojiIcon: String? = null,
)

internal data class TaskSymbolIcon(
    val iconResId: Int,
    val iconColor: Color,
)
