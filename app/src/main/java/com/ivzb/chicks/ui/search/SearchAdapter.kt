package com.ivzb.chicks.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivzb.chicks.databinding.ItemSearchResultBinding

class SearchAdapter(
    private val searchViewModel: SearchViewModel
) : ListAdapter<SearchResult, SearchViewHolder>(SearchDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            searchViewModel
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SearchViewHolder(
    private val binding: ItemSearchResultBinding,
    private val searchViewModel: SearchViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(searchResult: SearchResult) {
        binding.searchResult = searchResult
        binding.executePendingBindings()

        binding.cvLink.setOnClickListener {
            searchViewModel.clickSearchResult(searchResult)
        }

        binding.cvLink.setOnLongClickListener {
            searchViewModel.longClickSearchResult(searchResult)
            true
        }
    }
}

object SearchDiff : DiffUtil.ItemCallback<SearchResult>() {

    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult) =
        oldItem == newItem
}
