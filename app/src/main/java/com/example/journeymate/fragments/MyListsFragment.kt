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
import com.example.journeymate.ui.MyListsViewAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyListsFragment : Fragment() {
    lateinit var username : String
    val myroutines : MutableList<Routine> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(MainActivity.instance.userLogged != null) {
            username = MainActivity.instance.userLogged!!.username
        } else {
            username = ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_lists, container, false)

        if(!username.equals("")){
            val journeymateApi = RetrofitHelper.getInstance().create(JourneymateAPI::class.java)

            val progressBar = view.findViewById<ProgressBar>(R.id.mylists_progress)

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    progressBar.visibility = View.VISIBLE

                    val result = async { journeymateApi.getCreatedRoutines(username) }
                    val routinesObtained = result.await().response
                    myroutines.removeAll(routinesObtained)
                    myroutines.addAll(routinesObtained)
                    if(myroutines != null){
                        progressBar.visibility = View.GONE
                    }

                    val recycler : RecyclerView = view.findViewById(R.id.myroutines_recycler)
                    val adapter : MyListsViewAdapter = MyListsViewAdapter()

                    adapter.MyListsViewAdapter(myroutines, view.context)
                    adapter.onRoutineClick = {
                        val bundle = Bundle()
                        bundle.putParcelable("routine", it)
                        findNavController().navigate(R.id.action_myListsFragment_to_routineDetailsFragment, bundle)
                    }

                    adapter.onEditClick = {
                        val bundle = Bundle()
                        bundle.putParcelable("routineToEdit", it)
                        bundle.putBoolean("isEdit", true)
                        findNavController().navigate(R.id.action_myListsFragment_to_newRoutineFragment, bundle)
                    }

                    adapter.onDeleteClick = {
                        viewLifecycleOwner.lifecycleScope.launch {
                            val result = async { journeymateApi.deleteRoutine(it._id) }
                            val statusCode = result.await().code

                            if(statusCode == 200){
                                Toast.makeText(view.context, "Rutina eliminada con éxito", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_myListsFragment_self)
                            } else {
                                Toast.makeText(view.context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    recycler.hasFixedSize()
                    recycler.layoutManager = LinearLayoutManager(view.context)
                    recycler.adapter = adapter
                } catch (e : Exception) {
                    progressBar.visibility = View.GONE
                    Log.e("Error", e.toString())
                }
            }

        } else {
            findNavController().navigate(R.id.action_myListsFragment_to_loginFragment)
        }


        return view
    }


}