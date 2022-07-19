package com.ibadat.sdk.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.restrepo.NetworkDataCallBack
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import javax.net.ssl.HttpsURLConnection

internal object FileHandler {
    @SuppressLint("StaticFieldLeak")
    class DownloadFileFromUrl(
        val context: Context,
        val path: String,
        private val networkLoader: NetworkDataCallBack
    ) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            networkLoader.onLoading(true)
            var fileExtractStatus = false
            val url = URL(path)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
            if (urlConnection.responseCode == 200) {
                val filePath = url.path.toString()
                val input: InputStream = BufferedInputStream(urlConnection.inputStream)
                val path = context.filesDir
                val fileName = filePath.substring(filePath.lastIndexOf('/') + 1)
                val file = File(path, fileName)
                val output: OutputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var bufferLength = 0
                val downloadedZipFileURI = file.absolutePath
                val fileOutPutFolderName = file.name.substring(
                    0,
                    file.name.lastIndexOf(".")
                )
                val unzipFile = File(path, "")
                val unzipFolderWritePath = unzipFile.absolutePath
                while (input.read(buffer).also { bufferLength = it } > 0) {
                    output.write(buffer, 0, bufferLength)
                }
                output.flush()
                output.close()
                input.close()

                fileExtractStatus = beforeCheckExtractFolder(
                    downloadedZipFileURI,
                    unzipFolderWritePath,
                    fileOutPutFolderName
                )
                return fileExtractStatus
            }
            return fileExtractStatus
        }

        override fun onPostExecute(result: Boolean) {
            networkLoader.onSuccess(result)
            AppPreference.isSDkResourceDownloadStatus = result
            AppPreference.isSDkResourceExtractStatus = result
        }
    }

    private fun beforeCheckExtractFolder(
        fileInputLocation: String,
        unzipFolderWritePath: String,
        folderName: String
    ): Boolean {
        val f = File(("$unzipFolderWritePath/$folderName"))
        return if (!f.isDirectory) {
            f.mkdir()
            unzipDataProcess(f.absolutePath, fileInputLocation)
        } else {
            unzipDataProcess(f.absolutePath, fileInputLocation)
        }
    }

    private fun unzipDataProcess(
        unzipPath: String,
        fileStoredLocation: String
    ): Boolean {
        var fileExtractStatus = false
        try {
            val zipInputStreamRef = ZipInputStream(FileInputStream(fileStoredLocation))
            zipInputStreamRef.use { zipInputStream ->
                var zipEntry: ZipEntry
                var iniNextEntry = zipInputStream.nextEntry
                while (iniNextEntry != null) {
                    zipEntry = iniNextEntry
                    val path = unzipPath + "/" + zipEntry.name
                    if (zipEntry.isDirectory) {
                        val unzipFile = File(path)
                        if (!unzipFile.isDirectory) {
                            unzipFile.mkdirs()
                        }
                    } else {
                        val dir = unzipPath + "/" + getDirName(zipEntry.name)
                        val dirFile = File(dir)
                        dirFile.mkdirs()
                        val unzipFile = File(path)
                        unzipFile.createNewFile()
                        val fileOutPutStream = FileOutputStream(unzipFile, false)
                        fileOutPutStream.use { fileOutputStream ->
                            var c: Int = zipInputStream.read()
                            while (c != -1) {
                                fileOutputStream.write(c)
                                c = zipInputStream.read()
                            }
                            zipInputStream.closeEntry()
                        }
                    }
                    iniNextEntry = zipInputStream.nextEntry
                }
                fileExtractStatus = true
            }
            return fileExtractStatus
        } catch (e: Exception) {
            Log.e("TAG", "Unzip exception", e)
            return fileExtractStatus
        }
    }

    private fun getDirName(str: String): String {
        val lastDirIndex = str.lastIndexOf("/")
        if (lastDirIndex == -1) {
            return str
        }
        return str.substring(0, lastDirIndex)
    }
}