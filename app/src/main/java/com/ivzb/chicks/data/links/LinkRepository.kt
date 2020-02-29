package com.ivzb.chicks.data.links

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ivzb.chicks.data.db.AppDatabase
import com.ivzb.chicks.data.db.LinkFtsEntity
import com.ivzb.chicks.model.Link
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Single point of access to session data for the presentation layer.
 *
 * The session data is loaded from the bootstrap file.
 */
@Singleton
open class LinkRepository @Inject constructor(
    @Named("remoteLinkDataSource") private val remoteDataSource: LinkDataSource,
    @Named("localLinkDataSource") private val localDataSource: LinkDataSource,
    private val appDatabase: AppDatabase
) {

    var latestUpdateSource: UpdateSource = UpdateSource.NONE
        private set

    private val _dataLastUpdatedObservable = MutableLiveData<Long>()
    val dataLastUpdatedObservable: LiveData<Long>
        get() = _dataLastUpdatedObservable

    fun refreshCacheWithRemoteLinkData() {

        populateLinks(remoteDataSource.getLinks())

        _dataLastUpdatedObservable.postValue(System.currentTimeMillis())
        latestUpdateSource = UpdateSource.NETWORK
    }

    fun getLinks(): List<Link> = remoteDataSource.getLinks()

//    fun getOfflineLinkData(): List<Link> {
//        synchronized(loadConfDataLock) {
//            val offlineData = linkDataCache ?: getCacheOrBootstrapDataAndPopulateSearch()
//            linkDataCache = offlineData
//            return offlineData
//        }
//    }
//
//    private fun getCacheOrBootstrapDataAndPopulateSearch(): LinkData {
//        val conferenceData = getCacheOrBootstrapData()
//        populateSearchData(conferenceData)
//        return conferenceData
//    }
//
//    private fun getCacheOrBootstrapData(): LinkData {
//        // First, try the local cache:
//        var conferenceData = remoteDataSource.getOfflineData()
//
//        // Cache success!
//        if (conferenceData != null) {
//            latestUpdateSource = UpdateSource.CACHE
//            return conferenceData
//        }
//
//        // Second, use the bootstrap file:
//        conferenceData = boostrapDataSource.getLinks()
//        latestUpdateSource = UpdateSource.BOOTSTRAP
//        return conferenceData
//    }
//
    open fun populateLinks(links: List<Link>) {
        val linkFtsEntities = links.map { link ->
            LinkFtsEntity(
                url = link.url,
                title = link.title,
                imageUrl = link.imageUrl,
                timestamp=  link.timestamp
            )
        }

        appDatabase.linksFtsDao().insertAll(linkFtsEntities)
    }
}
