package com.ivzb.chicks.data.links

interface LinkDataSource {

    fun fetchLinks(timestamps: Int): LinkData?
}