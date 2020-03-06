package com.ivzb.chicks.ui.nsfw

import androidx.lifecycle.LiveData
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.domain.settings.GetNSFWUseCase
import com.ivzb.chicks.domain.settings.ObserveNSFUseCase
import com.ivzb.chicks.model.Theme
import com.ivzb.chicks.util.map
import javax.inject.Inject

/**
 * Interface to implement activity nsfw via a ViewModel.
 *
 * You can inject a implementation of this via Dagger2, then use the implementation as an interface
 * delegate to add the functionality without writing any code
 *
 * Example usage:
 * ```
 * class MyViewModel @Inject constructor(
 *     nsfwActivityDelegate: NSFWActivityDelegate
 * ) : ViewModel(), NSFWActivityDelegate by nsfwActivityDelegate {
 * ```
 */
interface NSFWActivityDelegate {

    /**
     * Allows observing of the current NSFW setting
     */
    val isNSFW: LiveData<Boolean>

    /**
     * Allows querying of the current NSFW setting synchronously
     */
    val currentNSFW: Boolean
}

class NSFWActivityDelegateImpl @Inject constructor(
    private val observeNSFWUseCase: ObserveNSFUseCase,
    private val getNSFWUseCase: GetNSFWUseCase
) : NSFWActivityDelegate {

    override val isNSFW: LiveData<Boolean> by lazy(LazyThreadSafetyMode.NONE) {
        observeNSFWUseCase.observe().map {
            if (it is Result.Success) it.data else false
        }
    }

    override val currentNSFW: Boolean
        get() = getNSFWUseCase.executeNow(Unit).let{
            if (it is Result.Success) it.data else false
        }

    init {
        observeNSFWUseCase.execute(Unit)
    }
}
