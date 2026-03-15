package com.abdo.feature.tasks

import com.abdo.core.domain.model.Task
import com.abdo.core.domain.model.TaskPriority
import com.abdo.core.domain.model.TaskStatus

data class TaskUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val errorMessage: String? = null,
    val successMessage: String? = null
)

sealed class TaskEvent {
    data class AddTask(
        val title: String,
        val description: String = "",
        val priority: TaskPriority = TaskPriority.MEDIUM
    ) : TaskEvent()

    data class UpdateStatus(
        val id: Int,
        val status: TaskStatus
    ) : TaskEvent()

    data class DeleteTask(
        val task: Task
    ) : TaskEvent()

    object ClearMessages : TaskEvent()
}
