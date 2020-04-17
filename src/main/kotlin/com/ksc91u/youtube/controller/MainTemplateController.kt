package com.ksc91u.youtube.controller

import com.google.gson.Gson
import com.ksc91u.youtube.dao.UserDao
import com.ksc91u.youtube.dao.UserDaoImpl
import org.apache.catalina.util.URLEncoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.util.*
import java.util.zip.Inflater
import java.util.zip.InflaterOutputStream
import javax.servlet.http.HttpServletRequest


data class ShareObject(
        val l: String, //redirect link
        val n0: String,  //streamer name
        val n1: String,  //user name
        val i0: String, //streamer image
        val i1: String, //user image
        val t: String   //title
)

@Controller
class MainTemplateController(@Autowired val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    private val dao: UserDao = UserDaoImpl(namedParameterJdbcTemplate)

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun listMappings(model: Model): String {
        val results = dao.findAll()
        model.addAttribute("mappings", results)
        return "list"
    }

    @RequestMapping(value = ["/create"], method = [RequestMethod.GET])
    fun create(model: Model): String {
        return "create"
    }

    //curl -X POST --data "routeUrl=http://yahoo.com.tw&title=CCCCC&content=content&imgUrl=http://google.com"  http://localhost:8080/add
    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun addMapping(model: Model,
                   @RequestParam routeUrl: String,
                   @RequestParam title: String,
                   @RequestParam content: String,
                   @RequestParam imgUrl: String): String {
        val hashKey = dao.addMapping(routeUrl, title, content, imgUrl)
        model.addAttribute("key", hashKey)
        return "ok"
    }

    @RequestMapping(value = ["/share/{id}"], method = [RequestMethod.GET])
    fun share(model: Model,
              @PathVariable("id") hashId: String,
              request: HttpServletRequest): String {

        val userAgentInfo = request.getHeader("User-Agent");
        val shareLinkDto = dao.getMapping(hashId)

        model.addAttribute("title", shareLinkDto.title)
        model.addAttribute("content", shareLinkDto.content)
        model.addAttribute("imgUrl", shareLinkDto.imgUrl)
        model.addAttribute("routeUrl", shareLinkDto.routeUrl)

        model.addAttribute("userAgent", userAgentInfo)
        model.addAttribute("notFb", userAgentInfo.contains("facebook").not())

        return "wave"
    }

    @RequestMapping(value = ["/b/{base64}"], method = [RequestMethod.GET])
    fun shareB64(model: Model,
                 @PathVariable("base64") base64: String,
                 request: HttpServletRequest): String {

        val userAgentInfo = request.getHeader("User-Agent");

        val bytes = Base64.getUrlDecoder().decode(base64)
        val stream2 = ByteArrayOutputStream()
        val decompresser = Inflater(true)
        val inflaterOutputStream = InflaterOutputStream(stream2, decompresser)
        inflaterOutputStream.write(bytes)
        inflaterOutputStream.close()
        val jsonString: String = String(stream2.toByteArray())

        val shareObj = Gson().fromJson<ShareObject>(jsonString, ShareObject::class.java)

        model.addAttribute("title", shareObj.t)
        model.addAttribute("content", "${shareObj.n1} 分享了 ${shareObj.n0} 的 直播")
        //https://i.ksc91u.info/unsafe/filters:watermark(https://live.staticflickr.com/6027/5946613249_0172090fce_b.jpg,50,50,10,20,20)/https%3A%2F%2Fi.imgur.com%2FWXoAhTa.png

        model.addAttribute("imgUrl", "https://i.ksc91u.info/unsafe/filters:watermark(${URLEncoder.DEFAULT.encode(shareObj.i1, Charset.defaultCharset())},50,50,10,20,20)/${URLEncoder.DEFAULT.encode(shareObj.i0, Charset.defaultCharset())}")
        model.addAttribute("routeUrl", shareObj.l)

        model.addAttribute("userAgent", userAgentInfo)
        model.addAttribute("notFb", userAgentInfo.contains("facebook").not())

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
        model.addAttribute("imgUrl", pic)
        model.addAttribute("content", "快來 Wave 一起抽")
        model.addAttribute("userAgent", userAgentInfo)
        model.addAttribute("notFb", userAgentInfo.contains("facebook").not())
        model.addAttribute("routeUrl", "https://www.wave.com.tw")

        return "wave"
    }
}
