package com.ivzb.chicks.domain.settings

import com.ivzb.chicks.data.prefs.PreferenceStorage
import com.ivzb.chicks.domain.UseCase
import javax.inject.Inject

open class SaveNSFWUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Boolean, Boolean>() {

    override fun execute(parameters: Boolean): Boolean {
        preferenceStorage.isNSFWEnabled = parameters
        return preferenceStorage.isNSFWEnabled
    }
}
