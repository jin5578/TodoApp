package com.example.setting.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class CategoryItemUiState(
    @param:StringRes val titleResId: Int,
    @param:DrawableRes val iconResId: Int,
    val onClick: () -> Unit,
)
