package com.abdo.productionmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdo.core.data.datastore.AppSettings
import com.abdo.core.domain.usecase.GetDailyProductionUseCase
import com.abdo.core.domain.usecase.GetTasksUseCase
import com.abdo.core.domain.model.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val getDailyProductionUseCase: GetDailyProductionUseCase,
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val userName = appSettings.userName
    val userJob = appSettings.userJob
    val themeId = appSettings.themeId
    val darkMode = appSettings.darkMode

    val todayProduction: StateFlow<Int> = getDailyProductionUseCase(LocalDate.now())
        .map { it.totalQuantity }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val pendingTasksCount: StateFlow<Int> = getTasksUseCase()
        .map { tasks -> tasks.count { it.status != TaskStatus.DONE } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun setTheme(id: Int) = viewModelScope.launch {
        appSettings.setThemeId(id)
    }
    fun setDarkMode(value: Boolean) = viewModelScope.launch {
        appSettings.setDarkMode(value)
    }
}
