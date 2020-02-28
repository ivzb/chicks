package com.ivzb.chicks.domain.settings

import com.ivzb.chicks.data.prefs.PreferenceStorage
import com.ivzb.chicks.domain.MediatorUseCase
import com.ivzb.chicks.model.Theme
import com.ivzb.chicks.model.themeFromStorageKey
import com.ivzb.chicks.domain.Result
import javax.inject.Inject

open class ObserveThemeModeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : MediatorUseCase<Unit, Theme>() {

    override fun execute(parameters: Unit) {
        result.addSource(preferenceStorage.observableSelectedTheme) {
            result.postValue(Result.Success(themeFromStorageKey(it)))
        }
    }
}
