package com.example.journeymate.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.journeymate.MainActivity
import com.example.journeymate.models.Encription
import com.example.journeymate.models.HttpStatusCode
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.UserRegisterModel
import com.example.journeymate.repositories.UserRepository
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback
import kotlin.random.Random

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

    companion object{
        fun createUsername(name:String, lastName: String): String{
            val nameSplit = name.split(" ")
            val lastNameSplit = lastName.split(" ")

            val firstName = nameSplit.firstOrNull()
            val firstLastName = lastNameSplit.firstOrNull()

            val randomNum = Random.nextInt(1, 1001)

            val username = "$firstName$firstLastName$randomNum"

            return username
        }
    }

    suspend fun signUp(newUser: UserRegisterModel): Int{
        var codeResult = HttpStatusCode.INTERNAL_SERVER_ERROR.code
        try{
            val responseResult = userRepository.signUp(newUser)
            if (responseResult.isSuccess) {
                val response = responseResult.getOrNull()
                if (response != null) {
                    if(response.code == 0)
                        codeResult = HttpStatusCode.OK.code
                    else
                        codeResult = response.code
                }
            }
            if(responseResult.isFailure){
                val response = responseResult.getOrNull()
                if (response != null) {
                    codeResult = HttpStatusCode.PREVIUSLY_REGISTERED.code
                }
            }

        }catch (e:Exception){
            codeResult = HttpStatusCode.INTERNAL_SERVER_ERROR.code
        }
        return codeResult
    }

    fun performAsyncSignUp(newUser : UserRegisterModel, callback: (Int) -> Unit){
        viewModelScope.launch {
            val codeResult = signUp(newUser)
            callback(codeResult)
        }
    }

}
