package com.example.journeymate.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import com.example.journeymate.R
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.User
import com.example.journeymate.repositories.RetrofitHelper
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {
    lateinit var userInfo : User

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
                val result = async { jouneymateApi.getUser("DanielPaxtian69") }
                userInfo = result.await().result
                if(userInfo != null){
                    progressbar.visibility = View.GONE
                }

                val editName = view.findViewById<EditText>(R.id.editTextName)
                val editLastname = view.findViewById<EditText>(R.id.editTextLastname)
                val editEmail = view.findViewById<EditText>(R.id.editTextEmail)
                val editPhone = view.findViewById<EditText>(R.id.editTextPhoneNumber)
                val editCountry = view.findViewById<EditText>(R.id.editTextCountry)
                val editCity = view.findViewById<EditText>(R.id.editTextCity)
                val editDescription = view.findViewById<EditText>(R.id.editTextDescription)

                editName.setText(userInfo.name)
                editLastname.setText(userInfo.lastname)
                editEmail.setText(userInfo.email)
                editPhone.setText(userInfo.phone_number)
                editCountry.setText(userInfo.country)
                editCity.setText(userInfo.city)
                editDescription.setText(userInfo.user_description)


            } catch (e: Exception) {
                progressbar.visibility = View.GONE
                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                Log.e("Exception", e.toString())
            }
        }

        return view
    }

}