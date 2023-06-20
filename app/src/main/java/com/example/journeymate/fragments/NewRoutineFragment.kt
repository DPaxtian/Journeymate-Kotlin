package com.example.journeymate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.journeymate.R

class NewRoutineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_routine, container, false)

        val categories = listOf(
            "Turismo",
            "Gastronomía",
            "Compras",
            "Entretenimiento",
            "Actividades al aire libre",
            "Vida nocturna",
            "Arte y cultura",
            "Naturaleza y espacios verdes",
            "Actividades educativas",
            "Deportes y recreación",
            "Relajación y bienestar",
            "Eventos culturales",
            "Aventura",
            "Actividades acuaticas",
            "Educación y aprendizaje"
        )
        val categoriesSpinner = view.findViewById<Spinner>(R.id.categories_spinner)
        val categoriesAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, categories)
        categoriesSpinner.adapter = categoriesAdapter

        val visibility = listOf(
            "Publica",
            "Privada"
        )
        val visibilitySpinner = view.findViewById<Spinner>(R.id.visibility_spinner)
        val visibilityAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, visibility)
        visibilitySpinner.adapter = visibilityAdapter

        return view
    }

}