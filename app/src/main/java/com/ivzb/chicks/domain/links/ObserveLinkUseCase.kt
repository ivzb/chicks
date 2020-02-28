package com.ivzb.chicks.domain.links

import com.ivzb.chicks.data.links.LinksRepository
import com.ivzb.chicks.domain.MediatorUseCase
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.model.Link
import javax.inject.Inject

open class ObserveLinkUseCase @Inject constructor(
    private val repository: LinksRepository
) : MediatorUseCase<Int, Link>() {

    override fun execute(parameters: Int) {
        result.addSource(repository.observe(parameters)) {
            result.postValue(Result.Success(it))
        }
    }
}
