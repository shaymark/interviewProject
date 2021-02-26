package com.testm.demosdk.network

import com.testm.demosdk.model.SoundItem
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.util.concurrent.Flow

interface Api {

    @GET
    suspend fun getSounds(@Url url: String) : List<SoundItem>

    @GET
    suspend fun download(@Url url: String) : ResponseBody

    @Streaming // allows streaming data directly to fs without holding all contents in ram
    @GET
    suspend fun getUrl(@Url url: String) : ResponseBody
}