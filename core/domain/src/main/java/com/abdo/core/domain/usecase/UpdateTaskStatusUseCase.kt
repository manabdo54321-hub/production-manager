package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.TaskStatus
import com.abdo.core.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskStatusUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int, status: TaskStatus) {
        repository.updateTaskStatus(id, status)
    }
}
