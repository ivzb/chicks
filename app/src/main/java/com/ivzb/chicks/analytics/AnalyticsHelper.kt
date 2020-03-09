package com.ivzb.chicks.analytics

import android.app.Activity

/** Analytics API surface */
interface AnalyticsHelper {

    /** Record a screen view */
    fun sendScreenView(screenName: String, activity: Activity)

    /** Record a UI event, e.g. user clicks a button */
    fun logUiEvent(action: String)
}

/** Actions that should be used when sending analytics events */
object AnalyticsActions {

    const val LINK_COPY = "link_copy"
    const val LINK_SHARE = "link_share"

    const val HOME_TO_DETAILS = "home_to_details"
    const val HOME_TO_OPTIONS = "home_to_options"
    const val HOME_TO_SEARCH = "home_to_search"
}

/** Screens that should be used when opening screens */
object AnalyticsScreens {

    const val HOME = "screen_home"
    const val SEARCH = "screen_search"
    const val OPTIONS = "screen_options"
    const val DETAILS = "screen_details"
    const val SETTINGS = "screen_settings"
}
