package com.example.journeymate.fragments

import RecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.journeymate.MainActivity
import com.example.journeymate.R
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.Routine
import com.example.journeymate.repositories.RetrofitHelper
import com.google.gson.JsonObject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class FavoritesFragment : Fragment() {

    lateinit var username : String
    val favoritesRoutines : MutableList<Routine> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(MainActivity.instance.userLogged != null){
            username = MainActivity.instance.userLogged!!.username
        } else {
            username = ""
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        if(!username.equals("")){
            val jouneymateApi = RetrofitHelper.getInstance().create(JourneymateAPI::class.java)
            val progressBar = view.findViewById<ProgressBar>(R.id.favorites_progress)

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    progressBar.visibility = View.VISIBLE

                    val result = async { jouneymateApi.getFavoritesRoutines(username) }
                    val routinesObtained = result.await().response

                    favoritesRoutines.removeAll(routinesObtained)
                    favoritesRoutines.addAll(routinesObtained )
                    if(favoritesRoutines != null){
                        progressBar.visibility = View.GONE
                    }

                    val recycler : RecyclerView = view.findViewById(R.id.favorites_recycler)
                    val adapter : RecyclerViewAdapter = RecyclerViewAdapter()

                    adapter.RecyclerViewAdapter(favoritesRoutines, view.context)
                    adapter.onRoutineClick = {
                        val bundle = Bundle()
                        bundle.putParcelable("routine", it)
                        findNavController().navigate(R.id.action_favoritesFragment_to_routineDetailsFragment, bundle)
                    }

                    adapter.onFollowClick = {
                        viewLifecycleOwner.lifecycleScope.launch {
                            val jsonObject = JsonObject()
                            jsonObject.addProperty("username", username)
                            jsonObject.addProperty("idRoutine", it._id)
                            val followResult = async { jouneymateApi.unfollowRoutine(jsonObject) }
                            val resultReturn = followResult.await().code

                            if(resultReturn == 200){
                                Toast.makeText(view.context, "Rutina eliminada", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_favoritesFragment_self)
                            }
                        }
                    }

                    recycler.hasFixedSize()
                    recycler.layoutManager = LinearLayoutManager(view.context)
                    recycler.adapter = adapter
                } catch (e: Exception) {
                    progressBar.visibility = View.GONE
                    Log.e("Error", e.toString())
                }
            }
        } else {
            findNavController().navigate(R.id.action_favoritesFragment_to_loginFragment)
        }



        return view
    }

}