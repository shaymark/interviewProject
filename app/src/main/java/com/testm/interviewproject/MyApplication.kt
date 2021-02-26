package com.testm.interviewproject

import android.app.Application
import com.testm.demosdk.DemoSdk

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        DemoSdk.initSdk(this)
    }
}