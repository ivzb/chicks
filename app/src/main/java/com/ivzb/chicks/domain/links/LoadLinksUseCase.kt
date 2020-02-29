package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinkRepository
import com.ivzb.chicks.domain.UseCase
import com.ivzb.chicks.model.Link
import javax.inject.Inject

open class LoadLinksUseCase @Inject constructor(
    private val repository: LinkRepository
) : UseCase<Unit, List<Link>>() {

    override fun execute(parameters: Unit) =
        repository.getLinks()
}

