package com.testm.demosdk.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testm.demosdk.model.SoundItem
import com.testm.demosdk.model.SoundItemUi
import com.testm.demosdk.network.Api
import com.testm.demosdk.util.FileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*
import java.io.File
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*
import javax.inject.Inject

abstract class SoundViewModel: ViewModel() {
    abstract val fileList: LiveData<List<SoundItemUi>>
    abstract val isRefreshing: LiveData<Boolean>
    abstract fun refreshSounds(url: String)
}


@HiltViewModel
class SoundViewModelImpl @Inject constructor(private val api: Api,
                                             private val fileUtil: FileUtil,
                                             @ApplicationContext private val appContext: Context)
    : SoundViewModel() {

    companion object {
        val TAG = SoundViewModel::class.java.simpleName
        const val SOUNDS_FOLDER = "/sounds"
    }

    override val isRefreshing: MutableLiveData<Boolean> = MutableLiveData()
    override val fileList : MutableLiveData<List<SoundItemUi>> = MutableLiveData()

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
                val items = downloadFiles(filterSounds)

                fileList.postValue(items)
                isRefreshing.postValue(false)
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "error: " + exception)
    }

    private suspend fun downloadFiles(files: List<SoundItem>) : List<SoundItemUi> =
        withContext(Dispatchers.IO) {
            supervisorScope {

                val deferrerList: MutableList<Deferred<SoundItemUi>> = mutableListOf()

                // create sound file folder and delete old files if exist
                val folder = appContext.filesDir.path + SOUNDS_FOLDER;
                fileUtil.deleteFilesFromDirectory(folder)
                fileUtil.createDirectory(folder)


                files.forEach { item ->
                    val url = async(exceptionHandler) {
                        val url = item.url

                        // download file
                        val requestBody = api.download(url)

                        // save file to disk
                        val filePath = folder + "/" + UUID.randomUUID() + URLUtil.guessFileName(
                            url,
                            null,
                            null
                        )

                        fileUtil.saveFileToDisk(requestBody.byteStream(), filePath)

                        // return Sound item with the file uri
                        SoundItemUi(Uri.fromFile(File(filePath)).toString(), item)
                    }
                    deferrerList.add(url)
                }

                // wait for all request, if fails filter don't add to list
                deferrerList.mapNotNull { try { it.await() } catch (e: Exception) { null } }
            }
    }


}