package com.example.journeymate.fragments

import android.net.RouteInfo
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.journeymate.R
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.Routine
import com.example.journeymate.repositories.RetrofitHelper
import com.example.journeymate.repositories.RoutineRepository
import com.example.journeymate.ui.RecyclerViewAdapter
import com.example.journeymate.viewmodels.RoutineViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception


class ExplorerFragment : Fragment() {
    val routines : MutableList<Routine> = ArrayList()
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

                val recycler : RecyclerView = view.findViewById(R.id.cards_recycler)
                val adapter : RecyclerViewAdapter = RecyclerViewAdapter()

                adapter.RecyclerViewAdapter(routines, view.context)

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