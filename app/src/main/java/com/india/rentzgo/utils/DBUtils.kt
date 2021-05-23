package com.india.rentzgo.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
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
import single.LatitudeLongitude
import single.NearbyProperties
import java.util.*

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
        var addresses: List<android.location.Address?> = getCurrentLocationAddress()
        Log.i("Here->", "${addresses}")
        firebaseFirestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser.uid
        val countryName = addresses.get(0)!!.countryName
        val adminArea = addresses.get(0)!!.adminArea
        val subAdminArea = addresses.get(0)!!.subAdminArea

                Log.i("Info", "${countryName}, ${adminArea}, ${subAdminArea}")
                val path =
            firebaseFirestore.collection("Properties/${index}/${Properties.INDIVIDUALROOM}")
                .document("BasicInfo")

            var path1 : Any?
            path1 = if(subAdminArea == null) {
                FirebaseDatabase.getInstance().getReference().child("Properties")
                    .child(countryName).child(adminArea)
            } else {
                FirebaseDatabase.getInstance().getReference().child("Properties")
                .child(countryName).child(adminArea).child(subAdminArea)
        }

        val g = GeoFire(path1)
        g.setLocation(
            "$index",
            GeoLocation(26.9146122, 75.8137346),
            GeoFire.CompletionListener { key, error -> })
        var batch = firebaseFirestore.batch()
        batch.set(path, property)
        batch.commit().addOnSuccessListener {

        }
    }

    private fun getCurrentLocationAddress(): List<Address?> {
        Log.i("NearbyProperties", "${NearbyProperties.list.size}")
        val geoCode = Geocoder(this, Locale.getDefault())

        Log.i(
            "Latitude and Longitude",
            "${LatitudeLongitude.latitude}, ${LatitudeLongitude.longitude}"
        )
        var addresses: List<android.location.Address?> =
            geoCode.getFromLocation(26.9146122,75.8137346, 1)

//        Log.i("My address", addresses.get(0)!!.getAddressLine(0))
        Log.i("Full address", addresses.toString())
        return addresses
    }

}