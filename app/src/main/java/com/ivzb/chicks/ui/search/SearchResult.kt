package com.ivzb.chicks.ui.search

data class SearchResult(
    val id: Long,
    val imageUrl: String,
    val isNSFW: Boolean,
    val username: String?,
    val type: SearchResultType
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchResult

        if (id != other.id) return false
        if (imageUrl != other.imageUrl) return false
        if (isNSFW != other.isNSFW) return false
        if (username != other.username) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.toString().hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + isNSFW.hashCode()
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        return result
    }
}

enum class SearchResultType {
    LINK
}
