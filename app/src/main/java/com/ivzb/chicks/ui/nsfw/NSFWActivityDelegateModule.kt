package com.ivzb.chicks.ui.nsfw

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
@Suppress("UNUSED")
abstract class NSFWActivityDelegateModule {

    @Singleton
    @Binds
    abstract fun provideNSFWActivityDelegate(
        impl: NSFWActivityDelegateImpl
    ): NSFWActivityDelegate
}
