package ch.innuvation.sayhi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SayHiController {

    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello, World!"
    }
}