package com.example.journeymate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.journeymate.R
import com.example.journeymate.models.HttpStatusCode
import com.example.journeymate.models.UserRegisterModel
import com.example.journeymate.viewmodels.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var editTextName : EditText
    private lateinit var editTextLastname : EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var editTextRepeatPassword : EditText
    private lateinit var buttonSignUp : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_register, container, false)
        editTextName = view.findViewById(R.id.editTextName)
        editTextLastname = view.findViewById(R.id.editTextLastName)
        editTextAge = view.findViewById(R.id.editTextAge)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editPassword)
        editTextRepeatPassword = view.findViewById(R.id.editPasswordRepeated)
        buttonSignUp = view.findViewById(R.id.registerSignUpButton)

        setupSignUpButton()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val returnLoginButton = requireActivity().findViewById<ImageView>(R.id.returnButton);
        val navController = findNavController()
        //nageva al dar clic al boton regresar
        returnLoginButton.setOnClickListener{
            navController.popBackStack(R.id.loginFragment, false)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.loginFragment, false)
        }

        val signInButton = requireActivity().findViewById<TextView>(R.id.signInButton)
        signInButton.setOnClickListener {
            navController.popBackStack(R.id.loginFragment, false)
        }
    }

    private fun setupSignUpButton(){
        val navController = findNavController()
        buttonSignUp.setOnClickListener(){
            val nameUser = editTextName.text.toString()
            val lastnameUser = editTextLastname.text.toString()

            val newUser = UserRegisterModel(
                name = nameUser,
                lastname = lastnameUser,
                age = editTextAge.text.toString().toInt(),
                email = editTextEmail.text.toString(),
                password = editTextPassword.text.toString(),
                username = UserViewModel.createUsername(nameUser, lastnameUser)
            )

            val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            var codeResult = HttpStatusCode.INTERNAL_SERVER_ERROR.code

            userViewModel.performAsyncSignUp(newUser) {
                    result ->
                    codeResult = result

                    if(codeResult == HttpStatusCode.OK.code){
                        showMessage("Bienvenido a la aventura!")
                        navController.navigate(R.id.loginFragment)
                    }

                    if(codeResult == HttpStatusCode.PREVIUSLY_REGISTERED.code){
                        showMessage("Ese correo ya ha sido registrado previamente")
                    }

                    if(codeResult == HttpStatusCode.INTERNAL_SERVER_ERROR.code){
                        showMessage("Estamos presentando errores intentalo mas tarde")
                    }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun validateEntryData(): Boolean{
        val result = true

        return result
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {

            }
    }
}