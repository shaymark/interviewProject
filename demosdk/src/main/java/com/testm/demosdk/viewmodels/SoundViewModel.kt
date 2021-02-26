package com.testm.demosdk.viewmodels

import android.content.Context
import android.util.Log
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testm.demosdk.model.SoundItem
import com.testm.demosdk.model.SoundItemUi
import com.testm.demosdk.network.Api
import com.testm.demosdk.network.Download
import com.testm.demosdk.network.downloadToFileWithProgress
import com.testm.demosdk.util.FileUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*

abstract class SoundViewModel: ViewModel() {
    abstract val fileList: LiveData<List<SoundItemUi>>
    abstract val isRefreshing: LiveData<Boolean>
    abstract fun refreshSounds(url: String)
}

class SoundViewModelImpl(private val api: Api,
                         private val fileUtil: FileUtil,
                         private val appContext: Context)
    : SoundViewModel() {

    companion object {
        val TAG = SoundViewModel::class.java.simpleName
        const val SOUNDS_FOLDER = "/sounds"
    }

    override val isRefreshing: MutableLiveData<Boolean> = MutableLiveData()
    override val fileList : MutableLiveData<List<SoundItemUi>> = MutableLiveData()

    var myFiles: List<SoundItemUi> = listOf()

    override fun refreshSounds(url: String){

        isRefreshing.postValue(true)

        viewModelScope.launch (Dispatchers.IO){
            val soundList = try {
                api.getSounds(url = url)
            } catch (e: RuntimeException) {
                null
            }

            if (soundList != null) {
                //filter distinct sounds by url
                val filterSounds = soundList.distinctBy { it.url }

                //download all files
                Log.d(TAG, "refreshSounds: before download files")

                myFiles = filterSounds.map { SoundItemUi("",0,it) }
                fileList.postValue(myFiles)
                downloadFiles(filterSounds) {

                        myFiles = myFiles?.map { mapItem ->
                            if(mapItem.soundItem.id == it.soundItem.id){ it } else { mapItem }}

                        fileList.postValue(myFiles)
                }

                myFiles = myFiles.filter { it.progress == 100 }
                fileList.postValue(myFiles)

                Log.d(TAG, "refreshSounds: after download files")

                isRefreshing.postValue(false)
            }
        }
    }

    private suspend fun downloadFiles(files: List<SoundItem>, downloadProgress: (SoundItemUi) -> Unit) {

        // create sound file folder and delete old files if exist
        val folder = appContext.filesDir.path + SOUNDS_FOLDER;
        fileUtil.deleteFilesFromDirectory(folder)
        fileUtil.createDirectory(folder)

        supervisorScope {


            files.forEach { item ->

                val url = item.url

                try {
                    // download file
                    val responseBody = api.getUrl(url)

                    async() {

                        // save file to disk
                        val filePath = folder + "/" + UUID.randomUUID() + URLUtil.guessFileName(
                            url,
                            null,
                            null
                        )

                        responseBody.downloadToFileWithProgress(filePath, item.id).collect {
                            when (it) {
                                is Download.Progress -> {
                                    Log.d("SoundViewModel", "download: " + it.percent)
                                    downloadProgress(SoundItemUi("", it.percent, item))
                                }
                                is Download.Finished -> {
                                    Log.d("SoundViewModel", "download finished: ")
                                    downloadProgress(SoundItemUi(filePath, 100, item))
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "downloadFiles: exepption:" + e)
                }
            }
        }
    }
}