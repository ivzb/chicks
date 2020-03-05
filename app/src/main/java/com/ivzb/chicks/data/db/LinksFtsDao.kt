package com.ivzb.chicks.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Data Access Object for the [LinkFtsEntity] class.
 */
@Dao
interface LinksFtsDao {

    @Query("SELECT rowid, url, title, imageUrl, timestamp, is_nsfw FROM linkFts WHERE url = :url")
    fun get(url: String): LinkFtsEntity

    @Query("SELECT rowid, url, title, imageUrl, timestamp, is_nsfw FROM linkFts ORDER BY timestamp DESC")
    fun getAll(): List<LinkFtsEntity>

    @Query("SELECT rowid, url, title, imageUrl, timestamp, is_nsfw FROM linkFts ORDER BY timestamp DESC")
    fun observeAll(): LiveData<List<LinkFtsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(link: List<LinkFtsEntity>)
}
