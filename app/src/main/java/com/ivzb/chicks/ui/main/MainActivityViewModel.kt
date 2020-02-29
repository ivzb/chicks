package com.ivzb.chicks.ui.main

import androidx.lifecycle.ViewModel
import com.ivzb.chicks.ui.theme.ThemedActivityDelegate
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    themedActivityDelegate: ThemedActivityDelegate
) : ViewModel(), ThemedActivityDelegate by themedActivityDelegate
