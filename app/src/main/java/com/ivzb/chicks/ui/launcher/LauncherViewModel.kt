package com.ivzb.chicks.ui.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.domain.Event
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.domain.prefs.OnboardingCompletedUseCase
import com.ivzb.chicks.util.map
import javax.inject.Inject

/**
 * Logic for determining which screen to send users to on app launch.
 */
class LaunchViewModel @Inject constructor(
    onboardingCompletedUseCase: OnboardingCompletedUseCase
) : ViewModel() {

    private val onboardingCompletedResult = MutableLiveData<Result<Boolean>>()

    val launchDestination: LiveData<Event<LaunchDestination>>

    init {
        launchDestination = onboardingCompletedResult.map {
            // If this check fails, prefer to launch main activity than show onboarding too often
            if ((it as? Result.Success)?.data == false) {
                Event(LaunchDestination.ONBOARDING)
            } else {
                Event(LaunchDestination.MAIN_ACTIVITY)
            }
        }

        // Check if onboarding has already been completed and then navigate the user accordingly
        onboardingCompletedUseCase(Unit, onboardingCompletedResult)
    }
}

enum class LaunchDestination {
    ONBOARDING,
    MAIN_ACTIVITY
}
