package com.example.journeymate.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.journeymate.R
import com.example.journeymate.models.JourneymateAPI
import com.example.journeymate.models.Routine
import com.example.journeymate.repositories.RetrofitHelper
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NewRoutineFragment : Fragment() {

    lateinit var username : String
    lateinit var routineToEdit : Routine
    var isEdit : Boolean = false
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

    val visibilityStrings = listOf(
        "Publica",
        "Privada"
    )

    var title : EditText? = null
    var city : EditText? = null
    var country : EditText? = null
    var state : EditText? = null
    var town : EditText? = null
    var category : Spinner? = null
    var visibility : Spinner? = null
    var description : EditText? = null
    var updateButton : Button? = null
    var registerButton : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = "DanielPaxtian69"

        val args = this.arguments
        routineToEdit = args?.getParcelable("routineToEdit")!!
        isEdit = args?.getBoolean("isEdit")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_routine, container, false)
        val journeymateAPI = RetrofitHelper.getInstance().create(JourneymateAPI::class.java)

        val categoriesSpinner = view.findViewById<Spinner>(R.id.categories_spinner)
        val categoriesAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, categories)
        categoriesSpinner.adapter = categoriesAdapter

        val visibilitySpinner = view.findViewById<Spinner>(R.id.visibility_spinner)
        val visibilityAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, visibilityStrings)
        visibilitySpinner.adapter = visibilityAdapter

        title = view.findViewById<EditText>(R.id.newroutine_title)
        city = view.findViewById<EditText>(R.id.newroutine_city)
        country = view.findViewById<EditText>(R.id.newroutine_country)
        state = view.findViewById<EditText>(R.id.newroutine_state)
        town = view.findViewById<EditText>(R.id.newroutine_town)
        category = view.findViewById<Spinner>(R.id.categories_spinner)
        visibility = view.findViewById<Spinner>(R.id.visibility_spinner)
        description = view.findViewById<EditText>(R.id.newroutine_description)
        val selectedVisibility : String = if(visibility!!.selectedItem.equals("Publica")){
            "public"
        } else {
            "private"
        }

        updateButton = view.findViewById(R.id.updateroutine_button)
        registerButton = view.findViewById<Button>(R.id.registerroutine_button)

        if(isEdit){
            setRoutineInfo()
        }

        registerButton!!.setOnClickListener {
            if(ValidateFieds()){
                val jsonObject = JsonObject()
                jsonObject.addProperty("routine_creator", username)
                jsonObject.addProperty("name", title!!.text.toString())
                jsonObject.addProperty("city", city!!.text.toString())
                jsonObject.addProperty("country", country!!.text.toString())
                jsonObject.addProperty("routine_description", description!!.text.toString())
                jsonObject.addProperty("visibility", selectedVisibility)
                jsonObject.addProperty("label_category", category!!.selectedItem.toString())
                jsonObject.addProperty("state_country", state!!.text.toString())
                jsonObject.addProperty("town", town!!.text.toString())

                viewLifecycleOwner.lifecycleScope.launch {
                    try{
                        val response = async { journeymateAPI.registerRoutine(jsonObject) }
                        val statusCode = response.await().code

                        if(statusCode == 200){
                            Toast.makeText(view.context, "Rutina registrada con exito", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_newRoutineFragment_to_myListsFragment)
                        }
                    } catch (e: Exception){
                        Log.e("Exception", e.toString())
                        Toast.makeText(view.context, "Hubo un problema", Toast.LENGTH_SHORT).show()
                    }
                    
                }
            }

        }

        updateButton!!.setOnClickListener {
            if(ValidateFieds()){
                val jsonObject = JsonObject()
                jsonObject.addProperty("routine_creator", username)
                jsonObject.addProperty("name", title!!.text.toString())
                jsonObject.addProperty("city", city!!.text.toString())
                jsonObject.addProperty("country", country!!.text.toString())
                jsonObject.addProperty("routine_description", description!!.text.toString())
                jsonObject.addProperty("visibility", selectedVisibility)
                jsonObject.addProperty("label_category", category!!.selectedItem.toString())
                jsonObject.addProperty("state_country", state!!.text.toString())
                jsonObject.addProperty("town", town!!.text.toString())
                jsonObject.addProperty("followers", routineToEdit.followers)

                viewLifecycleOwner.lifecycleScope.launch {

                    try{
                        val response = async { journeymateAPI.updateRoutine(routineToEdit._id ,jsonObject) }
                        val statusCode = response.await().code

                        if(statusCode == 200){
                            Toast.makeText(view.context, "Rutina actualizada con exito", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_newRoutineFragment_to_myListsFragment)
                        }
                    } catch (e: Exception){
                        Log.e("Exception", e.toString())
                        Toast.makeText(view.context, "Hubo un problema", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        return view
    }

    private fun setRoutineInfo(){
        registerButton!!.visibility = View.INVISIBLE
        updateButton!!.visibility = View.VISIBLE

        title!!.setText(routineToEdit.name)
        city!!.setText(routineToEdit.city)
        country!!.setText(routineToEdit.country)
        state!!.setText(routineToEdit.state_country)
        town!!.setText(routineToEdit.town)
        description!!.setText(routineToEdit.routine_description)
    }


    private fun ValidateFieds() : Boolean {
        var isValid : Boolean = true

        if(title!!.length() == 0){
            title!!.error = "Ingrese un titulo"
            isValid = false
        }
        if(city!!.length() == 0){
            city!!.error = "Ingrese una ciudad"
            isValid = false
        }
        if(country!!.length() == 0){
            country!!.error = "Ingrese un país"
            isValid = false
        }
        if(state!!.length() == 0){
            state!!.error = "Ingrese un estado"
            isValid = false
        }
        if(town!!.length() == 0){
            town!!.error = "Ingrese una localidad"
            isValid = false
        }
        if(description!!.length() == 0){
            description!!.error = "Ingrese una descripción"
            isValid = false
        }


        return isValid
    }

}