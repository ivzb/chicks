package com.ivzb.chicks.data.links

import com.ivzb.chicks.model.Link
import javax.inject.Inject

class FakeLinkDataSource @Inject constructor() : LinkDataSource {

    override fun fetchLinks(timestamp: Int): LinkData? {
        return LinkData(
            links = listOf(
                Link(
                    id = 1,
                    imageUrl = "https://fibro.bg/wp-content/uploads/2019/03/thumbnail-7.png",
                    isNSFW = true
                ),
                Link(
                    id = 2,
                    imageUrl = "https://preview.redd.it/8y1apxwk72k41.jpg?auto=webp&s=a511f4d50384227722eb75232bae34b35f7aff20",
                    isNSFW = true
                ),
                Link(
                    id = 3,
                    imageUrl = "https://i.ytimg.com/vi/vsl3gBVO2k4/maxresdefault.jpg",
                    isNSFW = false
                ),
                Link(
                    id = 4,
                    imageUrl = "https://i.ytimg.com/vi/vsl3gBVO2k4/maxresdefault.jpg",
                    isNSFW = false
                )
            ),
            timestamp = timestamp
        )
    }
}
