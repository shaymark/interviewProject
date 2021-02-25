package com.testm.interviewproject

import android.app.Application
import com.testm.demosdk.DemoSdk

class MyApplication: Application() {

    companion object {
        lateinit var demoSDK: DemoSdk
    }

    override fun onCreate() {
        super.onCreate()

        demoSDK = DemoSdk(this)
    }
}