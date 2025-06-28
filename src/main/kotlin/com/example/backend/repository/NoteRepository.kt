package com.example.backend.repository

import com.example.backend.entity.Note
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository: MongoRepository<Note, String> {
    fun findAllByEmail(email: String): List<Note>
}