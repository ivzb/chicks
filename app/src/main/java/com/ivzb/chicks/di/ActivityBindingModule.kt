package com.ivzb.chicks.di

import com.ivzb.chicks.ui.feed.FeedModule
import com.ivzb.chicks.ui.launcher.LaunchModule
import com.ivzb.chicks.ui.launcher.LauncherActivity
import com.ivzb.chicks.ui.details.DetailsModule
import com.ivzb.chicks.ui.main.MainActivity
import com.ivzb.chicks.ui.main.MainActivityModule
import com.ivzb.chicks.ui.onboarding.OnboardingActivity
import com.ivzb.chicks.ui.onboarding.OnboardingModule
import com.ivzb.chicks.ui.search.SearchModule
import com.ivzb.chicks.ui.settings.SettingsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be [AppComponent]. You never
 * need to tell [AppComponent] that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that [AppComponent] exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the
 * specified modules and be aware of a scope annotation [@ActivityScoped].
 * When Dagger.Android annotation processor runs it will create 2 subcomponents for us.
 */
@Module
@Suppress("UNUSED")
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LaunchModule::class])
    internal abstract fun launcherActivity(): LauncherActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [OnboardingModule::class])
    internal abstract fun onboardingActivity(): OnboardingActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // activity
            MainActivityModule::class,

            // fragments
            FeedModule::class,
            SearchModule::class,
            DetailsModule::class,
            SettingsModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity
}
