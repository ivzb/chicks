package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinkRepository
import com.ivzb.chicks.domain.UseCase
import javax.inject.Inject

/**
 * Forces a refresh in the link data repository.
 */
open class RefreshLinksUseCase @Inject constructor(
    private val repository: LinkRepository
) : UseCase<Any, Boolean>() {

    override fun execute(parameters: Any): Boolean {
        repository.refreshCacheWithRemoteLinkData()

        return true
    }
}
