package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.Task
import com.abdo.core.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(activeOnly: Boolean = false): Flow<List<Task>> {
        return if (activeOnly)
            repository.getActiveTasks()
        else
            repository.getAllTasks()
    }
}
