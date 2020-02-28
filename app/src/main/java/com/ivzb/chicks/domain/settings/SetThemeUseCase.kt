package com.ivzb.chicks.domain.settings

import com.ivzb.chicks.data.prefs.PreferenceStorage
import com.ivzb.chicks.domain.UseCase
import com.ivzb.chicks.model.Theme
import javax.inject.Inject

open class SetThemeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Theme, Unit>() {

    override fun execute(parameters: Theme) {
        preferenceStorage.selectedTheme = parameters.storageKey
    }
}
