package com.ksc91u.youtube.controller

import com.ksc91u.youtube.dao.UserDao
import com.ksc91u.youtube.dao.UserDaoImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
class MainTemplateController(@Autowired val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    private val dao: UserDao = UserDaoImpl(namedParameterJdbcTemplate)

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun listMappings(model: Model): String {
        val results = dao.findAll()
        println(">>>> $results")
        model.addAttribute("mappings", results)
        return "list"
    }

    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun addMapping(model: Model,
                   @RequestParam routeUrl: String,
                   @RequestParam title: String,
                   @RequestParam content: String,
                   @RequestParam imgUrl: String): String {
        dao.addMapping(routeUrl, title, content, imgUrl)
        return "ok"
    }

    @RequestMapping(value = ["/share/{id}"], method = [RequestMethod.GET])
    fun share(model: Model,
              @PathVariable("id") hashId: String,
              request: HttpServletRequest): String {

        val userAgentInfo = request.getHeader("User-Agent");
        val shareLinkDto = dao.getMapping(hashId)

        model.addAttribute("title", shareLinkDto.title)
        model.addAttribute("character", shareLinkDto.hashKey)
        model.addAttribute("userAgent", userAgentInfo)
        model.addAttribute("fb", userAgentInfo.contains("facebook").not())

        return "wave"
    }


    @RequestMapping(value = ["/index/{name}/{char}"], method = [RequestMethod.GET])
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