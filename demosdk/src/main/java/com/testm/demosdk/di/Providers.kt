package com.testm.demosdk.di

import android.content.Context
import com.testm.demosdk.network.Api
import com.testm.demosdk.util.FileUtil

object Providers {

    lateinit var appContext: Context

    val Api: Api by lazy {
        val retrofit = BaseRetrofit().get()
        retrofit.create(com.testm.demosdk.network.Api::class.java)
    }

    val fileUtil: FileUtil by lazy {
        FileUtil()
    }

}