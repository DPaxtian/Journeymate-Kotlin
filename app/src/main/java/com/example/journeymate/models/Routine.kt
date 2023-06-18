package com.example.journeymate.models

data class Routine(
    val __v: Int,
    val _id: String,
    val city: String,
    val country: String,
    val followers: Int,
    val label_category: String,
    val name: String,
    val routine_comments: List<Any>,
    val routine_creator: String,
    val routine_description: String,
    val state_country: String,
    val tasks: List<Any>,
    val town: String,
    val valorations: List<Any>,
    val visibility: String
)