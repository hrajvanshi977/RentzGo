package com.india.rentzgo.utils

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import single.NearbyProperties
import java.util.*
import kotlin.collections.ArrayList

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
        val a = (Math.pow(Math.sin(dlat / 2), 2.0)
                + (Math.cos(latitude1) * Math.cos(latitude2)
                * Math.pow(Math.sin(dlon / 2), 2.0)))

        val c = 2 * Math.asin(Math.sqrt(a))

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

}