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
                    imageUrl = it.imageUrl,
                    isNSFW = it.isNSFW,
                    username = it.username
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
                        imageUrl = it.imageUrl,
                        isNSFW = it.isNSFW,
                        username = it.username
                    )
                }
        }
    }

    open fun populateLinks(links: List<Link>) {
        val linkFtsEntities = links.map { link ->
            LinkFtsEntity(
                id = link.id,
                imageUrl = link.imageUrl,
                isNSFW = link.isNSFW,
                username = link.username
            )
        }

        appDatabase.linksFtsDao().insertAll(linkFtsEntities)
    }
}
