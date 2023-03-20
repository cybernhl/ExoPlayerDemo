/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.exoplayerdemo

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.offline.*
import com.google.android.exoplayer2.upstream.DataSource
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet
//https://github.com/uquabc/video_player/blob/master/android/src/main/java/io/flutter/plugins/videoplayer/VideoDownloadTracker.kt
/**
 * 下载管理
 */
//class VideoDownloadTracker(
//    context: Context, private val dataSourceFactory: DataSource.Factory, downloadManager: DownloadManager
//) {
//
//    private val context: Context = context.applicationContext
//    private val listeners: CopyOnWriteArraySet<Listener> = CopyOnWriteArraySet()
////    private val downloads: HashMap<Uri, Download> = HashMap()
//    private val downloads: HashMap<Uri, DownloadAction> = HashMap()
//    private val downloadIndex: DownloadIndex = downloadManager.downloadIndex
//
//    /** Listens for changes in the tracked downloads.  */
//    interface Listener {
//
//        /** Called when the tracked downloads changed.  */
//        fun onDownloadsChanged()
//    }
//
//    init {
//        downloadManager.addListener(DownloadManagerListener())
//        loadDownloads()
//    }
//
//    fun addListener(listener: Listener) {
//        listeners.add(listener)
//    }
//
//    fun removeListener(listener: Listener) {
//        listeners.remove(listener)
//    }
//
//    fun getDownload(uri: Uri): Download? {
//        return downloads[uri]
//    }
//
//    fun isDownloaded(uri: Uri): Boolean {
//        val download = downloads[uri]
//        return download != null && download.state != Download.STATE_FAILED
//    }
//
//    fun getDownloadRequest(uri: Uri): DownloadRequest? {
//        val download = downloads[uri]
//        return if (download != null && download.state != Download.STATE_FAILED) download.request else null
//    }
//
//    private fun loadDownloads() {
//        try {
//            downloadIndex.getDownloads().use { loadedDownloads ->
//                while (loadedDownloads.moveToNext()) {
//                    val download = loadedDownloads.download
//                    downloads[download.request.uri] = download
//                }
//            }
//        } catch (e: IOException) {
//            Log.e(TAG, "Failed to query downloads", e)
//        }
//
//    }
//
//    //Ref : Demo com.google.android.exoplayer2.demo.DownloadTracker
//    private inner class DownloadManagerListener : DownloadManager.Listener {
//
////        override fun onDownloadChanged(downloadManager: DownloadManager?, download: Download?) {
////            downloads[download!!.request.uri] = download
////            for (listener in listeners) {
////                listener.onDownloadsChanged()
////            }
////        }
////
////        override fun onDownloadRemoved(downloadManager: DownloadManager?, download: Download?) {
////            downloads.remove(download!!.request.uri)
////            for (listener in listeners) {
////                listener.onDownloadsChanged()
////            }
////        }
//
//        override fun onInitialized(downloadManager: DownloadManager ) {
//            // Do nothing.
//            Log.e(TAG, "DownloadManagerListener onInitialized")
//        }
//
//        override fun onTaskStateChanged( downloadManager: DownloadManager , taskState: DownloadManager.TaskState  ) {
//            Log.e(TAG, "DownloadManagerListener onTaskStateChanged")
//            val action = taskState.action
//            val uri = action.uri
//            if ((action.isRemoveAction && taskState.state == DownloadManager.TaskState.STATE_COMPLETED)|| (!action.isRemoveAction && taskState.state == DownloadManager.TaskState.STATE_FAILED)){
//                // A download has been removed, or has failed. Stop tracking it.
//                if (trackedDownloadStates.remove(uri) != null) {
//                    handleTrackedDownloadStatesChanged()
//                }
//            }
//        }
//
//        override fun onIdle(downloadManager: DownloadManager ) {
//            Log.e(TAG, "DownloadManagerListener onIdle")
//        }
//    }
//
//    companion object {
//
//        private val TAG = "DownloadTracker"
//    }
//}
