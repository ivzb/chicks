package com.ivzb.chicks.data.announcements

import com.ivzb.chicks.model.Announcement
import javax.inject.Inject

interface AnnouncementDataSource {

    fun getAnnouncements(): List<Announcement>
}

class DefaultAnnouncementDataSource @Inject constructor() : AnnouncementDataSource {

    override fun getAnnouncements() = listOf(
        Announcement(
            id = 1,
            title = "Refresh for chicks",
            message = "Pull to refresh for chicks."
        )
    )
}
