package com.ivzb.chicks.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.ivzb.chicks.data.announcements.AnnouncementDataSource
import com.ivzb.chicks.data.announcements.DefaultAnnouncementDataSource
import com.ivzb.chicks.data.db.AppDatabase
import com.ivzb.chicks.data.links.*
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
    fun provideFirebaseFireStore() =
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                // This is to enable the offline data
                // https://firebase.google.com/docs/firestore/manage-data/enable-offline
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        }

    @Singleton
    @Provides
    fun provideAnnouncementDataSource(): AnnouncementDataSource {
        return DefaultAnnouncementDataSource()
    }

    @Singleton
    @Provides
    fun provideRemoteLinkDataSource(firestore: FirebaseFirestore): LinkDataSource {
        return RemoteLinkDataSource(firestore)
    }

    @Singleton
    @Provides
    fun provideLocalLinkDataSource(appDatabase: AppDatabase): LinkDataSource {
        return LocalLinkDataSource(appDatabase)
    }

    @Singleton
    @Provides
    fun provideLinkRepository(
        remoteLinkDataSource: RemoteLinkDataSource,
        localLinkDataSource: LocalLinkDataSource,
        appDatabase: AppDatabase
    ): LinkRepository = LinkRepository(remoteLinkDataSource, localLinkDataSource, appDatabase)
}
