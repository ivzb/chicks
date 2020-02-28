package com.ivzb.chicks.ui.main

import androidx.lifecycle.ViewModel
import com.ivzb.chicks.di.ChildFragmentScoped
import com.ivzb.chicks.di.ViewModelKey
import com.ivzb.chicks.ui.link.LinkOptionsDialogFragment
import com.ivzb.chicks.ui.link.LinkOptionsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindViewModel(viewModel: MainActivityViewModel): ViewModel

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindLinkOptionsDialogFragment(): LinkOptionsDialogFragment


    @Binds
    @IntoMap
    @ViewModelKey(LinkOptionsViewModel::class)
    abstract fun bindLinkOptionsViewModel(viewModel: LinkOptionsViewModel): ViewModel
}
