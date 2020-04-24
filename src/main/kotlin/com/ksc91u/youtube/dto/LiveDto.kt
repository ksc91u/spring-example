package com.ksc91u.youtube.dto

import com.google.gson.annotations.SerializedName

data class LiveDto(
        @SerializedName("streamer")
        val streamer: UserDto,
        @SerializedName("title")
        val title: String
)

data class UserDto(
        @SerializedName("avatar_url")
        val avatarUrl: String?,
        @SerializedName("background_image_url")
        val backgroundUrl: String?,
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String
)