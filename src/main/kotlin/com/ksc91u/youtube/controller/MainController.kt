package com.ksc91u.youtube.controller

import com.ksc91u.youtube.dao.ShortLinkDao
import com.ksc91u.youtube.dao.ShortLinkDaoImpl
import com.ksc91u.youtube.dto.Car
import com.ksc91u.youtube.dto.ConfigDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
class MainRestController(@Autowired val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    private val dao: ShortLinkDao = ShortLinkDaoImpl(namedParameterJdbcTemplate)

    @RequestMapping("/")
    fun index(): String {
        return "Greetings from Spring Boot!"
    }

    @RequestMapping("/car")
    fun car(): Car {
        return Car("black", "toyota")
    }

    //curl -H 'Content-Type: application/json' -X POST http://localhost:8080/destroy -d '{"color":"black","type":"toyota"}'
    @RequestMapping(path = ["/destroy"], method = [RequestMethod.POST])
    @ResponseBody
    fun requestInt(@RequestBody car: Car): String {
        return "${car.type} car destroyed ${car.color}"
    }

    @RequestMapping(value = ["/config/{configKey}/{configValue}"], method = [RequestMethod.POST])
    @ResponseBody
    fun setConfig(
        model: Model,
        @PathVariable("configKey") configKey: String,
        @PathVariable("configValue") configValue: String,
        request: HttpServletRequest
    ): String {
        try {
            dao.setConfig(configKey, configValue)
        } catch (e: Exception) {
            e.printStackTrace()
            return "Exception"
        }
        return "OK"
    }

    @RequestMapping(value = ["/config/{configKey}"], method = [RequestMethod.GET])
    @ResponseBody
    fun getConfig(
        model: Model,
        @PathVariable("configKey") configKey: String,
        request: HttpServletRequest
    ): ConfigDto {
        try {
            return ConfigDto(dao.getConfig(configKey))
        } catch (e: Exception) {
            e.printStackTrace()
            return ConfigDto("")
        }
    }


    @RequestMapping("/deny")
    fun deny(request: HttpServletRequest, response: HttpServletResponse): String {


        val cookie = request.cookies?.find { it.name == "user" }

        val cookieReset = Cookie("user", "").apply { maxAge = 1 }
        response.addCookie(cookieReset)

        return "${cookie?.value ?: "empty"} user deny signing"
    }
}