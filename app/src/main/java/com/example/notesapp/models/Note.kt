package com.example.notesapp.models

data class Note(
    val id: Int,
    val title: String,
    val body: String,
    val image: String?
)
