package com.ivzb.chicks.ui.details

import androidx.lifecycle.ViewModel
import com.ivzb.chicks.di.FragmentScoped
import com.ivzb.chicks.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [DetailsFragment] are defined.
 */
@Module
@Suppress("UNUSED")
internal abstract class DetailsModule {

    /**
     * Generates an AndroidInjector for the [DetailsFragment].
     */
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeDetailsFragment(): DetailsFragment

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [DetailsViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsFragmentViewModel(viewModel: DetailsViewModel): ViewModel
}
