package com.ivzb.chicks.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import dagger.android.AndroidInjection

/**
 * A [FirebaseMessagingService] that injects into [AndroidInjection].
 */
open class DaggerFirebaseMessagingService : FirebaseMessagingService() {
    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }
}
