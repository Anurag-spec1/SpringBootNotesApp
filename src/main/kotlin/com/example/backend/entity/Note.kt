package com.example.backend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection="Notes")
data class Note(
    @Id
    val id: String?=null,
    val email: String,
    val title: String,
    val content: String,
    val timestamp: String
)
