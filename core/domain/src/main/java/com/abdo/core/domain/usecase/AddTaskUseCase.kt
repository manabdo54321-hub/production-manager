package com.abdo.core.domain.usecase

import com.abdo.core.domain.model.Task
import com.abdo.core.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<Long> {
        if (task.title.isBlank())
            return Result.failure(Exception("عنوان المهمة مطلوب"))
        return Result.success(repository.addTask(task))
    }
}
