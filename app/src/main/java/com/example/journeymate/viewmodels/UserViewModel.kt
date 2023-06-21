package com.example.journeymate.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journeymate.MainActivity
import com.example.journeymate.models.Encription
import com.example.journeymate.models.HttpStatusCode
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

class UserViewModel : ViewModel() {

    private val userRepository: UserRepository = UserRepository(JourneymateAPI.instance)

    fun performAsyncTask(email: String, password: String, callback: (Int) -> Unit) {
        viewModelScope.launch {
           val codeResult =  authenticateUser(email, password)
            callback(codeResult)
        }
    }

    suspend fun authenticateUser(email: String, password: String): Int {
        var codeResult = HttpStatusCode.FORBIDDEN.code
        try {
            val responseResult = userRepository.login(email, Encription.getHashedPassword(password))
            if (responseResult.isSuccess) {
                val response = responseResult.getOrNull()
                if (response != null) {
                    if (response.code == HttpStatusCode.OK.code) {
                        codeResult = response.code
                        MainActivity.instance.userLogged = response.result
                    }
                }
            }
        } catch (e: Exception) {
            codeResult = HttpStatusCode.INTERNAL_SERVER_ERROR.code
        }
        return codeResult
    }
}
