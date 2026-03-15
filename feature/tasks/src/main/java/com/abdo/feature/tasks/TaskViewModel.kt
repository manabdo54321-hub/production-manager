package com.abdo.feature.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdo.core.domain.model.Task
import com.abdo.core.domain.model.TaskPriority
import com.abdo.core.domain.usecase.AddTaskUseCase
import com.abdo.core.domain.usecase.GetTasksUseCase
import com.abdo.core.domain.usecase.UpdateTaskStatusUseCase
import com.abdo.core.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.AddTask -> addTask(event)
            is TaskEvent.UpdateStatus -> updateStatus(event)
            is TaskEvent.DeleteTask -> deleteTask(event.task)
            is TaskEvent.ClearMessages -> {
                _uiState.update { it.copy(errorMessage = null, successMessage = null) }
            }
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { tasks ->
                _uiState.update { it.copy(tasks = tasks) }
            }
        }
    }

    private fun addTask(event: TaskEvent.AddTask) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val task = Task(
                title = event.title,
                description = event.description,
                priority = event.priority
            )
            addTaskUseCase(task)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successMessage = "تم إضافة المهمة بنجاح ✓"
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

    private fun updateStatus(event: TaskEvent.UpdateStatus) {
        viewModelScope.launch {
            updateTaskStatusUseCase(event.id, event.status)
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}
