package com.ksc91u.youtube

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver



@Bean
fun freemarkerViewResolver(): FreeMarkerViewResolver? {
    val resolver = FreeMarkerViewResolver()
    resolver.isCache = true
    resolver.setPrefix("")
    resolver.setSuffix(".ftl")
    return resolver
}