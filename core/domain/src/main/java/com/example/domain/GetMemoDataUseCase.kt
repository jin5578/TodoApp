package com.example.domain

import androidx.work.WorkContinuation.combine
import androidx.work.multiprocess.RemoteWorkContinuation.combine
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.memo.Memo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMemoDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository
) {
    operator fun invoke(id: Long): Flow<Memo> =
        combine(
            flow = systemRepository.getMemoSystem(),
            flow2 = taskRepository.getFlowTaskById(id = id)
        ) { memoSystem, task ->
            Memo(
                memoTitle = task.memoTitle,
                memoContent = task.memoContent,
                memoUpdatedAt = task.memoUpdatedAt,
                memoSystem = memoSystem
            )
        }
}
