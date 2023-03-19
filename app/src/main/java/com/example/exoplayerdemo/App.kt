package com.example.exoplayerdemo

import android.app.Application
import android.os.Build
import com.google.android.exoplayer2.ExoPlayerLibraryInfo
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.*
import java.io.File

/**
 * Created by cnting on 2019-08-05
 *
 */
class App : Application() {
    private val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
    private var downloadCache: Cache? = null
    private var downloadDirectory: File? = null
//    lateinit var videoDownloadManager: VideoDownloadManager

    override fun onCreate() {
        super.onCreate()
//        videoDownloadManager = VideoDownloadManager(this)
    }

    fun getUserAgent( applicationName: String): String? {
        return (applicationName + "/" + BuildConfig.VERSION_NAME + " (Linux;Android " + Build.VERSION.RELEASE
                + ") " + ExoPlayerLibraryInfo.VERSION_SLASHY)
    }

    private fun getDownloadDirectory(): File? {
        if (downloadDirectory == null) {
            downloadDirectory = getExternalFilesDir(null)
            if (downloadDirectory == null) {
                downloadDirectory = filesDir
            }
        }
        return downloadDirectory
    }

    @Synchronized
    private fun getDownloadCache(): Cache  {
        if (downloadCache == null) {
            val downloadContentDirectory: File =  File(getDownloadDirectory(),  DOWNLOAD_CONTENT_DIRECTORY)
            downloadCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor())
        }
        return downloadCache!!
    }

    private fun buildReadOnlyCacheDataSource( upstreamFactory: DefaultDataSourceFactory, cache: Cache ): CacheDataSourceFactory  {
        return CacheDataSourceFactory(
            cache,
            upstreamFactory,
            FileDataSourceFactory(),  /* cacheWriteDataSinkFactory= */
            null,
            CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,  /* eventListener= */
            null
        )
    }

    /** Returns a [HttpDataSource.Factory].  */
    fun buildHttpDataSourceFactory(
        listener: TransferListener<in DataSource?>?
    ): HttpDataSource.Factory? {
        return DefaultHttpDataSourceFactory(getUserAgent("rrr"), listener)
    }

    /** Returns a [DataSource.Factory].  */
    fun buildDataSourceFactory(listener: TransferListener<in DataSource?>?): DataSource.Factory  {
        val upstreamFactory =  DefaultDataSourceFactory(this, listener, buildHttpDataSourceFactory(listener))
        return buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache())
    }


}