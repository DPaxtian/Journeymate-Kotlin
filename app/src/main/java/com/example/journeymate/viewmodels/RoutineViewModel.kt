package com.example.journeymate.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.Routine
import com.example.journeymate.repositories.RoutineRepository
import kotlinx.coroutines.launch

class RoutineViewModel(private val repository: RoutineRepository = RoutineRepository(JourneymateAPI.instance)
) : ViewModel() {

    val _routines = mutableListOf<Routine>()

    fun getRoutines(): List<Routine> {
        return _routines.toList()
    }

    init {
        viewModelScope.launch {
            repository.getRoutines().onSuccess { result ->
                _routines.addAll(result)
            }.onFailure {
                println("mamo")
            }
        }
    }
}