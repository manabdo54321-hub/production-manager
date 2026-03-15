package com.abdo.core.domain.model

import java.time.LocalDate

enum class TaskPriority { LOW, MEDIUM, HIGH }
enum class TaskStatus { PENDING, IN_PROGRESS, DONE }

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val status: TaskStatus = TaskStatus.PENDING,
    val dueDate: LocalDate? = null,
    val createdAt: LocalDate = LocalDate.now()
)
