package com.ksc91u.youtube.dto

data class ShareLinkDto(
        val id: Int,
        val hashKey: String,
        val routeUrl: String,
        val title: String,
        val content: String,
        val imgUrl: String
)