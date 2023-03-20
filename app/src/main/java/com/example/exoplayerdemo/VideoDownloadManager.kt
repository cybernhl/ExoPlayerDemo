package com.example.exoplayerdemo

//import com.google.android.exoplayer2.database.DatabaseProvider
//import com.google.android.exoplayer2.database.ExoDatabaseProvider
//import com.google.android.exoplayer2.offline.DefaultDownloadIndex
//import com.google.android.exoplayer2.offline.DefaultDownloaderFactory
import android.content.Context
import android.os.Build
import com.google.android.exoplayer2.ExoPlayerLibraryInfo
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper
import com.google.android.exoplayer2.offline.ProgressiveDownloadAction
import com.google.android.exoplayer2.source.dash.offline.DashDownloadAction
import com.google.android.exoplayer2.source.hls.offline.HlsDownloadAction
import com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloadAction
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import java.io.File

/**
 * Created by cnting on 2019-08-05
 *Ref : DemoApplication
 * Ref : 2.15.0 https://github.com/uquabc/video_player/blob/master/android/src/main/java/io/flutter/plugins/videoplayer/VideoDownloadManager.kt
 */
class VideoDownloadManager(val context: Context) {
    private val MAX_SIMULTANEOUS_DOWNLOADS = 2
    private val DOWNLOAD_ACTION_FILE = "actions"
    private val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
    private val DOWNLOAD_TRACKER_ACTION_FILE = "tracked_actions"
    private val DOWNLOAD_DESERIALIZERS = arrayOf(
        DashDownloadAction.DESERIALIZER,
        HlsDownloadAction.DESERIALIZER,
        SsDownloadAction.DESERIALIZER,
        ProgressiveDownloadAction.DESERIALIZER
    )
    private val userAgent = Util.getUserAgent(context, "ExoPlayerDemo")

    val downloadManager: DownloadManager by lazy {
//        val downloadIndex = DefaultDownloadIndex(databaseProvider)
        val downloaderConstructorHelper = DownloaderConstructorHelper(
//            downloadCache, buildHttpDataSourceFactory( /* listener= */null)
            downloadCache, buildHttpDataSourceFactory
        )
//        val downloadManager = DownloadManager(
//            context, downloadIndex, DefaultDownloaderFactory(downloaderConstructorHelper)
//        )
        val downloadManager = DownloadManager(
            downloaderConstructorHelper,
            MAX_SIMULTANEOUS_DOWNLOADS,
            DownloadManager.DEFAULT_MIN_RETRY_COUNT,
            File(downloadDirectory,  DOWNLOAD_ACTION_FILE),
            * DOWNLOAD_DESERIALIZERS
        )
       downloadManager.addListener(downloadTracker)
        downloadManager
    }

   val downloadTracker: DownloadTracker by lazy {
//       val downloadTracker: VideoDownloadTracker by lazy {
//       val downloadTracker = VideoDownloadTracker(context, buildDataSourceFactory, downloadManager)
        val downloadTracker = DownloadTracker(context, buildDataSourceFactory,File(downloadDirectory,  DOWNLOAD_TRACKER_ACTION_FILE) ,DOWNLOAD_DESERIALIZERS)
        downloadTracker
    }

    //FIXME 2.8.4 not
//    private val databaseProvider: DatabaseProvider by lazy {
//        val p = ExoDatabaseProvider(context)
//        p
//    }

    private val downloadDirectory: File by lazy {
        var directionality = context.getExternalFilesDir(null)
        if (directionality == null) {
            directionality = context.filesDir
        }
        directionality!!
    }

    val downloadCache: Cache by lazy {
        val downloadContentDirectory = File(downloadDirectory, DOWNLOAD_CONTENT_DIRECTORY)
        val downloadCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor())
//        val downloadCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor(), databaseProvider)
        downloadCache
    }

    private val buildHttpDataSourceFactory: HttpDataSource.Factory by lazy {
//        val ff =DefaultHttpDataSourceFactory(getUserAgent("rrr"), listener)
        val factory = DefaultHttpDataSourceFactory(userAgent)
        factory
    }

    private fun buildReadOnlyCacheDataSource(
        upstreamFactory: DataSource.Factory,
        cache: Cache
    ): CacheDataSourceFactory {
        return CacheDataSourceFactory(
            cache, upstreamFactory, FileDataSourceFactory(), null, CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null
        )
    }

    val buildDataSourceFactory: DataSource.Factory by lazy {
//        val upstreamFactory = DefaultDataSourceFactory(context, buildHttpDataSourceFactory)
        //Ref : listener can DefaultBandwidthMeter
//        val upstreamFactory =  DefaultDataSourceFactory(context, null, buildHttpDataSourceFactory(null))// null is listener   TransferListener<in DataSource?>
        val upstreamFactory =  DefaultDataSourceFactory(context, null, buildHttpDataSourceFactory)
        val factory = buildReadOnlyCacheDataSource(upstreamFactory, downloadCache)
        factory
    }
}