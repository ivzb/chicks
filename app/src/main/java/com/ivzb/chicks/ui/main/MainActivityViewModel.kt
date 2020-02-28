package com.ivzb.chicks.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.domain.links.FetchLinkMetaDataUseCase
import com.ivzb.chicks.domain.links.InsertLinkUseCase
import com.ivzb.chicks.model.Link
import com.ivzb.chicks.ui.theme.ThemedActivityDelegate
import com.ivzb.chicks.util.extractUrl
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    themedActivityDelegate: ThemedActivityDelegate,
    val insertLinkUseCase: InsertLinkUseCase,
    val fetchLinkMetaDataUseCase: FetchLinkMetaDataUseCase
) : ViewModel(), ThemedActivityDelegate by themedActivityDelegate {

    fun handleLink(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            Link(url = extractUrl(it)).let {
                insertLinkUseCase(it)
                fetchLinkMetaDataUseCase(it)
            }
        }
    }

}
