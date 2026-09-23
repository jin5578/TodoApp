package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.model.TimePickerType
import javax.inject.Inject

class UpdateTimePickerTypeUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    suspend operator fun invoke(timePickerType: TimePickerType) = systemRepository.updateTimePickerType(timePickerType = timePickerType)
}
