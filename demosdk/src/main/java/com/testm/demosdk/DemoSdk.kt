package com.testm.demosdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.testm.demosdk.di.Providers
import com.testm.demosdk.view.SoundActivity

class DemoSdk(appContext: Context) {

    init {
        Providers.appContext = appContext
    }

    fun showData(activity: Activity, requestCode: Int) {
        openNewScreen(activity, requestCode)
    }

    private fun openNewScreen(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(getIntent(activity), requestCode)
    }

    private fun getIntent(context: Context): Intent = Intent(context, SoundActivity::class.java)

}