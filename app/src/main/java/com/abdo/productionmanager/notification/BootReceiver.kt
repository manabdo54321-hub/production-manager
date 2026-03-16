package com.abdo.productionmanager.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // إعادة جدولة الإشعارات بعد إعادة تشغيل الموبايل
            NotificationHelper.scheduleDaily(context, 8, 0)
            NotificationHelper.scheduleSummary(context, 20, 0)
        }
    }
}
