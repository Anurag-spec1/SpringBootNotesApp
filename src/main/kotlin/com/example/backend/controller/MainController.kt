package com.example.backend.controller

import com.example.backend.dto.ImageResponse
import com.example.backend.dto.NoteRequest
import com.example.backend.entity.SigninRequest
import com.example.backend.entity.SignupRequest
import com.example.backend.service.MainService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping
class MainController(private val service: MainService) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): Any {
        return service.signup(request.name, request.email, request.password)
    }

    @PostMapping("/signin")
    fun signin(@RequestBody request: SigninRequest): Any{
        return service.signin(request.email,request.password)
    }

    @PostMapping("/upload")
    fun uploadImage(
        @RequestParam email: String,
        @RequestParam file: MultipartFile
    ) = service.uploadImage(email, file)

    @GetMapping("/images")
    fun getAllImages(): List<ImageResponse> = service.getAllImages()

    @PostMapping("/addNote")
    fun addNote(@RequestBody request: NoteRequest) = service.addNote(request)

    @GetMapping("/getNotes")
    fun getNotes(@RequestParam email: String) = service.getUserNotes(email)
}