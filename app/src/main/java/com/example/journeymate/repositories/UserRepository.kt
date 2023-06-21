package com.example.journeymate.repositories

import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.User
import com.example.journeymate.models.UserLoginModel
import com.example.journeymate.models.UserResponse

class UserRepository(
    private var api: JourneymateAPI
) {
    suspend fun getUser() : Result<User>{
        return try{
            val userRecovered = api.getUser("AxelMorales175")
            Result.success(userRecovered.result);
        }catch(e:Exception){
            Result.failure(e);
        }
    }

    suspend fun login(email:String, password:String) : Result<UserResponse>{
        val userToLogin = UserLoginModel(
            email = email,
            password = password
        )
        return try{
            val response = api.login(userToLogin)
            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}