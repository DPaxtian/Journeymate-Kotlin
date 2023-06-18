package com.example.journeymate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.journeymate.MainActivity
import com.example.journeymate.R
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bar)
        navBar.visibility = View.GONE
        val returnLoginButton = requireActivity().findViewById<ImageView>(R.id.returnLoginButton);
        val navController = findNavController()
        //nageva al dar clic al boton regresar
        returnLoginButton.setOnClickListener{
            navController.navigate(R.id.explorerFragment)
            showNavBar()
        }

        //navega al dar clic al boton registrar
        val signUpButton = requireActivity().findViewById<TextView>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        showNavBar()
    }

    private fun showNavBar(){
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bar)
        view.visibility = View.VISIBLE
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}