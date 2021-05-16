package com.india.rentzgo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQueryEventListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.india.rentzgo.utils.BaseUtil

class MainActivity : AppCompatActivity(), GeoQueryEventListener, LocationListener {

    private val SPLASH_SCREEN: Long = 2000

    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latitude: Double = 0.0;
    var longitude: Double = 0.0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sharedPreference: SharedPreferences = getSharedPreferences("RentzGo", MODE_PRIVATE)
        var sharedPreferenceEditor: SharedPreferences.Editor = sharedPreference.edit()
        var gson = Gson()
        var json: String = gson.toJson(ArrayList<String>())
        sharedPreferenceEditor.putString("Houses", json).commit()

        getCurrentLocation()
        Handler().postDelayed({
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }, SPLASH_SCREEN)
    }

    override fun onGeoQueryReady() {

    }

    override fun onKeyEntered(key: String?, location: GeoLocation?) {
        Log.d("MyKey", key.toString())
        var sharedPreference: SharedPreferences = getSharedPreferences("RentzGo", MODE_PRIVATE)
        var sharedPreferenceEditor: SharedPreferences.Editor = sharedPreference.edit()
        var json: String? = sharedPreference.getString("Houses", "")
        var gson = Gson()
        val type = object : TypeToken<ArrayList<String?>?>() {}.type
        var list: ArrayList<String> = gson.fromJson(json, type)
        list.add(key.toString())
        json = gson.toJson(list)
        sharedPreferenceEditor.putString("Houses", json).commit()
    }

    override fun onKeyMoved(key: String?, location: GeoLocation?) {
    }

    override fun onKeyExited(key: String?) {
    }

    override fun onGeoQueryError(error: DatabaseError?) {
    }

    private fun getCurrentLocation() {
        val latLong = ArrayList<Double>()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    latitude = it.latitude
                    longitude = it.longitude
                    val radius = 30.0
                    val reference = FirebaseDatabase.getInstance().getReference().child("Users")
                    val geoFire = GeoFire(reference)
                    println("Location-> ${latLong.size}")
                    val geoQuery =
                        geoFire.queryAtLocation(GeoLocation(latitude, longitude), radius)
                    geoQuery.addGeoQueryEventListener(this)
                    Toast.makeText(this, "${latitude}-${longitude} mine", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onLocationChanged(location: Location) {

    }
}