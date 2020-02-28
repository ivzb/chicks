package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinksRepository
import com.ivzb.chicks.domain.UseCase
import javax.inject.Inject

/**
 * Forces a refresh in the conference data repository.
 */
open class RefreshLinksUseCase @Inject constructor(
    private val repository: LinksRepository
) : UseCase<Any, Boolean>() {

    override fun execute(parameters: Any): Boolean {
        repository.refreshCacheWithRemoteLinks()

        return true
    }
}
