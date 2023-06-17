package com.example.journeymate.models

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JourneymateAPI {
    companion object {
        val instance = Retrofit.Builder().baseUrl("https://ee46-177-240-173-253.ngrok-free.app/api/v1/").addConverterFactory(GsonConverterFactory.create()).client(
            OkHttpClient().newBuilder().build()
        ).build().create(JourneymateAPI::class.java)
    }

    //USER
    @GET("user/{idUser}")
    suspend fun getUser(@Path("idUser") username: String) : UserResponse

    @POST("user")
    suspend fun addNewUser(@Body user: User) : UserResponse

    //ROUTINE


    //TASK

}