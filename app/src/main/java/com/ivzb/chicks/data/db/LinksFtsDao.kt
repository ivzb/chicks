package com.ivzb.chicks.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Data Access Object for the [LinkFtsEntity] class.
 */
@Dao
interface LinksFtsDao {

    @Query("SELECT rowid, url, sitename, title, imageUrl FROM linkFts WHERE rowid = :id")
    fun observe(id: Int): LiveData<LinkFtsEntity>

    @Query("SELECT rowid, url, sitename, title, imageUrl FROM linkFts WHERE rowid = :id")
    fun get(id: Int): LinkFtsEntity

    @Query("SELECT rowid, url, sitename, title, imageUrl FROM linkFts ORDER BY rowid DESC")
    fun getAll(): List<LinkFtsEntity>

    @Query("SELECT rowid, url, sitename, title, imageUrl FROM linkFts ORDER BY rowid DESC")
    fun observeAll(): LiveData<List<LinkFtsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(link: LinkFtsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(link: List<LinkFtsEntity>)

    @Query("UPDATE linkFts SET sitename = :sitename, title = :title, imageUrl = :imageUrl WHERE url = :url")
    fun update(url: String, sitename: String?, title: String?, imageUrl: String?)
}
