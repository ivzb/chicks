package com.ivzb.chicks.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.domain.Event
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.domain.search.ObserveSearchUseCase
import com.ivzb.chicks.domain.search.Searchable
import com.ivzb.chicks.domain.successOr
import com.ivzb.chicks.model.Link
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val observeSearchUseCase: ObserveSearchUseCase
) : ViewModel(), SearchResultActionHandler {

    private val loadSearchResults by lazy(LazyThreadSafetyMode.NONE) {
        observeSearchUseCase.observe()
    }

    private val _searchResults = MediatorLiveData<List<SearchResult>>()
    val searchResults: LiveData<List<SearchResult>> = _searchResults

    private val _isEmpty = MediatorLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    val performLinkClickEvent: MutableLiveData<Event<Link>> = MutableLiveData()

    val performLinkLongClickEvent: MutableLiveData<Event<Link>> = MutableLiveData()

    init {
        _searchResults.addSource(loadSearchResults) {
            val result = (it as? Result.Success)?.data ?: emptyList()
            _searchResults.value = result.map { searched ->
                when (searched) {
                    is Searchable.SearchedLink -> {
                        val link = searched.link

                        SearchResult(
                            id = link.id,
                            title = link.title,
                            subtitle= link.url,
                            imageUrl = link.imageUrl,
                            sitename = link.sitename,
                            type = SearchResultType.LINK
                        )
                    }
                }
            }
        }

        _isEmpty.addSource(loadSearchResults) {
            _isEmpty.value = it.successOr(null).isNullOrEmpty()
        }

        onSearchQueryChanged("")
    }

    override fun clickSearchResult(searchResult: SearchResult) {
        when (searchResult.type) {
            SearchResultType.LINK -> {
                performLinkClickEvent.postValue(Event(Link(
                    id = searchResult.id,
                    title = searchResult.title,
                    url = searchResult.subtitle,
                    imageUrl = searchResult.imageUrl
                )))
            }
        }
    }

    override fun longClickSearchResult(searchResult: SearchResult) {
        when (searchResult.type) {
            SearchResultType.LINK -> {
                performLinkLongClickEvent.postValue(Event(Link(
                    id = searchResult.id,
                    title = searchResult.title,
                    url = searchResult.subtitle,
                    imageUrl = searchResult.imageUrl
                )))
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        observeSearchUseCase.execute(query)
    }
}
