package com.testm.demosdk.network


import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

import java.io.File

import java.lang.Exception


// look at https://stackoverflow.com/questions/42118924/android-retrofit-download-progress


sealed class Download {
    data class Progress(val percent: Int, val id: Int) : Download()
    data class Finished(val file: File, val id: Int) : Download()
}


    fun ResponseBody.downloadToFileWithProgress(filename: String, id: Int): Flow<Download> =
    flow {
        emit(Download.Progress(0, id))

        // flag to delete file if download errors or is cancelled
        var deleteFile = true
        val file = File(filename)

        try {
            byteStream().use { inputStream ->
                file.outputStream().use { outputStream ->
                    val totalBytes = contentLength()
                    val data = ByteArray(8_192)
                    var progressBytes = 0L

                    while (true) {
                        val bytes = inputStream.read(data)

                        if (bytes == -1) {
                            break
                        }

                        outputStream.channel
                        outputStream.write(data, 0, bytes)
                        progressBytes += bytes

                        emit(Download.Progress(percent = ((progressBytes * 100) / totalBytes).toInt(), id = id))
                    }

                    when {
                        progressBytes < totalBytes ->
                            throw Exception("missing bytes")
                        progressBytes > totalBytes ->
                            throw Exception("too many bytes")
                        else ->
                            deleteFile = false
                    }
                }
            }

            emit(Download.Finished(file, id))
        } finally {
            // check if download was successful

            if (deleteFile) {
                file.delete()
            }
        }
    }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()

