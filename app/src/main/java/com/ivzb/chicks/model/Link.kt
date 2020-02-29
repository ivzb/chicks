package com.ivzb.chicks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Describes an item in the feed, displaying social-media like updates.
 * Each item includes a message, id, a title and a value.
 */
@Parcelize
data class Link(
    val url: String,
    val title: String? = null,
    val imageUrl: String? = null,
    val timestamp: Long? = 0
) : Parcelable
