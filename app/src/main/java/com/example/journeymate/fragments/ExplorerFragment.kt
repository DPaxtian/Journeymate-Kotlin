package com.example.journeymate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.journeymate.R


class ExplorerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explorer, container, false)
        val button = view.findViewById<Button>(R.id.ButtonDetails)

        button.setOnClickListener{
            findNavController().navigate(R.id.action_explorerFragment_to_routineDetailsFragment)
        }

        return view
    }


}