package com.ivzb.chicks.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.domain.Event
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.domain.search.LoadSearchUseCase
import com.ivzb.chicks.domain.search.Searchable
import com.ivzb.chicks.domain.successOr
import com.ivzb.chicks.model.Link
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val loadSearchUseCase: LoadSearchUseCase
) : ViewModel(), SearchResultActionHandler {

    private val loadSearchResult = MutableLiveData<Result<List<Searchable>>>()

    private val _searchResults = MediatorLiveData<List<SearchResult>>()
    val searchResults: LiveData<List<SearchResult>> = _searchResults

    private val _isEmpty = MediatorLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    val performLinkClickEvent: MutableLiveData<Event<Link>> = MutableLiveData()

    val performLinkLongClickEvent: MutableLiveData<Event<Link>> = MutableLiveData()

    init {
        _searchResults.addSource(loadSearchResult) {
            val result = (it as? Result.Success)?.data ?: emptyList()
            _searchResults.value = result.map { searched ->
                when (searched) {
                    is Searchable.SearchedLink -> {
                        val link = searched.link

                        SearchResult(
                            id = link.id,
                            url = link.url,
                            title = link.title,
                            imageUrl = link.imageUrl,
                            timestamp = link.timestamp,
                            type = SearchResultType.LINK
                        )
                    }
                }
            }
        }

        _isEmpty.addSource(loadSearchResult) {
            _isEmpty.value = it.successOr(null).isNullOrEmpty()
        }

        onSearchQueryChanged("")
    }

    override fun clickSearchResult(searchResult: SearchResult) {
        when (searchResult.type) {
            SearchResultType.LINK -> {
                performLinkClickEvent.postValue(
                    Event(
                        Link(
                            id = searchResult.id,
                            url = searchResult.url,
                            title = searchResult.title,
                            imageUrl = searchResult.imageUrl,
                            timestamp = searchResult.timestamp
                        )
                    )
                )
            }
        }
    }

    override fun longClickSearchResult(searchResult: SearchResult) {
        when (searchResult.type) {
            SearchResultType.LINK -> {
                performLinkLongClickEvent.postValue(
                    Event(
                        Link(
                            id = searchResult.id,
                            url = searchResult.url,
                            title = searchResult.title,
                            imageUrl = searchResult.imageUrl,
                            timestamp = searchResult.timestamp
                        )
                    )
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        loadSearchUseCase(query, loadSearchResult)
    }
}
