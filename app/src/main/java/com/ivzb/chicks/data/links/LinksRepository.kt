package com.ivzb.chicks.data.links

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ivzb.chicks.data.db.AppDatabase
import com.ivzb.chicks.data.db.LinkFtsEntity
import com.ivzb.chicks.model.Link
import com.ivzb.chicks.model.LinkMetaData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single point of access to feed data for the presentation layer.
 */
interface LinksRepository {

    fun get(id: Int): Link

    fun getAll(): List<Link>

    fun observe(id: Int): LiveData<Link>

    fun observeAll(): LiveData<List<Link>>

    fun insert(link: Link)

    fun update(linkMetaData: LinkMetaData)

    fun refreshCacheWithRemoteLinks()
}

@Singleton
open class DefaultFeedRepository @Inject constructor(
    private val appDatabase: AppDatabase
) : LinksRepository {

    override fun get(id: Int): Link {
        return mapLink(appDatabase.linksFtsDao().get(id))
    }

    override fun getAll(): List<Link> {
        return appDatabase.linksFtsDao().getAll().toSet().map {
            mapLink(it)
        }
    }

    override fun observe(id: Int): LiveData<Link> {
        return Transformations.map(
            appDatabase.linksFtsDao().observe(id)
        ) { mapLink(it) }
    }

    override fun observeAll(): LiveData<List<Link>> {
        return Transformations.map(
            appDatabase.linksFtsDao().observeAll()
        ) {
            it
                .toSet()
                .map {
                    mapLink(it)
                }
        }
    }

    override fun insert(link: Link) {
        appDatabase.linksFtsDao().insert(
            LinkFtsEntity(
                id = link.id,
                url = link.url
            )
        )
    }

    override fun update(linkMetaData: LinkMetaData) {
        appDatabase.linksFtsDao().update(
            linkMetaData.url,
            linkMetaData.sitename,
            linkMetaData.title,
            linkMetaData.imageUrl
        )
    }

    override fun refreshCacheWithRemoteLinks() {
        TODO("not implemented")
    }

    private fun mapLink(it: LinkFtsEntity) = Link(
        id = it.id,
        url = it.url,
        sitename = it.sitename,
        title = it.title,
        imageUrl = it.imageUrl
    )
}
