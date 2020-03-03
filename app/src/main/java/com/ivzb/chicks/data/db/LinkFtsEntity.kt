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

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long? = 0
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LinkFtsEntity

        if (id != other.id) return false
        if (url != other.url) return false
        if (title != other.title) return false
        if (imageUrl != other.imageUrl) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (timestamp?.hashCode() ?: 0)
        return result
    }
}
