package com.abdo.productionmanager.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TasksReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.sendNotification(
            context,
            NotificationHelper.CHANNEL_TASKS,
            NotificationHelper.NOTIF_ID_TASKS,
            "عندك مهام متأخرة! ✅",
            "افتح التطبيق وخلص مهامك"
        )
    }
}
