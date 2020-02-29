package com.ivzb.chicks.domain.announcements

import com.ivzb.chicks.data.announcements.AnnouncementDataSource
import com.ivzb.chicks.domain.UseCase
import com.ivzb.chicks.model.Announcement
import javax.inject.Inject

open class LoadAnnouncementsUseCase @Inject constructor(
    private val dataSource: AnnouncementDataSource
) : UseCase<Unit, List<Announcement>>() {

    override fun execute(parameters: Unit): List<Announcement> {
        return dataSource.getAnnouncements()
    }
}
