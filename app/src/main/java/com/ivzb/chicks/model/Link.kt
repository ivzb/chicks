package com.ivzb.chicks.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Describes an item in the feed, displaying social-media like updates.
 * Each item includes a message, id, a title and a value.
 */
@Parcelize
data class Link(

    @SerializedName("id")
    val id: Long,

    @SerializedName("url")
    val url: String,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("timestamp")
    val timestamp: Long? = 0
) : Parcelable
