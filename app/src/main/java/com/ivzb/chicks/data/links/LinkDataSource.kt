package com.ivzb.chicks.data.links

import com.ivzb.chicks.model.Link

interface LinkDataSource {

//    fun getLinks(): List<Link>
    fun getRemoteData(): LinkData?
    fun getOfflineData(): LinkData?
}

enum class UpdateSource {
    NONE,
    NETWORK,
    CACHE,
    BOOTSTRAP
}
