package com.example.backend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "images")
data class Image(
    @Id
    val id: String? = null,
    val email: String,
    val uploaderName: String,
    val path: String
)
