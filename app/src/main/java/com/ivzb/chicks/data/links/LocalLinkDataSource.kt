package com.ivzb.chicks.data.links

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ivzb.chicks.data.announcements.AnnouncementDataSource
import com.ivzb.chicks.data.db.AppDatabase
import com.ivzb.chicks.data.db.LinkFtsEntity
import com.ivzb.chicks.model.Link
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single point of access to feed data for the presentation layer.
 */
//interface LinkDataSource {
//
//    fun getLinks(): List<Link>
//
////    fun get(id: Int): Link
////
////    fun getAll(): List<Link>
////
////    fun observe(id: Int): LiveData<Link>
////
////    fun observeAll(): LiveData<List<Link>>
////
////    fun insert(link: Link)
////
////    fun refreshCacheWithRemoteLinks()
//}
//
@Singleton
open class LocalLinkDataSource @Inject constructor(
    private val appDatabase: AppDatabase
) : LinkDataSource {

    //    override fun get(id: Int): Link {
//        return mapLink(appDatabase.linksFtsDao().get(id))
//    }
//
    override fun getLinks() =
        appDatabase.linksFtsDao().getAll().toSet().map { parseFtsEntity(it) }

    //
//    override fun observe(id: Int): LiveData<Link> {
//        return Transformations.map(
//            appDatabase.linksFtsDao().observe(id)
//        ) { mapLink(it) }
//    }
//
//    override fun observeAll(): LiveData<List<Link>> {
//        return Transformations.map(
//            appDatabase.linksFtsDao().observeAll()
//        ) {
//            it
//                .toSet()
//                .map {
//                    mapLink(it)
//                }
//        }
//    }
//
//    override fun insert(link: Link) {
//        appDatabase.linksFtsDao().insert(
//            LinkFtsEntity(
//                id = link.id,
//                url = link.url
//            )
//        )
//    }
//
//    override fun refreshCacheWithRemoteLinks() {
//        TODO("not implemented")
//    }
//
    private fun parseFtsEntity(it: LinkFtsEntity) = Link(
        url = it.url,
        title = it.title,
        imageUrl = it.imageUrl,
        timestamp = it.timestamp
    )
}
