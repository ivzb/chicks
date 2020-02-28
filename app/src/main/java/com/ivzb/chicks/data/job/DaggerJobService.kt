package com.ivzb.chicks.data.job

import android.app.job.JobService
import dagger.android.AndroidInjection

/**
 * A [JobService] that injects into [AndroidInjection].
 */
abstract class DaggerJobService : JobService() {
    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }
}