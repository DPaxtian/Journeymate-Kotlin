package com.example.journeymate.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.journeymate.R
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.User
import com.example.journeymate.repositories.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    lateinit var userInfo: User
    lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = this.arguments
        username = args?.getString("username")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val progressbar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val jouneymateApi = RetrofitHelper.getInstance().create(JourneymateAPI::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                progressbar.visibility = View.VISIBLE
                val result = async { jouneymateApi.getUser(username) }
                userInfo = result.await().result
                if(userInfo != null){
                    progressbar.visibility = View.GONE
                }

                val nameText = view.findViewById<TextView>(R.id.text_name)
                val usernameText = view.findViewById<TextView>(R.id.text_username)
                val emailText = view.findViewById<TextView>(R.id.text_email)
                val locationText = view.findViewById<TextView>(R.id.text_location)
                val ageText = view.findViewById<TextView>(R.id.text_age)
                val descriptionText = view.findViewById<TextView>(R.id.text_description)

                nameText.text = userInfo.name + " " + userInfo.lastname
                usernameText.text = userInfo.username
                emailText.text = userInfo.email
                locationText.text = userInfo.city + ", " + userInfo.country
                ageText.text = userInfo.age.toString()
                descriptionText.text = userInfo.user_description
            } catch (e: Exception) {
                progressbar.visibility = View.GONE
                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                Log.e("Exception", e.toString())
            }
        }

        val editProfileButton = view.findViewById<ImageButton>(R.id.button_editprofile)
        editProfileButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", username)
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
        }
        return view
    }

}