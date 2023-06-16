package com.example.journeymate.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.User
import com.example.journeymate.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel (
    private val repository: UserRepository = UserRepository(JourneymateAPI.instance)
) : ViewModel(){
    init {
        viewModelScope.launch {
            repository.getUser().onSuccess {user ->
                println(user.email);
            }.onFailure {
                println("hubo un error");
            }
        }
    }
}