package com.ivzb.chicks.ui.search

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["searchResultItems", "searchViewModel"], requireAll = true)
fun searchResultItems(
    recyclerView: RecyclerView,
    list: List<SearchResult>?,
    searchViewModel: SearchViewModel
) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = SearchAdapter(searchViewModel)
    }

    if (list.isNullOrEmpty()) {
        recyclerView.isVisible = false
    } else {
        recyclerView.isVisible = true
        (recyclerView.adapter as SearchAdapter).submitList(list)
    }
}
