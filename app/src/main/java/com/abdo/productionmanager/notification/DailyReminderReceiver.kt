package com.abdo.productionmanager.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.sendNotification(
            context,
            NotificationHelper.CHANNEL_DAILY,
            NotificationHelper.NOTIF_ID_DAILY,
            "وقت تسجيل الإنتاج! 🏭",
            "لا تنسى تسجل إنتاج اليوم"
        )
    }
}
