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
import com.example.journeymate.R
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.Routine
import com.example.journeymate.repositories.RetrofitHelper
import com.google.gson.Gson
import com.google.gson.JsonObject

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception


class ExplorerFragment : Fragment() {
    val routines : MutableList<Routine> = ArrayList()
    lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        username = "DanielPaxtian69"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explorer, container, false)
        val jouneymateApi = RetrofitHelper.getInstance().create(JourneymateAPI::class.java)

        val progressBar = view.findViewById<ProgressBar>(R.id.explorer_progress)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE

                val result = async { jouneymateApi.getRoutines() }
                val routinesObtained = result.await().response
                routines.removeAll(routinesObtained)
                routines.addAll(routinesObtained )
                if(routines != null){
                    progressBar.visibility = View.GONE
                }


                val recycler : RecyclerView = view.findViewById(R.id.explorer_recycler)
                val adapter : RecyclerViewAdapter = RecyclerViewAdapter()

                adapter.RecyclerViewAdapter(routines, view.context)
                adapter.onRoutineClick = {
                    val bundle = Bundle()
                    bundle.putParcelable("routine", it)
                    findNavController().navigate(R.id.action_explorerFragment_to_routineDetailsFragment, bundle)
                }

                adapter.onFollowClick = {

                    viewLifecycleOwner.lifecycleScope.launch {
                        val jsonObject = JsonObject()
                        jsonObject.addProperty("username", username)
                        jsonObject.addProperty("idRoutine", it._id)
                        val followResult = async { jouneymateApi.followRoutine(jsonObject) }
                        val resultReturn = followResult.await().code

                        if(resultReturn == 200){
                            Toast.makeText(view.context, "Rutina seguida", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_explorerFragment_to_favoritesFragment)
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
        return view
    }
}

