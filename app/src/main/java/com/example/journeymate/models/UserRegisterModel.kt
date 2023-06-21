package com.example.journeymate.models

data class UserRegisterModel(
    val username: String,
    val name:String,
    val lastname: String,
    val email: String,
    val age: Int,
    val password : String
)
