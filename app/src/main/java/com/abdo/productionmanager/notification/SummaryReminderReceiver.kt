package com.abdo.productionmanager.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SummaryReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.sendNotification(
            context,
            NotificationHelper.CHANNEL_SUMMARY,
            NotificationHelper.NOTIF_ID_SUMMARY,
            "ملخص إنتاج اليوم 📊",
            "افتح التطبيق لتشوف إنجازاتك اليوم"
        )
    }
}
