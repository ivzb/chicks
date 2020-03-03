package com.ivzb.chicks.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.R
import com.ivzb.chicks.domain.Event
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.domain.Result.Loading
import com.ivzb.chicks.domain.announcements.LoadAnnouncementsUseCase
import com.ivzb.chicks.domain.links.FetchLinksUseCase
import com.ivzb.chicks.domain.links.ObserveLinksUseCase
import com.ivzb.chicks.domain.successOr
import com.ivzb.chicks.model.Announcement
import com.ivzb.chicks.model.Link
import com.ivzb.chicks.ui.SectionHeader
import com.ivzb.chicks.util.SnackbarMessage
import com.ivzb.chicks.util.combine
import com.ivzb.chicks.util.map
import javax.inject.Inject

/**
 * Loads data and exposes it to the view.
 * By annotating the constructor with [@Inject], Dagger will use that constructor when needing to
 * create the object, so defining a [@Provides] method for this class won't be needed.
 */
class FeedViewModel @Inject constructor(
    loadAnnouncementsUseCase: LoadAnnouncementsUseCase,
    private val observeLinksUseCase: ObserveLinksUseCase,
    private val fetchLinksUseCase: FetchLinksUseCase
) : ViewModel(), EventActions {

    val feed: LiveData<List<Any>>

    val searchVisible: LiveData<Event<Boolean>>

    val errorMessage: LiveData<Event<String>>

    val snackBarMessage: LiveData<Event<SnackbarMessage>>

    val performLinkClickEvent: MutableLiveData<Event<Link>> = MutableLiveData()

    val performLinkLongClickEvent: MutableLiveData<Event<Link>> = MutableLiveData()

    private val loadAnnouncementsResult = MutableLiveData<Result<List<Announcement>>>()

    val swipeRefreshing: LiveData<Boolean>

    private val loadLinksResult by lazy(LazyThreadSafetyMode.NONE) {
        observeLinksUseCase.observe()
    }

    init {
        loadAnnouncementsUseCase(Unit, loadAnnouncementsResult)

        val announcements: LiveData<List<Any>> = loadAnnouncementsResult.map {
            if (it is Loading) {
                listOf(LoadingIndicator)
            } else {
                it.successOr(emptyList())
            }
        }

        val links = loadLinksResult.map {
            if (it is Loading) {
                listOf(LoadingIndicator)
            } else {
                val items = it.successOr(emptyList())
                if (items.isNotEmpty()) items else listOf(LinkEmpty)
            }
        }

        // Compose feed
        feed = announcements.combine(links) { announcementItems, linkItems ->

            val feedItems = mutableListOf<Any>()

            if (announcementItems.isNotEmpty() && !(linkItems[0] is Link)) {
//                feedItems.add(SectionHeader(R.string.feed_announcements_title))
                feedItems.addAll(announcementItems)
            }

            feedItems.plus(SectionHeader(R.string.feed_links_title))
                .plus(linkItems)

        }

        swipeRefreshing = loadLinksResult.map {
            // Whenever refresh finishes, stop the indicator, whatever the result it
            false
        }

        errorMessage = loadLinksResult.map {
            Event(content = (it as? Result.Error)?.exception?.message ?: "")
        }

        // Show an error message if the feed could not be loaded.
        snackBarMessage = MediatorLiveData()
        snackBarMessage.addSource(loadLinksResult) {
            if (it is Result.Error) {
                snackBarMessage.value =
                    Event(
                        SnackbarMessage(
                            messageId = R.string.feed_loading_error,
                            longDuration = true
                        )
                    )
            }
        }

        searchVisible = loadLinksResult.map {
            Event(content = (it as? Result.Success)?.data?.isNotEmpty() ?: false)
        }

        // Observe updates for links
        observeLinksUseCase.execute(Unit)

        fetchLinks(0)
    }

    override fun click(link: Link) {
        performLinkClickEvent.postValue(Event(link))
    }

    override fun longClick(link: Link) {
        performLinkLongClickEvent.postValue(Event(link))
    }

    fun fetchLinks(page: Int) {
        fetchLinksUseCase(page)
    }

    fun onSwipeRefresh() {
        fetchLinksUseCase(0)
    }
}
