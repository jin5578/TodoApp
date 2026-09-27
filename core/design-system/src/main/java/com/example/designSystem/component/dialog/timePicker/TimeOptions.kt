package com.example.designSystem.component.dialog.timePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.designSystem.R
import com.example.model.TimeOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Composable
internal fun rememberTimeOptions(taskDate: LocalDate): ImmutableList<TimeOption> = remember(taskDate) {
    persistentListOf(
        TimeOption(
            resId = R.string.no_time,
            time = null,
        ),
        TimeOption(
            resId = R.string.time_00,
            time = taskDate.atTime(0, 0),
        ),
        TimeOption(
            resId = R.string.time_02,
            time = taskDate.atTime(2, 0),
        ),
        TimeOption(
            resId = R.string.time_04,
            time = taskDate.atTime(4, 0),
        ),
        TimeOption(
            resId = R.string.time_06,
            time = taskDate.atTime(6, 0),
        ),
        TimeOption(
            resId = R.string.time_08,
            time = taskDate.atTime(8, 0),
        ),
        TimeOption(
            resId = R.string.time_10,
            time = taskDate.atTime(10, 0),
        ),
        TimeOption(
            resId = R.string.time_12,
            time = taskDate.atTime(12, 0),
        ),
        TimeOption(
            resId = R.string.time_14,
            time = taskDate.atTime(14, 0),
        ),
        TimeOption(
            resId = R.string.time_16,
            time = taskDate.atTime(16, 0),
        ),
        TimeOption(
            resId = R.string.time_18,
            time = taskDate.atTime(18, 0),
        ),
        TimeOption(
            resId = R.string.time_20,
            time = taskDate.atTime(20, 0),
        ),
        TimeOption(
            resId = R.string.time_22,
            time = taskDate.atTime(22, 0),
        ),
    )
}
