package com.example.backend.service

import com.example.backend.dto.ImageResponse
import com.example.backend.dto.NoteRequest
import com.example.backend.dto.NoteResponse
import com.example.backend.dto.UserResponse
import com.example.backend.entity.Image
import com.example.backend.entity.Note
import com.example.backend.entity.User
import com.example.backend.repository.ImageRepository
import com.example.backend.repository.NoteRepository
import com.example.backend.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class MainService(
    private val userRepo: UserRepository,
    private val imageRepo: ImageRepository,
    private val noterepo: NoteRepository
) {
    private val encoder = BCryptPasswordEncoder()
    private val uploadDir = "uploads"

    fun signup(name: String, email: String, password: String): Any {
        if (userRepo.findByEmail(email) != null) return "Email already exists"
        val user = User(name = name, email = email, password = encoder.encode(password))
        userRepo.save(user)
        return UserResponse(user.name, user.email)
    }

    fun signin(email: String, password: String): Any {
        val user = userRepo.findByEmail(email) ?: return "User not found"
        return if (encoder.matches(password, user.password)) "Login successful" else "Incorrect password"
    }

    fun uploadImage(email: String, file: MultipartFile): String {
        val user = userRepo.findByEmail(email) ?: return "User not found"

        try {
            val uploadDir = System.getProperty("user.dir") + File.separator + "uploads"
            File(uploadDir).mkdirs()

            val fileName = "${System.currentTimeMillis()}_${file.originalFilename}"
            val fullPath = "$uploadDir${File.separator}$fileName"

            println("Uploading to: $fullPath")

            file.transferTo(File(fullPath))

            imageRepo.save(Image(email = email, uploaderName = user.name, path = fullPath))
            return "Image uploaded"
        } catch (e: Exception) {
            e.printStackTrace()
            return "File upload failed: ${e.message}"
        }
    }



    fun getAllImages(): List<ImageResponse> {
        return imageRepo.findAll().map {
            val bytes = File(it.path).readBytes()
            val base64 = java.util.Base64.getEncoder().encodeToString(bytes)
            ImageResponse(name = it.uploaderName, email = it.email, image = base64)
        }
    }

    fun addNote(request: NoteRequest) : String {
        val user = userRepo.findByEmail(request.email) ?: return "User not found"
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val note = Note(email = user.email, title = request.title, content = request.content, timestamp = timestamp)
        noterepo.save(note)
        return "Note added"
    }

    fun getUserNotes(email: String):List<NoteResponse>{
        return noterepo.findAllByEmail(email).map {
            NoteResponse(title =it.title, content = it.content, timestamp = it.timestamp)
        }
    }
}