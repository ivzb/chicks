package com.ivzb.chicks.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.gson.Gson
import com.ivzb.chicks.data.announcements.AnnouncementDataSource
import com.ivzb.chicks.data.announcements.DefaultAnnouncementDataSource
import com.ivzb.chicks.data.db.AppDatabase
import com.ivzb.chicks.data.links.*
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

//    @Singleton
//    @Provides
//    fun provideRemoteDataSource(
//        context: Context,
//        gson: Gson,
//        networkUtils: NetworkUtils
//    ): LinkDataSource {
//        return RemoteLinkDataSource(context, gson, networkUtils)
//    }

    @Singleton
    @Provides
    fun provideFakeDataSource(): LinkDataSource {
        return FakeLinkDataSource()
    }

    @Singleton
    @Provides
    fun provideLinkRepository(
        linkDataSource: LinkDataSource,
        appDatabase: AppDatabase
    ): LinkRepository = LinkRepository(linkDataSource, appDatabase)

    @Singleton
    @Provides
    fun provideGson() = Gson()
}
