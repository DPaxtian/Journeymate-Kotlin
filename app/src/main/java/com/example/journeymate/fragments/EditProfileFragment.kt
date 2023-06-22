package com.example.journeymate.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.journeymate.R
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.User
import com.example.journeymate.repositories.RetrofitHelper
import com.google.gson.JsonObject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {
    lateinit var userInfo : User
    lateinit var username : String

    var editName : EditText? = null
    var editLastname : EditText? = null
    var editEmail : EditText? = null
    var editPhone : EditText? = null
    var editCountry : EditText? = null
    var editCity : EditText? = null
    var editDescription : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = this.arguments
        username = args?.getString("username")!!
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        val progressbar = view.findViewById<ProgressBar>(R.id.editprofileprogress)
        val jouneymateApi = RetrofitHelper.getInstance().create(JourneymateAPI::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                progressbar.visibility = View.VISIBLE
                val result = async { jouneymateApi.getUser(username) }
                userInfo = result.await().result
                if(userInfo != null){
                    progressbar.visibility = View.GONE
                }

                editName = view.findViewById<EditText>(R.id.editTextName)
                editLastname = view.findViewById<EditText>(R.id.editTextLastname)
                editEmail = view.findViewById<EditText>(R.id.editTextEmail)
                editPhone = view.findViewById<EditText>(R.id.editTextPhoneNumber)
                editCountry = view.findViewById<EditText>(R.id.editTextCountry)
                editCity = view.findViewById<EditText>(R.id.editTextCity)
                editDescription = view.findViewById<EditText>(R.id.editTextDescription)

                editName!!.setText(userInfo.name)
                editLastname!!.setText(userInfo.lastname)
                editEmail!!.setText(userInfo.email)
                editPhone!!.setText(userInfo.phone_number)
                editCountry!!.setText(userInfo.country)
                editCity!!.setText(userInfo.city)
                editDescription!!.setText(userInfo.user_description)

                val updateProfile = view.findViewById<Button>(R.id.button_saveprofile)
                updateProfile.setOnClickListener {
                    if(validateFields()){
                        val jsonObject = JsonObject()
                        jsonObject.addProperty("name", editName!!.text.toString())
                        jsonObject.addProperty("lastname", editLastname!!.text.toString())
                        jsonObject.addProperty("phone_number", editPhone!!.text.toString())
                        jsonObject.addProperty("username", userInfo.username)
                        jsonObject.addProperty("email", editEmail!!.text.toString())
                        jsonObject.addProperty("age", userInfo.age)
                        jsonObject.addProperty("city", editCity!!.text.toString())
                        jsonObject.addProperty("country", editCountry!!.text.toString())
                        jsonObject.addProperty("user_description", editDescription!!.text.toString())

                        viewLifecycleOwner.lifecycleScope.launch {
                            val result = async { jouneymateApi.updateUser(jsonObject) }
                            val statusCode = result.await().code

                            if(statusCode == 200){
                                Toast.makeText(view.context, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                                val bundle = Bundle()
                                bundle.putString("username", username)
                                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment, bundle)
                            }
                        }
                    }
                }



            } catch (e: Exception) {
                progressbar.visibility = View.GONE
                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                Log.e("Exception", e.toString())
            }
        }

        return view
    }

    private fun validateFields() : Boolean{
        var isValid : Boolean = true

        if(editName!!.length() == 0){
            editName!!.error = "Ingrese su nombre"
            isValid = false
        }
        if(editLastname!!.length() == 0){
            editLastname!!.error = "Ingrese su apellido"
            isValid = false
        }
        if(editEmail!!.length() == 0){
            editEmail!!.error = "Ingrese su email"
            isValid = false
        }
        if(editPhone!!.length() == 0){
            editPhone!!.error = "Ingrese su telefono"
            isValid = false
        }
        if(editCountry!!.length() == 0){
            editCountry!!.error = "Ingrese su pais"
            isValid = false
        }
        if(editCity!!.length() == 0){
            editCity!!.error = "Ingrese su ciudad"
            isValid = false
        }
        if(editDescription!!.length() == 0){
            editDescription!!.error = "Ingrese una descripción"
            isValid = false
        }

        return isValid
    }

}