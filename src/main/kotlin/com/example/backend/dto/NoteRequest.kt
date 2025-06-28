package com.example.backend.dto

data class NoteRequest(
    val email: String,
    val content: String,
    val title: String,
)
