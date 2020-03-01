package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinkRepository
import com.ivzb.chicks.domain.UseCase
import javax.inject.Inject

open class FetchLinksUseCase @Inject constructor(
    private val repository: LinkRepository
) : UseCase<Int, Unit>() {

    override fun execute(timestamp: Int) {
        repository.fetchLinks(timestamp)
    }
}
