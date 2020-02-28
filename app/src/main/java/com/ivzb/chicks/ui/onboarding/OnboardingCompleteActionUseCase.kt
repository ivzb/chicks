package com.ivzb.chicks.ui.onboarding

import com.ivzb.chicks.data.prefs.PreferenceStorage
import com.ivzb.chicks.domain.UseCase
import javax.inject.Inject

/**
 * Records whether onboarding has been completed.
 */
open class OnboardingCompleteActionUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Boolean, Unit>() {

    override fun execute(parameters: Boolean) {
        preferenceStorage.onboardingCompleted = parameters
    }
}
