package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinkMetaDataDataSource
import com.ivzb.chicks.data.links.LinksRepository
import com.ivzb.chicks.domain.UseCase
import com.ivzb.chicks.model.Link
import javax.inject.Inject

/**
 * Update link item into db.
 */
open class FetchLinkMetaDataUseCase @Inject constructor(
    private val repository: LinksRepository,
    private val linkMetaDataDataSource: LinkMetaDataDataSource
) : UseCase<Link, Unit>() {

    override fun execute(link: Link) {
        val linkMetaData = linkMetaDataDataSource.getLinkMetaData(link.url)

        repository.update(linkMetaData)
    }
}
