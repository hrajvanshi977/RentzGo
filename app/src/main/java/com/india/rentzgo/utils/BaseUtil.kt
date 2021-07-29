package com.india.rentzgo.utils

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.india.rentzgo.R
import single.NearbyProperties
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*


class PropertiesAndDistance(var distance: Float, var id: String)

internal class SortByDistance : Comparator<PropertiesAndDistance?> {
    override fun compare(o1: PropertiesAndDistance?, o2: PropertiesAndDistance?): Int {
        return if (o1!!.distance > o2!!.distance) {
            1
        } else if (o1!!.distance < o2!!.distance) {
            -1
        } else {
            0
        }
    }
}

class BaseUtil : AppCompatActivity() {
    fun getDistanceInKilometer(latitude: Double, longitude: Double): Int {
        return 0
    }

    fun getCurrentLocation(): ArrayList<Double> {
        val latLong = ArrayList<Double>()
        var fusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                latLong.add(it.latitude)
                latLong.add(it.longitude)
            }.addOnFailureListener {
                Log.e("er in fetching location", "${it.printStackTrace()}")
            }
        }
        return latLong
    }

    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Any {
//        println("lat1->${lat1}")
//        println("lon1->${lon1}")
//        println("lat2->${lat2}")
//        println("lon2->${lon2}")
//        val theta: Double = lon1 - lon2
//        var dist = (sin(deg2rad(lat1))
//                * sin(deg2rad(lat2))
//                + (cos(deg2rad(lat1))
//                * cos(deg2rad(lat2))
//                * cos(deg2rad(theta))))
//        dist = acos(dist)
//        dist = rad2deg(dist)
//        dist *= 60 * 1.1515
//        return dist
        var longitude1 = Math.toRadians(lon1)
        var longitude2 = Math.toRadians(lon2)
        var latitude1 = Math.toRadians(lat1)
        var latitude2 = Math.toRadians(lat2)

        val dlon = longitude2 - longitude1
        val dlat = latitude2 - latitude1
        val a = (Math.sin(dlat / 2).pow(2.0)
                + (cos(latitude1) * cos(latitude2)
                * sin(dlon / 2).pow(2.0)))

        val c = 2 * asin(sqrt(a))

        val r = 6371.0

        return c * r
    }

    private fun rad2deg(deg: Double): Double {
        return (deg * Math.PI / 180.0);
    }

    private fun deg2rad(rad: Double): Double {
        return (rad * 180.0 / Math.PI);
    }

    fun sortByDistance() {
        Collections.sort(NearbyProperties.list, SortByDistance())
    }


    fun logoutFromFirebase() {
        FirebaseAuth.getInstance().signOut()
    }

    fun setLoader(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_layout)
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun logoutFromGoogle(context: Context) {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        ).signOut()
    }
}