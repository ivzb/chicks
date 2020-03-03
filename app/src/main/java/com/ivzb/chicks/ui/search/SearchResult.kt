package com.ivzb.chicks.ui.search

data class SearchResult(
    val id: Long,
    val url: String,
    val title: String?,
    val imageUrl: String?,
    val timestamp: Long?,
    val type: SearchResultType
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchResult

        if (id != other.id) return false
        if (url != other.url) return false
        if (title != other.title) return false
        if (imageUrl != other.imageUrl) return false
        if (timestamp != other.timestamp) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (timestamp?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        return result
    }
}

enum class SearchResultType {
    LINK
}
