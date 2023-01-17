package com.example.flags.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.flags.*
import com.google.firebase.auth.FirebaseAuth

class FragmentAuthorization: Fragment(R.layout.activity_authorization) {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button








    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (FirebaseAuth.getInstance().currentUser != null) {
            goToProfile()
        }

        val navController = Navigation.findNavController(view)


        val buttonReset = view.findViewById<Button>(R.id.button5)
        buttonReset.setOnClickListener {
            val reset =
                FragmentAuthorizationDirections.actionFragmentAuthorizationToFragmentResetPassword()
            navController.navigate(reset)
        }


        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress2)
        editTextPassword = view.findViewById(R.id.editTextTextPassword3)
        buttonLogin = view.findViewById(R.id.button4)






        buttonLogin.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty()) {
                editTextEmail.error = "შეავსეთ ველები"
                return@setOnClickListener
            } else if (email.length < 8) {
                editTextEmail.error = "მეილი არასწორადაა შეყვანილი"
                return@setOnClickListener
            } else if (!email.contains("@") || !email.contains(".")) {
                editTextEmail.error = "მეილი არასწორადაა შეყვანილი"
            } else if (password.isEmpty()) {
                editTextPassword.error = "შეავსეთ პაროლი!"
                return@setOnClickListener
            } else if (password.length < 8) {
                editTextPassword.error = "პაროლი უნდა შეიცავდეს მინიმუმ 8 სიმბოლოს"
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(
                            this@FragmentAuthorization.requireContext(),
                            com.example.flags.Menu::class.java
                        )
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        Toast.makeText(
                            this@FragmentAuthorization.requireActivity(),
                            "Error!",
                            Toast.LENGTH_SHORT
                        ).show()



                    }

                }



        }



    }

    private fun goToProfile() {
        val intent = Intent(this@FragmentAuthorization.requireContext(), com.example.flags.Menu::class.java)
        startActivity(intent)
        activity?.finish()
    }

}







