package com.ivzb.chicks.domain.search

import com.ivzb.chicks.model.Link

/**
 * Sealed class that represents searchable contents.
 */
sealed class Searchable {

    class SearchedLink(val link: Link) : Searchable()
}
