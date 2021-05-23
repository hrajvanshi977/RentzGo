package com.india.rentzgo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.india.rentzgo.utils.DBUtils

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: CardView
    private lateinit var googleSignInButton: CardView
    private lateinit var signInButtonTextview: TextView
    private lateinit var googleSignInButtonTextview: TextView
    private lateinit var signProgressBarAnimation: LottieAnimationView
    private lateinit var googleSignProgressBarAnimation: LottieAnimationView
    private lateinit var logInEmail: EditText
    private lateinit var logInPassword: EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 123

    override fun onStart() {
        super.onStart()
        signProgressBarAnimation.visibility = LottieAnimationView.GONE
        googleSignProgressBarAnimation.visibility = LottieAnimationView.GONE
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializationOfAllFields()
        signProgressBarAnimation.visibility = LottieAnimationView.GONE
        googleSignProgressBarAnimation.visibility = LottieAnimationView.GONE
        createRequest()
        findViewById<LinearLayout>(R.id.loginLinearLayout).setOnTouchListener { _: View, _: MotionEvent ->
            closeKeyBoard()
        }

        loginButton.setOnClickListener {
            signProgressBarAnimation.visibility = LottieAnimationView.VISIBLE
            signInButtonTextview.setText("")
            signInWithEmail()
//            DBUtils().saveProperty(PropertyPriceActivity().getPropertyInfo(2), 2)
        }

        googleSignInButton.setOnClickListener {
            googleSignProgressBarAnimation.visibility = LottieAnimationView.VISIBLE
            googleSignInButtonTextview.setText("")
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun createRequest() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    private fun initializationOfAllFields() {
        loginButton = findViewById(R.id.loginButton)
        signProgressBarAnimation = findViewById(R.id.signInProgressBarAnimation)
        signInButtonTextview = findViewById(R.id.signInButtonTextview)
        firebaseAuth = FirebaseAuth.getInstance()
        googleSignInButton = findViewById(R.id.googleSignButton)
        googleSignProgressBarAnimation = findViewById(R.id.googleSignInProgressBarAnimation)
        googleSignInButtonTextview = findViewById(R.id.googleSignButtonTextview)
    }

    private fun closeKeyBoard(): Boolean {
        val view = this.currentFocus
        if (view != null) {
            val inputMethodService =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodService.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        return false
    }

    fun signInWithEmail() {
        if (firebaseAuth.currentUser != null) {
            var intent = Intent(this, HousesLists::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        } else {
            logInEmail = findViewById(R.id.logInEmail)
            logInPassword = findViewById(R.id.logInPassword)

            val logInEmailString = logInEmail.text.toString().trim()
            val logInPasswordString = logInPassword.text.toString().trim()

            if (TextUtils.isEmpty(logInEmailString)) {
                logInEmail.setError("Email is required")
                signProgressBarAnimation.visibility = LottieAnimationView.VISIBLE
                signInButtonTextview.setText("Go")
            } else if (TextUtils.isEmpty(logInPasswordString)) {
                logInPassword.setError("Password is required")
                signProgressBarAnimation.visibility = LottieAnimationView.VISIBLE
                signInButtonTextview.setText("Go")
            } else {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signInWithEmailAndPassword(logInEmailString, logInPasswordString)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Sign in successfully :)", Toast.LENGTH_SHORT)
                                .show()
                            var intent = Intent(this, HousesLists::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            finish()
                        } else {
                            Toast.makeText(this, "Wrong Email or password :(", Toast.LENGTH_SHORT)
                                .show()
                            signProgressBarAnimation.visibility = LottieAnimationView.VISIBLE
                            signInButtonTextview.setText("Go")
                        }
                    }
            }
        }
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
                    Handler().postDelayed({
                        googleSignProgressBarAnimation.visibility = LottieAnimationView.GONE
                        googleSignInButtonTextview.setText("Continue With Google")
                        DBUtils().saveUser(this)
                        val intent = Intent(applicationContext, HousesLists::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }, 100L)

                } else {
                    Toast.makeText(this, "Sorry Auth failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

}