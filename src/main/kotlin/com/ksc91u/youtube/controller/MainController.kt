package com.ksc91u.youtube.controller

import com.ksc91u.youtube.dto.Car
import org.springframework.web.bind.annotation.*


@RestController
class MainController {
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

}