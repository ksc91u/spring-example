package com.ksc91u.youtube

import com.apple.eawt.Application
import com.ksc91u.youtube.controller.MainController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class YoutubeApplication

fun main(args: Array<String>) {
	runApplication<YoutubeApplication>(*args)
}

