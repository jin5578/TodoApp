package com.example.data_api.repository

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
}