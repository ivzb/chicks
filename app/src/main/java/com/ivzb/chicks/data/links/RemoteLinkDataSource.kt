package com.ivzb.chicks.data.links

import android.content.Context
import com.google.gson.Gson
import com.ivzb.chicks.util.NetworkUtils
import java.io.IOException
import javax.inject.Inject

/**
 * Downloads and parses link data.
 */
class RemoteLinkDataSource @Inject constructor(
    val context: Context,
    val gson: Gson,
    private val networkUtils: NetworkUtils
): LinkDataSource {

    override fun fetchLinks(timestamp: Int): LinkData? {
        if (!networkUtils.hasNetworkConnection()) {
            return null
        }

        val responseSource = try {
            LinkDataDownloader(context, "1").fetch()
        } catch (e: IOException) {
            throw e
        }

        val body = responseSource.body()?.byteStream() ?: return null

        val parsedData = try {
            gson.fromJson(body.reader(), LinkData::class.java)
        } catch (e: RuntimeException) {
            null
        }

        responseSource.close()
        return parsedData
    }
}
