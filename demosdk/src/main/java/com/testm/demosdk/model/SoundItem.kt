package com.testm.demosdk.model

import com.google.gson.annotations.SerializedName

data class SoundItem (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
    )

