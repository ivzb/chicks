package com.ivzb.chicks.data.db

import androidx.room.*

/**
 * The Data Access Object for the [LinkFtsEntity] class.
 */
@Dao
interface LinksFtsDao {

    @Query("SELECT url, title, imageUrl, timestamp FROM linkFts WHERE url = :url")
    fun get(url: String): LinkFtsEntity

    @Query("SELECT url, title, imageUrl, timestamp FROM linkFts ORDER BY timestamp DESC")
    fun getAll(): List<LinkFtsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(link: List<LinkFtsEntity>)
}
