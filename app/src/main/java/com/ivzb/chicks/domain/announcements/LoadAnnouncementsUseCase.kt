package com.ivzb.chicks.domain.announcements

import com.ivzb.chicks.data.announcements.AnnouncementsRepository
import com.ivzb.chicks.domain.UseCase
import com.ivzb.chicks.model.Announcement
import javax.inject.Inject

open class LoadAnnouncementsUseCase @Inject constructor(
    private val repository: AnnouncementsRepository
) : UseCase<Unit, List<Announcement>>() {

    override fun execute(parameters: Unit): List<Announcement> {
        return repository.getAnnouncements()
    }
}
