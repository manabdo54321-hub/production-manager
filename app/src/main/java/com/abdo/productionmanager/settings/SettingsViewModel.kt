package com.abdo.productionmanager.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdo.core.data.datastore.AppSettings
import com.abdo.productionmanager.notification.NotificationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val userName: String = "عبده",
    val userJob: String = "صانع سنجر",
    val themeId: Int = 0,
    val darkMode: Boolean = false,
    val notifDailyEnabled: Boolean = false,
    val notifDailyHour: Int = 8,
    val notifDailyMinute: Int = 0,
    val notifSummaryEnabled: Boolean = false,
    val notifSummaryHour: Int = 20,
    val notifSummaryMinute: Int = 0,
    val notifTasksEnabled: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettings: AppSettings,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        appSettings.userName,
        appSettings.userJob,
        appSettings.themeId,
        appSettings.darkMode,
        appSettings.notifDailyEnabled,
        appSettings.notifDailyHour,
        appSettings.notifDailyMinute,
        appSettings.notifSummaryEnabled,
        appSettings.notifSummaryHour,
        appSettings.notifSummaryMinute,
        appSettings.notifTasksEnabled
    ) { values ->
        SettingsUiState(
            userName = values[0] as String,
            userJob = values[1] as String,
            themeId = values[2] as Int,
            darkMode = values[3] as Boolean,
            notifDailyEnabled = values[4] as Boolean,
            notifDailyHour = values[5] as Int,
            notifDailyMinute = values[6] as Int,
            notifSummaryEnabled = values[7] as Boolean,
            notifSummaryHour = values[8] as Int,
            notifSummaryMinute = values[9] as Int,
            notifTasksEnabled = values[10] as Boolean
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SettingsUiState()
    )

    fun setUserName(value: String) = viewModelScope.launch {
        appSettings.setUserName(value)
    }
    fun setUserJob(value: String) = viewModelScope.launch {
        appSettings.setUserJob(value)
    }
    fun setTheme(id: Int) = viewModelScope.launch {
        appSettings.setThemeId(id)
    }
    fun setDarkMode(value: Boolean) = viewModelScope.launch {
        appSettings.setDarkMode(value)
    }
    fun setNotifDaily(enabled: Boolean, hour: Int, minute: Int) = viewModelScope.launch {
        appSettings.setNotifDailyEnabled(enabled)
        appSettings.setNotifDailyTime(hour, minute)
        if (enabled)
            NotificationHelper.scheduleDaily(context, hour, minute)
        else
            NotificationHelper.cancelNotification(
                context,
                NotificationHelper.NOTIF_ID_DAILY,
                com.abdo.productionmanager.notification.DailyReminderReceiver::class.java
            )
    }
    fun setNotifSummary(enabled: Boolean, hour: Int, minute: Int) = viewModelScope.launch {
        appSettings.setNotifSummaryEnabled(enabled)
        appSettings.setNotifSummaryTime(hour, minute)
        if (enabled)
            NotificationHelper.scheduleSummary(context, hour, minute)
        else
            NotificationHelper.cancelNotification(
                context,
                NotificationHelper.NOTIF_ID_SUMMARY,
                com.abdo.productionmanager.notification.SummaryReminderReceiver::class.java
            )
    }
    fun setNotifTasks(enabled: Boolean) = viewModelScope.launch {
        appSettings.setNotifTasksEnabled(enabled)
        if (enabled)
            NotificationHelper.scheduleTasksReminder(context)
        else
            NotificationHelper.cancelNotification(
                context,
                NotificationHelper.NOTIF_ID_TASKS,
                com.abdo.productionmanager.notification.TasksReminderReceiver::class.java
            )
    }
}
