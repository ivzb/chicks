package com.ivzb.chicks.domain.search

import com.ivzb.chicks.data.links.LinkRepository
import com.ivzb.chicks.domain.UseCase
import javax.inject.Inject

/**
 * Performs a search from a query string.
 *
 * A session is returned in the results if the title, description, or tag matches the query parameter.
 */
class LoadSearchUseCase @Inject constructor(
    private val repository: LinkRepository
) : UseCase<String, List<Searchable>>() {

    override fun execute(parameters: String): List<Searchable> {
        val query = parameters.toLowerCase()

        return repository.getLinks()
            .toSet()
            .filter { link ->
                query.isEmpty() ||
                        link.title?.toLowerCase()?.contains(query) ?: false ||
                        link.url.toLowerCase().contains(query)
            }
            .map { Searchable.SearchedLink(it) }
    }
}
