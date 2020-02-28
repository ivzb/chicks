package com.ivzb.chicks.di

import android.content.Context
import com.ivzb.chicks.data.announcements.AnnouncementDataSource
import com.ivzb.chicks.data.announcements.AnnouncementsRepository
import com.ivzb.chicks.data.announcements.DefaultAnnouncementDataSource
import com.ivzb.chicks.data.announcements.DefaultAnnouncementsRepository
import com.ivzb.chicks.data.db.AppDatabase
import com.ivzb.chicks.data.links.DefaultFeedRepository
import com.ivzb.chicks.data.links.LinkMetaDataDataSource
import com.ivzb.chicks.data.links.LinksRepository
import com.ivzb.chicks.util.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): AppDatabase = AppDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideLinkMetaDataDataSource(
        context: Context,
        networkUtils: NetworkUtils
    ): LinkMetaDataDataSource = LinkMetaDataDataSource(context, networkUtils)

    @Singleton
    @Provides
    fun provideLinksRepository(
        appDatabase: AppDatabase
    ): LinksRepository = DefaultFeedRepository(appDatabase)

    @Singleton
    @Provides
    fun provideAnnouncementDataSource(): AnnouncementDataSource {
        return DefaultAnnouncementDataSource()
    }

    @Singleton
    @Provides
    fun provideAnnouncementsRepository(
        announcementDataSource: AnnouncementDataSource
    ): AnnouncementsRepository {
        return DefaultAnnouncementsRepository(announcementDataSource)
    }
}
