package com.abdo.productionmanager

import android.app.Application
import com.abdo.productionmanager.notification.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductionManagerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannels(this)
    }
}
