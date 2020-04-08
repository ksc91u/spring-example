package com.ksc91u.youtube.bean

import freemarker.cache.ClassTemplateLoader
import freemarker.cache.MultiTemplateLoader
import freemarker.cache.NullCacheStorage
import freemarker.cache.TemplateLoader
import freemarker.template.Configuration
import freemarker.template.TemplateException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import java.io.IOException

@org.springframework.context.annotation.Configuration
class FreeMarkerConfig{
    @Bean
    @Throws(IOException::class, TemplateException::class)
    @Autowired
    fun freeMarkerConfigurer(context: ApplicationContext): FreeMarkerConfigurer? {
        val configurer: FreeMarkerConfigurer = object : FreeMarkerConfigurer() {
            @Throws(IOException::class, TemplateException::class)
            override fun postProcessConfiguration(config: Configuration) {
                val classTplLoader = ClassTemplateLoader(context.classLoader, "/template")
                val baseMvcTplLoader = ClassTemplateLoader(FreeMarkerConfigurer::class.java, "") //TODO tratar de acceder a spring.ftl de forma directa
                val mtl = MultiTemplateLoader(arrayOf<TemplateLoader>(
                        classTplLoader,
                        baseMvcTplLoader
                ))
                config.templateLoader = mtl
                config.cacheStorage = NullCacheStorage()
            }
        }
        configurer.setDefaultEncoding("UTF-8")
        configurer.setPreferFileSystemAccess(false)
        return configurer
    }
}
