package com.india.rentzgo

import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import com.india.rentzgo.utils.BaseUtil
import com.india.rentzgo.utils.PropertiesAndDistance
import single.NearbyProperties


class MainActivity : AppCompatActivity(), LocationListener {

    private val SPLASH_SCREEN: Long = 6000

    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latitude: Double = 0.0;
    var longitude: Double = 0.0;
    private lateinit var firebaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseReference =
            FirebaseDatabase.getInstance().reference.child("Properties").child("India")
                .child("Rajasthan").child("Jaipur")
        getData()
        /*
        var sharedPreference: SharedPreferences = getSharedPreferences("RentzGo", MODE_PRIVATE)
        var sharedPreferenceEditor: SharedPreferences.Editor = sharedPreference.edit()
        var gson = Gson()
        var json: String = gson.toJson(ArrayList<String>())
        sharedPreferenceEditor.putString("Houses", json).commit()
        getCurrentLocation()
         */
        Handler(Looper.myLooper()!!).postDelayed({
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }, SPLASH_SCREEN)
    }

    private fun getData() {
        firebaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("Snapshot", "$snapshot")

                for (datas in snapshot.children) {
                    val latitude = datas.child("l").child("0").value.toString()
                    val longitude = datas.child("l").child("1").value.toString()
                    var id = datas.key.toString()
                    Log.i("msgType", "${latitude}")
                    Log.i("id", "${id}")

                    val distance: Double =
                        BaseUtil().getDistance(
                            26.815192,
                            75.769899,
                            latitude.toDouble(),
                            longitude.toDouble()
                        ) as Double

                    val source = Location("")
                    source.latitude = 26.9012738
                    source.longitude = 75.7275784

                    val destination = Location("")
                    destination.latitude = latitude.toDouble()
                    destination.longitude = longitude.toDouble()

                    val result = FloatArray(10)
                    val from = LatLng(26.8172019, 75.7657745)
                    val to = LatLng(latitude.toDouble(), longitude.toDouble())

                    Location.distanceBetween(26.815262, 75.7677224, 26.9373175, 75.8133372, result)
                    NearbyProperties.clearAllProperties()
                    NearbyProperties.list.add(PropertiesAndDistance(result[0], id))
                    Log.i("distance", "${latitude.toDouble()}, ${result[0]}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    /*
    override fun onGeoQueryReady() {

    }

    override fun onKeyEntered(key: String?, location: GeoLocation?) {
        Log.d("MyKey", key.toString())
//        NearbyProperties.list.add(key.toString())
//        var sharedPreference: SharedPreferences = getSharedPreferences("RentzGo", MODE_PRIVATE)
//        var sharedPreferenceEditor: SharedPreferences.Editor = sharedPreference.edit()
//        var json: String? = sharedPreference.getString("Houses", "")
//        var gson = Gson()
//        val type = object : TypeToken<ArrayList<String?>?>() {}.type
//        var list: ArrayList<String> = gson.fromJson(json, type)
//        list.add(key.toString())
//        json = gson.toJson(list)
//        sharedPreferenceEditor.putString("Houses", json).commit()
    }

    override fun onKeyMoved(key: String?, location: GeoLocation?) {
    }

    override fun onKeyExited(key: String?) {
    }

    override fun onGeoQueryError(error: DatabaseError?) {
    }
*/

    /*private fun getCurrentLocation() {
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
//                    geoQuery.addGeoQueryEventListener(this)
                    Toast.makeText(this, "${latitude}-${longitude} mine", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    } */

    override fun onLocationChanged(location: Location) {

    }
}