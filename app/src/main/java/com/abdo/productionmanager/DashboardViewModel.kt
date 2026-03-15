package com.abdo.productionmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdo.core.domain.model.DailyProduction
import com.abdo.core.domain.model.Task
import com.abdo.core.domain.model.TaskStatus
import com.abdo.core.domain.usecase.GetDailyProductionUseCase
import com.abdo.core.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class DashboardUiState(
    val isLoading: Boolean = true,
    val dailyProduction: DailyProduction? = null,
    val pendingTasks: List<Task> = emptyList(),
    val todayDate: LocalDate = LocalDate.now()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDailyProductionUseCase: GetDailyProductionUseCase,
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            combine(
                getDailyProductionUseCase(LocalDate.now()),
                getTasksUseCase(activeOnly = true)
            ) { daily, tasks ->
                DashboardUiState(
                    isLoading = false,
                    dailyProduction = daily,
                    pendingTasks = tasks.filter {
                        it.status != TaskStatus.DONE
                    }.take(3)
                )
            }.collect { state ->
                _uiState.update { state }
            }
        }
    }
}
