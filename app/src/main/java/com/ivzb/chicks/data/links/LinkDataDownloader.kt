package com.ivzb.chicks.data.links

import android.content.Context
import androidx.annotation.WorkerThread
import com.ivzb.chicks.BuildConfig
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

/**
 * Downloads link data.
 */
class LinkDataDownloader(
    private val context: Context,
    private val bootstrapVersion: String
) {

    private val client: OkHttpClient by lazy {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val cacheSize = 2L * 1024 * 1024 // 2 MiB
        val cacheDir = context.getDir("link_data", Context.MODE_PRIVATE)
        val cache = Cache(cacheDir, cacheSize)

        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logInterceptor)
            .build()
    }

    @Throws(IOException::class)
    @WorkerThread
    fun fetch(): Response {
        val url = BuildConfig.LINK_DATA_URL

        val httpBuilder = HttpUrl.parse(url)?.newBuilder()
            ?: throw IllegalArgumentException("Malformed link data URL")
        httpBuilder.addQueryParameter("bootstrapVersion", bootstrapVersion)

        val request = Request.Builder()
            .url(httpBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()

        // Blocking call
        val response = client.newCall(request).execute()

        return response ?: throw IOException("Network error")
    }

    fun fetchCached(): Response? {
        val url = BuildConfig.LINK_DATA_URL

        val httpBuilder = HttpUrl.parse(url)?.newBuilder()
            ?: throw IllegalArgumentException("Malformed link data URL")
        httpBuilder.addQueryParameter("bootstrapVersion", bootstrapVersion)

        val request = Request.Builder()
            .url(httpBuilder.build())
            .cacheControl(CacheControl.FORCE_CACHE)
            .build()

        // Blocking call
        val response = client.newCall(request).execute()

        if (response.code() == 504) {
            return null
        }

        return response ?: throw IOException("Network error")
    }
}
