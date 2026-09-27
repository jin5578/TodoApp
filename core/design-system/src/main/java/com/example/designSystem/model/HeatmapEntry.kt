package com.example.designSystem.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class HeatmapEntry(
    val date: LocalDate,
    val level: Int,
)
