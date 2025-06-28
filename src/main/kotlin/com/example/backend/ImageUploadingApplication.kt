package com.example.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ImageUploadingApplication

fun main(args: Array<String>) {
    runApplication<ImageUploadingApplication>(*args)
}
