package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinksRepository
import com.ivzb.chicks.domain.UseCase
import com.ivzb.chicks.model.Link
import javax.inject.Inject

open class LoadLinkUseCase @Inject constructor(
    private val repository: LinksRepository
) : UseCase<Int, Link>() {

    override fun execute(parameters: Int): Link {
        return repository.get(parameters)
    }
}