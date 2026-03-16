package com.abdo.productionmanager.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.abdo.productionmanager.MainActivity
import java.util.Calendar

object NotificationHelper {

    const val CHANNEL_DAILY = "daily_reminder"
    const val CHANNEL_SUMMARY = "summary_reminder"
    const val CHANNEL_TASKS = "tasks_reminder"

    const val NOTIF_ID_DAILY = 1001
    const val NOTIF_ID_SUMMARY = 1002
    const val NOTIF_ID_TASKS = 1003

    fun createChannels(context: Context) {
        val manager = context.getSystemService(NotificationManager::class.java)

        listOf(
            NotificationChannel(
                CHANNEL_DAILY,
                "تذكير يومي",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "تذكير بتسجيل الإنتاج اليومي" },

            NotificationChannel(
                CHANNEL_SUMMARY,
                "ملخص مسائي",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "ملخص إنتاج اليوم" },

            NotificationChannel(
                CHANNEL_TASKS,
                "تذكير المهام",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "تذكير بالمهام المتأخرة" }
        ).forEach { manager.createNotificationChannel(it) }
    }

    fun scheduleDaily(context: Context, hour: Int, minute: Int) {
        schedule(
            context,
            hour, minute,
            DailyReminderReceiver::class.java,
            NOTIF_ID_DAILY
        )
    }

    fun scheduleSummary(context: Context, hour: Int, minute: Int) {
        schedule(
            context,
            hour, minute,
            SummaryReminderReceiver::class.java,
            NOTIF_ID_SUMMARY
        )
    }

    fun scheduleTasksReminder(context: Context) {
        schedule(
            context,
            9, 0,
            TasksReminderReceiver::class.java,
            NOTIF_ID_TASKS
        )
    }

    fun cancelNotification(context: Context, requestCode: Int, cls: Class<*>) {
        val intent = PendingIntent.getBroadcast(
            context, requestCode,
            Intent(context, cls),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        context.getSystemService(AlarmManager::class.java).cancel(intent)
    }

    private fun schedule(
        context: Context,
        hour: Int,
        minute: Int,
        cls: Class<*>,
        requestCode: Int
    ) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val intent = PendingIntent.getBroadcast(
            context, requestCode,
            Intent(context, cls),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        context.getSystemService(AlarmManager::class.java)
            .setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                intent
            )
    }

    fun sendNotification(
        context: Context,
        channelId: String,
        notifId: Int,
        title: String,
        message: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        context.getSystemService(NotificationManager::class.java)
            .notify(notifId, notification)
    }
}
