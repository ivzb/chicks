package com.ivzb.chicks.di

import android.content.Context
import com.ivzb.chicks.MainApplication
import com.ivzb.chicks.analytics.AnalyticsHelper
import com.ivzb.chicks.analytics.FirebaseAnalyticsHelper
import com.ivzb.chicks.data.prefs.PreferenceStorage
import com.ivzb.chicks.data.prefs.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Defines all the classes that need to be provided in the scope of the app.
 *
 * Define here all objects that are shared throughout the app, like SharedPreferences, navigators or
 * others. If some of those objects are singletons, they should be annotated with `@Singleton`.
 */
@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesPreferenceStorage(context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)

    @Singleton
    @Provides
    fun providesAnalyticsHelper(
        context: Context
    ): AnalyticsHelper = FirebaseAnalyticsHelper(context)
}
