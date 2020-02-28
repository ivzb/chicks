package com.ivzb.chicks.ui.feed

import com.ivzb.chicks.model.Link

interface EventActions {

    fun click(link: Link)

    fun longClick(link: Link)
}
