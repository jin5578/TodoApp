package com.example.main

import androidx.lifecycle.ViewModel
import com.example.domain.GetThemeTypeUseCase
import com.example.model.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getThemeTypeUseCase: GetThemeTypeUseCase,
) : ViewModel() {
    val themeType: Flow<ThemeType> = getThemeTypeUseCase()
}
