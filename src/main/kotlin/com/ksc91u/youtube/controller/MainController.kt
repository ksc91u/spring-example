package com.ksc91u.youtube.controller

import com.ksc91u.youtube.dao.MemberDao
import com.ksc91u.youtube.dao.MemberDaoImpl
import com.ksc91u.youtube.dao.ShortLinkDao
import com.ksc91u.youtube.dao.ShortLinkDaoImpl
import com.ksc91u.youtube.dto.Car
import com.ksc91u.youtube.dto.ConfigDto
import com.ksc91u.youtube.dto.MemberLogDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
abstract class MainRestController(
    @Autowired private val dao: ShortLinkDao,
    @Autowired private val memberDao: MemberDao
) {
    private val logger = LoggerFactory.getLogger(MainRestController::class.java)

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
            logger.info("Request setConfig ${request.remoteAddr}, key $configKey, value $configValue")
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
            logger.info("Request getConfig ${request.remoteAddr}, key $configKey")
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

    @RequestMapping("/member/test")
    fun memberTest(request: HttpServletRequest, response: HttpServletResponse): String {
        val uuid = UUID.randomUUID()
        val deviceID = UUID.randomUUID()
        memberDao.addMemberLog(
            MemberLogDto(
                uuid.toString(), "127.0.0.1",
                "TW", "Mozilla", "Pixel 3", "Android 12", Instant.now(),
                deviceID.toString()
            )
        )
        val dto = memberDao.getByMemberDeviceId(uuid.toString(), deviceID.toString())
        return "OK ${dto.toString()}"
    }
}