package com.example.journeymate.repositories

import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.User

class UserRepository(
    private var api: JourneymateAPI
) {
    suspend fun getUser() : Result<User>{
        return try{
            val userRecovered = api.getUser("AxelMorales175");
            Result.success(userRecovered.result);
        }catch(e:Exception){
            Result.failure(e);
        }
    }
}