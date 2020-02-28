package com.ivzb.chicks.data.announcements

import com.ivzb.chicks.model.Announcement
import javax.inject.Inject
import javax.inject.Singleton

interface AnnouncementsRepository {

    fun getAnnouncements(): List<Announcement>
}

@Singleton
open class DefaultAnnouncementsRepository @Inject constructor(
    private val announcementDataSource: AnnouncementDataSource
) : AnnouncementsRepository {

    override fun getAnnouncements(): List<Announcement> = announcementDataSource.getAnnouncements()
}
