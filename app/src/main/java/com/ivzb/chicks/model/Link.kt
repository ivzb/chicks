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
    val timestamp: Long? = 0,

    @SerializedName("is_nsfw")
    val isNSFW: Boolean
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Link

        if (id != other.id) return false
        if (url != other.url) return false
        if (title != other.title) return false
        if (imageUrl != other.imageUrl) return false
        if (timestamp != other.timestamp) return false
        if (isNSFW != other.isNSFW) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.toString().hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (timestamp?.toString().hashCode() ?: 0)
        result = 31 * result + isNSFW.hashCode()
        return result
    }
}
