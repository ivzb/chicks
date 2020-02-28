package com.ivzb.chicks.data.job

import android.app.job.JobParameters
import com.ivzb.chicks.domain.internal.DefaultScheduler
import com.ivzb.chicks.domain.links.RefreshLinksUseCase
import com.ivzb.chicks.domain.succeeded
import javax.inject.Inject

/**
 * A Job that refreshes the conference data in the repository (if the app is active) and
 * in the cache (if the app is not active).
 */
class LinksDataService : DaggerJobService() {

    @Inject
    lateinit var refreshEventDataUseCase: RefreshLinksUseCase

    override fun onStartJob(params: JobParameters?): Boolean {
        // Execute off the main thread
        DefaultScheduler.execute {

            val result = refreshEventDataUseCase.executeNow(Unit)

            when {
                result.succeeded -> {
                    // Finishing indicating this job doesn't need to be rescheduled.
                    jobFinished(params, false)
                }
                else -> {
                    // Indicating job shold be rescheduled
                    jobFinished(params, true)
                }
            }
        }
        // Returning true to indicate we're not done yet (execution still running in the background)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        // Return true to indicate this job should run again.
        return true
    }

    companion object {
        const val JOB_ID = 0xFE0FE0
    }
}
