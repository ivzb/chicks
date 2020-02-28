package com.ivzb.chicks.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.domain.Event
import com.ivzb.chicks.model.Link
import com.ivzb.chicks.ui.messages.SnackbarMessageManager
import com.ivzb.chicks.util.SnackbarMessage
import javax.inject.Inject
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.domain.links.ObserveLinkUseCase

/**
 * Loads [Link] data and exposes it to the link detail view.
 */
class DetailsViewModel @Inject constructor(
    private val observeLinkUseCase: ObserveLinkUseCase,
    private val snackbarMessageManager: SnackbarMessageManager
) : ViewModel() {

    private val loadLinkResult by lazy(LazyThreadSafetyMode.NONE) {
        observeLinkUseCase.observe()
    }

    private val _errorMessage = MediatorLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    private val _snackBarMessage = MediatorLiveData<Event<SnackbarMessage>>()
    val snackBarMessage: LiveData<Event<SnackbarMessage>>
        get() = _snackBarMessage

    private val _link = MediatorLiveData<Link>()
    val link: LiveData<Link>
        get() = _link

    init {
        _link.addSource(loadLinkResult) {
            (loadLinkResult.value as? Result.Success)?.data?.let {
                _link.value = it
            }
        }
    }

    fun observeLink(id: Int) {
        observeLinkUseCase.execute(id)
    }
}
