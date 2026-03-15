package com.abdo.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val priority: String = "MEDIUM",
    val status: String = "PENDING",
    val dueDate: LocalDate? = null,
    val createdAt: LocalDate = LocalDate.now()
)
