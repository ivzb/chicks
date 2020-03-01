package com.ivzb.chicks.data.links

import com.ivzb.chicks.model.Link
import javax.inject.Inject

class FakeLinkDataSource @Inject constructor() : LinkDataSource {

    override fun fetchLinks(timestamp: Int): LinkData? {
        return LinkData(
            links = listOf(
                Link(url = "https://www.youtube.com/watch?v=uovg-pe6goI", title = "Юбилейно разкриване на тайни - Седмична реалност #60", imageUrl = "https://fibro.bg/wp-content/uploads/2019/03/thumbnail-7.png", timestamp = 1)
            ),
            timestamp = timestamp
        )
    }
}
