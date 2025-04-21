package com.example.supabasetest.model

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: Int? = null,
    val name: String,
    val mark: Double
)
