package com.testm.interviewproject

import android.app.Application
import com.testm.demosdk.DemoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        DemoSdk.initSdk(this)
    }
}