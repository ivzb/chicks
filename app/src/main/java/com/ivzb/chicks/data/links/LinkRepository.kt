package com.ivzb.chicks.data.links

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ivzb.chicks.data.db.AppDatabase
import com.ivzb.chicks.data.db.LinkFtsEntity
import com.ivzb.chicks.model.Link
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single point of access to session data for the presentation layer.
 *
 * The session data is loaded from the bootstrap file.
 */
@Singleton
open class LinkRepository @Inject constructor(
    private val dataSource: LinkDataSource,
    private val appDatabase: AppDatabase
) {

    fun fetchLinks(timestamp: Int) {
        dataSource.fetchLinks(timestamp)?.links?.apply {
            populateLinks(this)
        }
    }

    fun getLinks(): List<Link> {
        return appDatabase.linksFtsDao().getAll()
            .toSet()
            .map {
                Link(
                    id = it.id,
                    url = it.url,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    timestamp = it.timestamp,
                    isNSFW = it.isNSFW
                )
            }
    }

    fun observeLinks(): LiveData<List<Link>> {
        return Transformations.map(
            appDatabase.linksFtsDao().observeAll()
        ) {
            it
                .toSet()
                .map {
                    Link(
                        id = it.id,
                        url = it.url,
                        title = it.title,
                        imageUrl = it.imageUrl,
                        timestamp = it.timestamp,
                        isNSFW = it.isNSFW
                    )
                }
        }
    }

    open fun populateLinks(links: List<Link>) {
        val linkFtsEntities = links.map { link ->
            LinkFtsEntity(
                id = link.id,
                url = link.url,
                title = link.title,
                imageUrl = link.imageUrl,
                timestamp = link.timestamp,
                isNSFW = link.isNSFW
            )
        }

        appDatabase.linksFtsDao().insertAll(linkFtsEntities)
    }
}
