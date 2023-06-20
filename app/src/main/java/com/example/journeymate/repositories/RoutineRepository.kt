package com.example.journeymate.repositories

import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.Routine

class RoutineRepository (
    private var api : JourneymateAPI
    ) {

    suspend fun getRoutines() : Result<List<Routine>>{
        return try{
            val routinesRecovered = api.getRoutines()
            Result.success(routinesRecovered.response);
        } catch (e: Exception){
            Result.failure(e);
        }
    }


    suspend fun getFavoritesRoutines(username : String) : Result<List<Routine>>{
        return try{
            val routinesRecovered = api.getFavoritesRoutines(username)
            Result.success(routinesRecovered.response);
        } catch (e: Exception){
            Result.failure(e);
        }
    }
}