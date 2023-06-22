package com.example.journeymate.models

data class UserResponse(
    val code: Int,
    val msg: String,
    val result: User
)