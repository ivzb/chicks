package com.ivzb.chicks.data.links

//interface LinkRepository {
//
//    fun getLinks(): List<Link>
//    fun getLink(id: Int): Link
//}
//
//class DefaultLinkRepository @Inject constructor(
//    private val dataRepository: DataRepository
//) : LinkRepository {
//
//    override fun getLinks(): List<Link> {
//        return dataRepository.getOfflineData().links
//    }
//
//    override fun getLink(id: Int): Link {
//        return dataRepository.getOfflineData().links.firstOrNull { link ->
//            link.id == id
//        } ?: throw LinkNotFoundException()
//    }
//}
//
//class LinkNotFoundException : Throwable()
