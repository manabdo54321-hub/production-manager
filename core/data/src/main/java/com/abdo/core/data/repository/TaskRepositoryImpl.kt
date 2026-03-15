package com.abdo.core.data.repository

import com.abdo.core.data.dao.TaskDao
import com.abdo.core.data.mapper.toDomain
import com.abdo.core.data.mapper.toEntity
import com.abdo.core.domain.model.Task
import com.abdo.core.domain.model.TaskStatus
import com.abdo.core.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { list -> list.map { it.toDomain() } }

    override fun getActiveTasks(): Flow<List<Task>> =
        taskDao.getActiveTasks().map { list -> list.map { it.toDomain() } }

    override fun getCompletedTasks(): Flow<List<Task>> =
        taskDao.getCompletedTasks().map { list -> list.map { it.toDomain() } }

    override suspend fun getTaskById(id: Int): Task? =
        taskDao.getTaskById(id)?.toDomain()

    override suspend fun addTask(task: Task): Long =
        taskDao.insertTask(task.toEntity())

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task.toEntity())

    override suspend fun deleteTask(task: Task) =
        taskDao.deleteTask(task.toEntity())

    override suspend fun updateTaskStatus(id: Int, status: TaskStatus) =
        taskDao.updateTaskStatus(id, status.name)
}
