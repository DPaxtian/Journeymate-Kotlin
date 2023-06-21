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
        val instance: JourneymateAPI = Retrofit.Builder().baseUrl("https://bdf0-177-240-174-205.ngrok-free.app/api/v1/").addConverterFactory(GsonConverterFactory.create()).client(
            OkHttpClient().newBuilder().build()
        ).build().create(JourneymateAPI::class.java)
    }

    //USER
    @POST("login")
    suspend fun login(@Body userLogin: UserLoginModel) : UserResponse

    @GET("user/{idUser}")
    suspend fun getUser(@Path("idUser") username: String) : UserResponse

    @POST("user")
    suspend fun addNewUser(@Body userToAdd: UserRegisterModel) : UserResponse

    //ROUTINE
    @GET("routines")
    suspend fun getRoutines() : RoutineResponse

    @GET("routines/routinesFollowed/{username}")
    suspend fun getFavoritesRoutines(@Path("username") username: String) : RoutineResponse

    //TASK

}