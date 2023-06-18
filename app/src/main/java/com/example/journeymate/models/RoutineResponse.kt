package com.example.journeymate.models

data class RoutineResponse(
    val code: Int,
    val msg: String,
    val response: List<Routine>
)