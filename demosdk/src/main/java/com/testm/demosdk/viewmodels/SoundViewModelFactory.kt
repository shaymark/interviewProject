package com.testm.demosdk.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testm.demosdk.di.Providers

class SoundViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SoundViewModel::class.java)) {
            return SoundViewModelImpl(Providers.Api, Providers.fileUtil, Providers.appContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}