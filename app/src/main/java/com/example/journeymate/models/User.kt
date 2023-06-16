package com.example.journeymate.models

data class User(
    val _id: String,
    val age: Int,
    val city: String,
    val country: String,
    val email: String,
    val followed_routines: List<Any>,
    val lastname: String,
    val name: String,
    val password: String,
    val phone_number: String,
    val role: String,
    val routines_created: List<RoutinesCreated>,
    val user_description: String,
    val username: String,
    val users_followed: List<Any>
)