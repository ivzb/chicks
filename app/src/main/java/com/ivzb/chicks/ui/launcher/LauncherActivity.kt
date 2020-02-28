package com.ivzb.chicks.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ivzb.chicks.domain.EventObserver
import com.ivzb.chicks.ui.main.MainActivity
import com.ivzb.chicks.util.checkAllMatched
import com.ivzb.chicks.util.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * A 'Trampoline' activity for sending users to an appropriate screen on launch.
 */
class LauncherActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: LaunchViewModel = viewModelProvider(viewModelFactory)

        viewModel.launchDestination.observe(this, EventObserver { destination ->
            when (destination) {
                LaunchDestination.MAIN_ACTIVITY -> startActivity(Intent(this, MainActivity::class.java))
                LaunchDestination.ONBOARDING -> startActivity(Intent(this, MainActivity::class.java))
            }.checkAllMatched

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        })
    }
}
