package com.india.rentzgo.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.rentzgo.data.Users.Users
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.india.rentzgo.data.Property
import com.india.rentzgo.data.base.Properties

class DBUtils : AppCompatActivity() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    fun saveUser(context: Context) {
        val signInAccount: GoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(context)!!
        val user = Users(
            signInAccount.givenName.toString(),
            signInAccount.familyName.toString(),
            signInAccount.email.toString(),
            signInAccount.photoUrl.toString()
        )
        firebaseFirestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser.uid
        val path = firebaseFirestore.collection("Users").document(userId)
        val batch = firebaseFirestore.batch()
        batch.set(path, user)
        batch.commit().addOnSuccessListener {
        }
    }

    fun saveProperty(property: Property, index: Int) {
        firebaseFirestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser.uid
        val path =
            firebaseFirestore.collection("Properties/${index}/${Properties.INDIVIDUALROOM}").document("BasicInfo")
        val path1 = FirebaseDatabase.getInstance().getReference().child("Users")
//        val g = GeoFire(path1)
//        g.setLocation(
//            "$index",
//            GeoLocation(26.8361984, 75.7729436),
//            GeoFire.CompletionListener { key, error -> })
        var batch = firebaseFirestore.batch()
        batch.set(path, property)
        batch.commit().addOnSuccessListener {

        }
    }

}