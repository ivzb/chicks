package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinksRepository
import com.ivzb.chicks.domain.MediatorUseCase
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.model.Link
import javax.inject.Inject

open class ObserveLinksUseCase @Inject constructor(
    private val repository: LinksRepository
) : MediatorUseCase<Unit, List<Link>>() {

    override fun execute(parameters: Unit) {
        result.addSource(repository.observeAll()) {
            result.postValue(Result.Success(it))
        }
    }
}
