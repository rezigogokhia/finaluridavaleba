package com.example.flags.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.flags.R
import com.google.firebase.auth.FirebaseAuth

class FragmentResetPassword: Fragment(R.layout.activity_reset_password) {



    private lateinit var editTextEmail: EditText
    private lateinit var buttonSend: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)







        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress3)
        buttonSend = view.findViewById(R.id.button6)



        buttonSend.setOnClickListener {

            val email = editTextEmail.text.toString()

            if (email.isEmpty()) {
                editTextEmail.error = "შეიყვანეთ მეილი"
                return@setOnClickListener
            }
            else if (!email.contains("@")) {
                editTextEmail.error = "მეილი არასწორადაა შეყვანილი"
                return@setOnClickListener
            }
            else if (!email.contains(".")) {
                editTextEmail.error = "მეილი არასწორადაა შეყვანილი"
                return@setOnClickListener
            }
            else if (email.length<8) {
                editTextEmail.error = "მეილი არასწორადაა შეყვანილი"
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val buttonReset = view.findViewById<Button>(R.id.button5)
                        buttonReset.setOnClickListener {
                            val authorization = FragmentResetPasswordDirections.actionFragmentResetPasswordToFragmentAuthorization()
                            navController.navigate(authorization)

                        }

                    }
                }

        }
    }
}
