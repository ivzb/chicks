package com.ivzb.chicks.domain.settings

import com.ivzb.chicks.data.prefs.PreferenceStorage
import com.ivzb.chicks.domain.UseCase
import javax.inject.Inject

open class GetNSFWUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Unit, Boolean>() {

    override fun execute(parameters: Unit) = preferenceStorage.isNSFWEnabled
}
