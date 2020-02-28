package com.ivzb.chicks.fcm

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.google.firebase.messaging.RemoteMessage
import com.ivzb.chicks.data.job.LinksDataService
import java.util.concurrent.TimeUnit

/**
 * Receives Firebase Cloud Messages and starts a [LinksDataService] to download new data.
 */
class ChicksFirebaseMessagingService : DaggerFirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data[TRIGGER_EVENT_DATA_SYNC_key] == TRIGGER_EVENT_DATA_SYNC) {
            // Schedule job on JobScheduler when FCM message with action `TRIGGER_EVENT_DATA_SYNC`
            // is received.
            scheduleFetchEventData()
        }
    }

    private fun scheduleFetchEventData() {
        val serviceComponent = ComponentName(this, LinksDataService::class.java)
        val builder = JobInfo.Builder(LinksDataService.JOB_ID, serviceComponent)
            .setMinimumLatency(MINIMUM_LATENCY) // wait at least
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // Unmetered if possible
            .setOverrideDeadline(OVERRIDE_DEADLINE) // run by deadline if conditions not met

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        jobScheduler.schedule(builder.build())
    }

    companion object {
        private const val TRIGGER_EVENT_DATA_SYNC = "SYNC_EVENT_DATA"
        private const val TRIGGER_EVENT_DATA_SYNC_key = "action"

        // Some latency to avoid load spikes
        private val MINIMUM_LATENCY = TimeUnit.SECONDS.toMillis(5)

        // Job scheduled to run only with Wi-Fi but with a deadline
        private val OVERRIDE_DEADLINE = TimeUnit.MINUTES.toMillis(15)
    }
}
