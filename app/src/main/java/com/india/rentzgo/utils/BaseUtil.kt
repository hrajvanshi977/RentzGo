package com.india.rentzgo.utils

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

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
}