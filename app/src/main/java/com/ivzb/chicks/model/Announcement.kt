package com.ivzb.chicks.model

data class Announcement(
    val id: Int,
    val title: String,
    val message: String? = null,
    val imageUrl: String? = null
) {
    val hasImage = !imageUrl.isNullOrEmpty()
}
