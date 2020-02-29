package com.ivzb.chicks.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.model.Link
import javax.inject.Inject

/**
 * Loads [Link] data and exposes it to the link detail view.
 */
class DetailsViewModel @Inject constructor() : ViewModel() {

    private val _link = MediatorLiveData<Link>()
    val link: LiveData<Link>
        get() = _link

    fun setLink(link: Link) {
        _link.value = link
    }
}
