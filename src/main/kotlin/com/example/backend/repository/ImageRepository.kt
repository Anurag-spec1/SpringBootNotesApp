package com.example.backend.repository

import com.example.backend.entity.Image
import org.springframework.data.mongodb.repository.MongoRepository

interface ImageRepository : MongoRepository<Image, String>