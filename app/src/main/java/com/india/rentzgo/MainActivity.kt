package com.india.rentzgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.india.rentzgo.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonCancellable

class MainActivity : AppCompatActivity() {

    private lateinit var googleSignInButton: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private val RC_SIGN_IN = 123

    private lateinit var logInEmail: EditText
    private lateinit var logInPassword: EditText

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = firebaseAuth.currentUser

        if (user != null) {
            val housesListsActivity: Intent = Intent(applicationContext, HousesLists::class.java)
            startActivity(housesListsActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        googleSignInButton = findViewById<SignInButton>(R.id.googleSignInButton)
        createRequest();

        googleSignInButton.setOnClickListener {
            signIn();
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = firebaseAuth.currentUser
                        val intent: Intent = Intent(applicationContext, HousesLists::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Sorry Auth failed", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun createRequest() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    fun signUp(view: View) {
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signUpIntent)
    }

    fun signInWithEmail(view: View) {
        logInEmail = findViewById(R.id.logInEmail)
        logInPassword = findViewById(R.id.logInPassword)

        val logInEmailString = logInEmail.text.toString().trim()
        val logInPasswordString = logInPassword.text.toString().trim()


        if (TextUtils.isEmpty(logInEmailString)) {
            logInEmail.setError("Email is required")
        } else if (TextUtils.isEmpty(logInPasswordString)) {
            logInPassword.setError("Password is required")
        } else {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signInWithEmailAndPassword(logInEmailString, logInPasswordString)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Sign in successfully :)", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, HousesLists::class.java))
                        } else {
                            Toast.makeText(this, "Wrong Email or password :(", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
        }
    }

    fun saveSignUpData(view: View) {}
}