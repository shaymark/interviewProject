package com.testm.demosdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.testm.demosdk.view.SoundActivity

interface DemoSdk {
    fun showData(activity: Activity, requestCode: Int)
    fun initSdk(appContext: Context)

    companion object: DemoSdk {
        var isInit = false

        private fun checkInit() {
            if(isInit == false) {
                throw (RuntimeException("must init the sdk before using it !!!"))
            }
        }

        override fun initSdk(appContext: Context) {
            isInit = true
        }

        override fun showData(activity: Activity, requestCode: Int) {
            checkInit()

            activity.startActivityForResult(Intent(activity, SoundActivity::class.java), requestCode)
        }

    }
}