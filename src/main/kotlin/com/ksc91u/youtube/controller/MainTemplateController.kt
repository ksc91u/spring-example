package com.ksc91u.youtube.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
class MainTemplateController{
    @RequestMapping(value=arrayOf("/index/{name}/{char}"), method= arrayOf(RequestMethod.GET))
    fun index(model: Model,
              @PathVariable("name") userId: String,
              @PathVariable("char") char: String,
              request: HttpServletRequest): String {

        val userAgentInfo = request.getHeader("User-Agent");
        val names = arrayListOf<String>("智商 180", "韓國瑜珈老師", "台灣天才IT大臣")
        val pics = arrayListOf<String>("https://img.ltn.com.tw/Upload/talk/page/800/2020/03/27/phpdbowmV.jpg",
                "https://img.ltn.com.tw/Upload/news/600/2020/03/27/phpBvG7YQ.jpg",
                "https://img.ltn.com.tw/Upload/news/600/2020/03/08/phpWICJua.jpg")

        val name = names[char.toInt() % 3]
        val pic = pics[char.toInt() % 3]

        model.addAttribute("title", "$userId 在 Wave 抽到了 $name")
        model.addAttribute("character", pic)
        model.addAttribute("userAgent", userAgentInfo)
        model.addAttribute("fb", userAgentInfo.contains("facebook").not())

        return "wave"
    }
}