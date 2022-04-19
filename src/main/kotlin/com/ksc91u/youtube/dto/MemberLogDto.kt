package com.ksc91u.youtube.dto

import java.time.Instant

data class MemberLogDto(
    val memberId: String,
    val ip: String,
    val ipCountry: String,
    val userAgent: String,
    val device: String,
    val os: String,
    val lastTime: Instant,
    val deviceId: String
) {
    companion object {
        val defaultInstance = MemberLogDto(
            "",
            "",
            "",
            "",
            "",
            "",
            Instant.MIN,
            ""
        )
    }
}

data class MemberLogSimpleDto(
    val memberId: String,
    val ip: String,
    val userAgent: String,
    val device: String,
    val deviceId: String
)