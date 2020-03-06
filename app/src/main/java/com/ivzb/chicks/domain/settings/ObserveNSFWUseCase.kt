package com.ivzb.chicks.domain.settings

import com.ivzb.chicks.data.prefs.PreferenceStorage
import com.ivzb.chicks.domain.MediatorUseCase
import com.ivzb.chicks.domain.Result
import javax.inject.Inject

open class ObserveNSFUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : MediatorUseCase<Unit, Boolean>() {

    override fun execute(parameters: Unit) {
        result.addSource(preferenceStorage.observableNSFW) {
            result.postValue(Result.Success(it))
        }
    }
}
