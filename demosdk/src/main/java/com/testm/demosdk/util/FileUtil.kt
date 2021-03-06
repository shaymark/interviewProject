package com.testm.demosdk.util

import android.util.Log
import com.testm.demosdk.viewmodels.SoundViewModelImpl
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

class FileUtil {

    @Throws(IOException::class)
    fun saveFileToDisk(inputStream: InputStream, pathname: String?) {
        val file = File(pathname)
        inputStream.use { inputStream ->
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
    }

    fun createDirectory(folder: String){
        File(folder).mkdirs()
    }

    fun deleteFilesFromDirectory(dir: String?) {
        deleteRecursive(File(dir))
    }

    private fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) for (child in fileOrDirectory.listFiles()) deleteRecursive(
            child
        )
        fileOrDirectory.delete()
    }

}