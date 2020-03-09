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

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("is_nsfw")
    val isNSFW: Boolean,

    @SerializedName("username")
    val username: String? = null

) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Link

        if (id != other.id) return false
        if (imageUrl != other.imageUrl) return false
        if (isNSFW != other.isNSFW) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.toString().hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + isNSFW.hashCode()
        result = 31 * result + (username?.hashCode() ?: 0)
        return result
    }
}
