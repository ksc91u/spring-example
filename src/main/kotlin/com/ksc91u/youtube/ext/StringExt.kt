package com.ksc91u.youtube.ext

import org.apache.tomcat.util.codec.binary.Base64
import java.nio.ByteBuffer
import java.util.*

fun String.toUUID(): UUID {
    val byteByffer = ByteBuffer.wrap(Base64(true).decode(this.toByteArray()))
    return UUID(byteByffer.getLong(), byteByffer.getLong())
}

fun String.toUUIDString(): String {
    return this.toUUID().toString()
}