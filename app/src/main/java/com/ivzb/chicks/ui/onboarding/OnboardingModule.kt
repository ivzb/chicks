package com.ivzb.chicks.ui.onboarding

import androidx.lifecycle.ViewModel
import com.ivzb.chicks.di.FragmentScoped
import com.ivzb.chicks.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [OnboardingFragment] are defined.
 */
@Module
@Suppress("UNUSED")
internal abstract class OnboardingModule {

    /**
     * Generates an [AndroidInjector] for the [OnboardingFragment].
     */
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeOnboardingFragment(): OnboardingFragment

    /**
     * Generates an [AndroidInjector] for the [OnboardingPagerFragment].
     */
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeOnboardingFragmentPager(): OnboardingPagerFragment

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [OnboardingViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    internal abstract fun bindOnboardingViewModel(viewModel: OnboardingViewModel): ViewModel
}
