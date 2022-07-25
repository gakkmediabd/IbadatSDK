package com.ibadat.sdk.util

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.ibadat.sdk.activities.HomeIbadatSdkActivity
import java.io.File

object DownloadImage {
    fun startDownload(
        context: Context,
        ContentName: String,
        DownloadedPhysicalFile: String,
        requestType: String
    ) {
        var ext = ".jpg"
        if (requestType == "wp") {
            ext = ".jpg"
        } else if (requestType == "an") {
            ext = ".gif"
        } else if (requestType == "vd") {
            ext = ".mp4"
        } else if (requestType == "bft") {
            ext = ".mp3"
        } else if (requestType == "tt") {
            ext = ".wav"
        }
        val file = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/Ibadat"
        )
        if (!file.exists()) {
            file.mkdirs()
        }
        val request =
            DownloadManager.Request(Uri.parse(DownloadedPhysicalFile.replace(" ".toRegex(), "%20")))
        request.setDescription("$ContentName start to download")
        request.setTitle(ContentName)
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setAllowedOverRoaming(false)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            ContentName + ext
        )
        request.setVisibleInDownloadsUi(true)
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = manager.enqueue(request)
        val finalExt = ext
        Thread {
            var downloading = true
            while (downloading) {
                val q = DownloadManager.Query()
                q.setFilterById(downloadId)
                val cursor = manager.query(q)
                cursor.moveToFirst()
                val bytesDownloaded = cursor.getInt(
                    cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR).toInt()
                )
                val bytesTotal =
                    cursor.getInt(
                        cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES).toInt()
                    )
                if (cursor.getInt(
                        cursor.getColumnIndex(DownloadManager.COLUMN_STATUS).toInt()
                    ) == DownloadManager.STATUS_SUCCESSFUL
                ) {
                    downloading = false
                }
                val dlProgress =
                    (bytesDownloaded.toDouble() / bytesTotal.toDouble() * 100f).toInt()
                try {
                    (context as HomeIbadatSdkActivity).runOnUiThread {
                        if (dlProgress == 100) {
                            try {
                                var downloadedType = ""
                                if (finalExt == ".jpg") {
                                    downloadedType = "WP"
                                } else if (finalExt == ".gif") {
                                    downloadedType = "AN"
                                } else if (finalExt == ".mp4") {
                                    downloadedType = "VD"
                                } else if (finalExt == ".mp3") {
                                    downloadedType = "BFT"
                                } else if (finalExt == ".wav") {
                                    downloadedType = "TT"
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            Toast.makeText(
                                context,
                                "Download has been completed",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Log.d("Download Status", statusMessage(cursor))
                cursor.close()
            }
        }.start()
    }

    private fun statusMessage(c: Cursor): String {
        var msg = "???"
        msg = when (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS).toInt())) {
            DownloadManager.STATUS_FAILED -> "Download failed!"
            DownloadManager.STATUS_PAUSED -> "Download paused!"
            DownloadManager.STATUS_PENDING -> "Download pending!"
            DownloadManager.STATUS_RUNNING -> "Download in progress!"
            DownloadManager.STATUS_SUCCESSFUL -> "Download complete!"
            else -> "Download is nowhere in sight"
        }
        return msg
    }
}