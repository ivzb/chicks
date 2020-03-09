package com.ivzb.chicks.ui.link

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.domain.Event
import com.ivzb.chicks.model.Link
import javax.inject.Inject

enum class LinkOptionsEvent {
    Copy, Share
}

/**
 * ViewModel for link options dialog
 */
class LinkOptionsViewModel @Inject constructor() : ViewModel() {

    val link = MutableLiveData<Link>()

    val performLinkOptionEvent: MutableLiveData<Event<LinkOptionsEvent>> = MutableLiveData()

    fun setLink(value: Link) {
        link.value = value
    }

    fun copy() {
        performLinkOptionEvent.postValue(Event(LinkOptionsEvent.Copy))
    }

    fun share() {
        performLinkOptionEvent.postValue(Event(LinkOptionsEvent.Share))
    }
}
