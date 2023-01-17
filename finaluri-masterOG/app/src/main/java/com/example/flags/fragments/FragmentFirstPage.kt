package com.example.flags.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.flags.R

class FragmentFirstPage: Fragment(R.layout.fragment_first_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val authorization = FragmentFirstPageDirections.actionFragmentFirstPageToFragmentAuthorization()
            navController.navigate(authorization)
        }
        val button2 = view.findViewById<Button>(R.id.button2)
        button2.setOnClickListener{
            val register = FragmentFirstPageDirections.actionFragmentFirstPageToFragmentRegister()
            navController.navigate(register)
        }

    }
}