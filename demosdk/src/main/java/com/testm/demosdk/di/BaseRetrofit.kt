package com.testm.demosdk.di

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseRetrofit {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.google.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun get() = retrofit

}