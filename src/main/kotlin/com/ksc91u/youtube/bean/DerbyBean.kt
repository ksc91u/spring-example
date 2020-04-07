package com.ksc91u.youtube.bean

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DerbyDataSource {
    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
                .driverClassName("org.hsqldb.jdbcDriver")
                .url("jdbc:hsqldb:file:./db/link")
                .username("sa")
                .build()
    }
}