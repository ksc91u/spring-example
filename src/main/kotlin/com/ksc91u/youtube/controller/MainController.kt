package com.ksc91u.youtube.controller

import com.ksc91u.youtube.dto.Car
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
class MainRestController {
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

    @RequestMapping("/sign")
    fun sign(response: HttpServletResponse,
             @RequestParam("id", required = false, defaultValue = "") userId: String): Unit {
        val cookie = Cookie("user", userId)
        response.addCookie(cookie)
        response.sendRedirect("https://na3.docusign.net/Member/PowerFormSigning.aspx?PowerFormId=95bc56f2-1a3f-4b60-952c-0f6528a80a97&env=na3-eu1&acct=2cb555f3-2272-4cec-b6a3-2a761af0980f&v=2")
    }


    @RequestMapping("/deny")
    fun deny(request: HttpServletRequest, response: HttpServletResponse): String {


        val cookie = request.cookies?.find { it.name == "user" }

        val cookieReset = Cookie("user", "").apply { maxAge = 1 }
        response.addCookie(cookieReset)

        return "${cookie?.value ?: "empty"} user deny signing"
    }
}