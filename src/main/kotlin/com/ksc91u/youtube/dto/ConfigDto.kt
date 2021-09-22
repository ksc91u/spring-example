package com.ksc91u.youtube.dto

import com.google.gson.annotations.SerializedName


data class ConfigDto(
    @SerializedName("configValue")
    val configValue: String
)