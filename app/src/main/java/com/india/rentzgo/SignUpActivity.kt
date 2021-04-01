package com.india.rentzgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText

    private lateinit var submitBtn: Button

    private lateinit var progressBar: ProgressBar

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firstName = findViewById(R.id.signUp_firstname)
        lastName = findViewById(R.id.signUp_lastname)
        email = findViewById(R.id.signUp_email)
        password = findViewById(R.id.signUp_password)
        confirmPassword = findViewById(R.id.signUp_confirmPassword)

        submitBtn = findViewById(R.id.signup_btn)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        submitBtn.setOnClickListener {
            firebaseAuth = FirebaseAuth.getInstance()


            val firstNameString = email.text.toString().trim()
            val lastNameString = email.text.toString().trim()
            val emailString = email.text.toString().trim()
            val passwordString = password.text.toString().trim()
            val confirmPasswordString = password.text.toString().trim()

            if (isValidInputFields()) {
                progressBar.visibility = View.VISIBLE

                firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener{
                        if (it.isSuccessful) {
                            Toast.makeText(this, "User successfully created", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this, HousesLists::class.java))
                        } else {
                            Toast.makeText(this, "Error while creating user", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun isValidInputFields(): Boolean {
        if (TextUtils.isEmpty(firstName.text.toString().trim())) {
            firstName.setError("Please Enter your Firstname")
        } else if (TextUtils.isEmpty(lastName.text.toString().trim())) {
            lastName.setError("Please Enter your Lastname")
        } else if (TextUtils.isEmpty(firstName.text.toString().trim())) {
            email.setError("Email can not be empty")
        } else if (TextUtils.isEmpty(password.text.toString().trim())) {
            password.setError("Password can not be null")
        } else if (TextUtils.isEmpty(confirmPassword.text.toString().trim())) {
            confirmPassword.setError("Please confirm your password")
        } else if (!(confirmPassword.text.toString().trim()
                .equals(password.text.toString().trim()))
        ) {
            confirmPassword.setError("Password didn't match")
        } else {
            return true;
        }
        return false;
    }
}