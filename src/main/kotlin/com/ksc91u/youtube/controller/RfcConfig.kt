package com.ksc91u.youtube.controller

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RfcConfig {
    @Bean
    fun setRfc(): Int {
        // 指定jre系统属性，允许特殊符号， 如{} 做入参，其他符号按需添加。见 tomcat的HttpParser源码。
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}");
        return 0
    }

}
//————————————————
//版权声明：本文为CSDN博主「Gogym」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/KokJuis/java/article/details/85069916