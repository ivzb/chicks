package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinksRepository
import com.ivzb.chicks.domain.UseCase
import com.ivzb.chicks.model.Link
import javax.inject.Inject

/**
 * Insert link item into db.
 */
open class InsertLinkUseCase @Inject constructor(
    private val repository: LinksRepository
) : UseCase<Link, Unit>() {

    override fun execute(link: Link) {
        repository.insert(link)
    }
}
