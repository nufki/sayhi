package ch.innuvation.sayhi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SayhiApplication

fun main(args: Array<String>) {
	runApplication<SayhiApplication>(*args)
}
