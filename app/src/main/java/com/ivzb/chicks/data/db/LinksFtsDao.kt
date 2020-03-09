package com.ivzb.chicks.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Data Access Object for the [LinkFtsEntity] class.
 */
@Dao
interface LinksFtsDao {

    @Query("SELECT rowid, imageUrl, is_nsfw, username FROM linkFts ORDER BY rowid DESC")
    fun getAll(): List<LinkFtsEntity>

    @Query("SELECT rowid, imageUrl, is_nsfw, username FROM linkFts ORDER BY rowid DESC")
    fun observeAll(): LiveData<List<LinkFtsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(link: List<LinkFtsEntity>)
}
