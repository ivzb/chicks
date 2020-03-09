package com.ivzb.chicks.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

/**
 * This class represents [Link] data for searching with FTS.
 *
 * The [ColumnInfo] name is explicitly declared to allow flexibility for renaming the data class
 * properties without requiring changing the column name.
 */
@Entity(tableName = "linkFts")
@Fts4
data class LinkFtsEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "rowid")
    val id: Long,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "is_nsfw")
    val isNSFW: Boolean,

    @ColumnInfo(name = "username")
    val username: String? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LinkFtsEntity

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
