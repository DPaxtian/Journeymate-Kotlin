package com.example.journeymate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.journeymate.R
import com.example.journeymate.models.Routine
import org.w3c.dom.Text

class RoutineDetailsFragment : Fragment() {
    lateinit var routineDetails : Routine


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = this.arguments
        routineDetails = args?.getParcelable("routine")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_routine_details, container, false)

        val routineTitle = view.findViewById<TextView>(R.id.routinedetails_title)
        val routineLocation = view.findViewById<TextView>(R.id.routinedetails_location)
        val routineCategory = view.findViewById<TextView>(R.id.routinedetails_category)
        val routineBudget = view.findViewById<TextView>(R.id.routinedetails_budget)
        val routineCreator = view.findViewById<TextView>(R.id.routinedetails_creator)
        val routineFollowers = view.findViewById<TextView>(R.id.routinedetails_followers)
        val routineDescription = view.findViewById<TextView>(R.id.routinedetails_description)


        routineTitle.text = routineDetails.name
        routineLocation.text = routineDetails.city + ", " + routineDetails.country
        routineCategory.text = routineDetails.label_category
        routineBudget.text = "$" + 1200
        routineCreator.text = routineDetails.routine_creator
        routineFollowers.text = routineDetails.followers.toString()
        routineDescription.text = routineDetails.routine_description

        return view
    }

}