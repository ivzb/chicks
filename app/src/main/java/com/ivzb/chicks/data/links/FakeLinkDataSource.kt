package com.ivzb.chicks.data.links

import com.ivzb.chicks.model.Link
import javax.inject.Inject

class FakeLinkDataSource @Inject constructor() : LinkDataSource {

    override fun fetchLinks(timestamp: Int): LinkData? {
        return LinkData(
            links = listOf(
                Link(
                    url = "https://www.youtube.com/watch?v=uovg-pe6goI",
                    title = "Юбилейно разкриване на тайни - Седмична реалност #60",
                    imageUrl = "https://fibro.bg/wp-content/uploads/2019/03/thumbnail-7.png",
                    timestamp = 1
                ),
                Link(
                    url = "https://www.reddit.com/r/pics/comments/fbtqgk/your_day_just_got_that_much_better/?utm_medium=android_app&utm_source=share",
                    title = "r / pics - Your day just got that much better",
                    imageUrl = "https://preview.redd.it/8y1apxwk72k41.jpg?auto=webp&s=a511f4d50384227722eb75232bae34b35f7aff20",
                    timestamp = 2
                ),
                Link(
                    url = "https://youtu.be/vsl3gBVO2k4",
                    title = "Queen - Bohemian Rhapsody [High Definition]",
                    imageUrl = "https://i.ytimg.com/vi/vsl3gBVO2k4/maxresdefault.jpg",
                    timestamp = 3
                ),
                Link(
                    url = "https://youtu.be/vsl3gBVO2k4",
                    title = "Queen - Bohemian Rhapsody",
                    imageUrl = "https://i.ytimg.com/vi/vsl3gBVO2k4/maxresdefault.jpg",
                    timestamp = 4
                )
            ),
            timestamp = timestamp
        )
    }
}
