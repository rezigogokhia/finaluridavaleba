package com.example.flags.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.flags.R
import com.google.firebase.auth.FirebaseAuth

class FragmentRegister: Fragment(R.layout.activity_register) {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPassword2: EditText
    private lateinit var editTextButton: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)





        editTextName = view.findViewById(R.id.editTextTextPersonName)
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress)
        editTextPassword = view.findViewById(R.id.editTextTextPassword)
        editTextPassword2 = view.findViewById(R.id.editTextTextPassword2)
        editTextButton = view.findViewById(R.id.button3)



        editTextButton.setOnClickListener{

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val password2 = editTextPassword2.text.toString()
            val name = editTextName.text.toString()


            if (email.isEmpty()) {
                editTextEmail.error = "შეიყვანეთ მეილი!"
                return@setOnClickListener
            }
            else if (name.isEmpty()){
                editTextName.error = "შეიყვანეთ სახელი!"
                return@setOnClickListener
            }
            else if (password != password2) {
                Toast.makeText(this@FragmentRegister.requireActivity(), "პაროლები უნდა ემთხვეოდეს ერთმანეთს!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (email.length<8){
                editTextEmail.error = "მეილი არასწორადაა შეყვანილი"
                return@setOnClickListener
            }
            else if (!email.contains("@") || !email.contains(".")){
                editTextEmail.error = "მეილი არასწორადაა შეყვანილი"
            }
            else if (password.isEmpty()){
                editTextPassword.error = "შეიყვანეთ პაროლი"
                return@setOnClickListener
            }
            else if (password2.isEmpty()) {
                editTextPassword2.error = "შეიყვანეთ პაროლი"
                return@setOnClickListener
            }
            else if(password.length<8){
                editTextPassword.error = "პაროლი უნდა შეიცავდეს მინიმუმ 8 სიმბოლოს"
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val authorization = FragmentRegisterDirections.actionFragmentRegisterToFragmentAuthorization()
                        navController.navigate(authorization)
                    } else {
                        Toast.makeText(this@FragmentRegister.requireActivity(), "Error!", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

}