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
        val instance = Retrofit.Builder().baseUrl("https://2e05-2806-268-1480-80e5-19b2-949c-ffc5-295b.ngrok-free.app/api/v1/").addConverterFactory(GsonConverterFactory.create()).client(
            OkHttpClient().newBuilder().build()
        ).build().create(JourneymateAPI::class.java)
    }

    //USER
    @GET("user/{idUser}")
    suspend fun getUser(@Path("idUser") username: String) : UserResponse

    @POST("user")
    suspend fun addNewUser(@Body user: User) : UserResponse

    //ROUTINE
    @GET("routines")
    suspend fun getRoutines() : RoutineResponse

    //TASK

}