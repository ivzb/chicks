package com.ivzb.chicks.ui.search

data class SearchResult(
    val url: String,
    val title: String?,
    val imageUrl: String?,
    val timestamp: Long?,
    val type: SearchResultType
)

enum class SearchResultType {
    LINK
}
