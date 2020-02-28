package com.ivzb.chicks.ui

import androidx.annotation.StringRes

data class SectionHeader(
    @StringRes val titleId: Int,
    val useHorizontalPadding: Boolean = true
)
