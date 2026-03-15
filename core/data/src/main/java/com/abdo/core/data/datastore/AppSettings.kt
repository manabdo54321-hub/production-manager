package com.abdo.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

@Singleton
class AppSettings @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_JOB = stringPreferencesKey("user_job")
        val THEME_ID = intPreferencesKey("theme_id")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val NOTIF_DAILY_ENABLED = booleanPreferencesKey("notif_daily_enabled")
        val NOTIF_DAILY_HOUR = intPreferencesKey("notif_daily_hour")
        val NOTIF_DAILY_MINUTE = intPreferencesKey("notif_daily_minute")
        val NOTIF_SUMMARY_ENABLED = booleanPreferencesKey("notif_summary_enabled")
        val NOTIF_SUMMARY_HOUR = intPreferencesKey("notif_summary_hour")
        val NOTIF_SUMMARY_MINUTE = intPreferencesKey("notif_summary_minute")
        val NOTIF_TASKS_ENABLED = booleanPreferencesKey("notif_tasks_enabled")
    }

    val userName: Flow<String> = context.dataStore.data.map {
        it[USER_NAME] ?: "عبده"
    }
    val userJob: Flow<String> = context.dataStore.data.map {
        it[USER_JOB] ?: "صانع سنجر"
    }
    val themeId: Flow<Int> = context.dataStore.data.map {
        it[THEME_ID] ?: 0
    }
    val darkMode: Flow<Boolean> = context.dataStore.data.map {
        it[DARK_MODE] ?: false
    }
    val notifDailyEnabled: Flow<Boolean> = context.dataStore.data.map {
        it[NOTIF_DAILY_ENABLED] ?: false
    }
    val notifDailyHour: Flow<Int> = context.dataStore.data.map {
        it[NOTIF_DAILY_HOUR] ?: 8
    }
    val notifDailyMinute: Flow<Int> = context.dataStore.data.map {
        it[NOTIF_DAILY_MINUTE] ?: 0
    }
    val notifSummaryEnabled: Flow<Boolean> = context.dataStore.data.map {
        it[NOTIF_SUMMARY_ENABLED] ?: false
    }
    val notifSummaryHour: Flow<Int> = context.dataStore.data.map {
        it[NOTIF_SUMMARY_HOUR] ?: 20
    }
    val notifSummaryMinute: Flow<Int> = context.dataStore.data.map {
        it[NOTIF_SUMMARY_MINUTE] ?: 0
    }
    val notifTasksEnabled: Flow<Boolean> = context.dataStore.data.map {
        it[NOTIF_TASKS_ENABLED] ?: false
    }

    suspend fun setUserName(value: String) = context.dataStore.edit {
        it[USER_NAME] = value
    }
    suspend fun setUserJob(value: String) = context.dataStore.edit {
        it[USER_JOB] = value
    }
    suspend fun setThemeId(value: Int) = context.dataStore.edit {
        it[THEME_ID] = value
    }
    suspend fun setDarkMode(value: Boolean) = context.dataStore.edit {
        it[DARK_MODE] = value
    }
    suspend fun setNotifDailyEnabled(value: Boolean) = context.dataStore.edit {
        it[NOTIF_DAILY_ENABLED] = value
    }
    suspend fun setNotifDailyTime(hour: Int, minute: Int) = context.dataStore.edit {
        it[NOTIF_DAILY_HOUR] = hour
        it[NOTIF_DAILY_MINUTE] = minute
    }
    suspend fun setNotifSummaryEnabled(value: Boolean) = context.dataStore.edit {
        it[NOTIF_SUMMARY_ENABLED] = value
    }
    suspend fun setNotifSummaryTime(hour: Int, minute: Int) = context.dataStore.edit {
        it[NOTIF_SUMMARY_HOUR] = hour
        it[NOTIF_SUMMARY_MINUTE] = minute
    }
    suspend fun setNotifTasksEnabled(value: Boolean) = context.dataStore.edit {
        it[NOTIF_TASKS_ENABLED] = value
    }
}
