package com.example.journeymate.models

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @PATCH("user")
    suspend fun updateUser(@Body user: JsonObject) : ResponseInt

    //ROUTINE
    @GET("routines")
    suspend fun getRoutines() : RoutineResponse

    @GET("routines/routinesFollowed/{username}")
    suspend fun getFavoritesRoutines(@Path("username") username: String) : RoutineResponse

    @GET("routines/routinesByUser/{username}")
    suspend fun getCreatedRoutines(@Path("username") username: String) : RoutineResponse

    @POST("routines/")
    suspend fun registerRoutine(@Body routine: JsonObject) : ResponseInt

    @POST("routines/followRoutine")
    suspend fun followRoutine(@Body routine: JsonObject) : ResponseInt

    @POST("routines/unfollowRoutine")
    suspend fun unfollowRoutine(@Body routine: JsonObject) : ResponseInt

    @DELETE("routines/{idRoutine}")
    suspend fun deleteRoutine(@Path("idRoutine") idRoutine: String) : ResponseInt

    @PUT("routines/{idRoutine}")
    suspend fun updateRoutine(@Path("idRoutine") idRoutine: String, @Body routine: JsonObject) : ResponseInt

    //TASK

}