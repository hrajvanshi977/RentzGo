package com.india.rentzgo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.firebase.geofire.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderApi
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import org.imperiumlabs.geofirestore.GeoFirestore

class HousesMapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    com.google.android.gms.location.LocationListener {

    var current = this
    private lateinit var mMap: GoogleMap
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLastLocation: Location


    private var list = mutableListOf<String>()

    var radius = 5.0

    var isDriverFound: Boolean = false

    var driverId: String = ""

    var count: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses_map_activty)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        findViewById<Button>(R.id.getNearby).setOnClickListener {

            list = mutableListOf<String>()

            radius = 5.0

            isDriverFound = false

            driverId = ""

            count = 0;

            getClosestHouse();

            Log.i("Size", list.size.toString())
        }
    }


    private fun getClosestHouse() {
        /*var reference = FirebaseDatabase.getInstance().getReference("Houses")

        var geoFire = GeoFire(reference)

        var geoQuery = geoFire.queryAtLocation(
            GeoLocation(
                26.8187543,
                75.7926861
            ), radius
        )

        geoQuery.removeAllListeners()

        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String?, location: GeoLocation?) {
                Log.i("House Found", key.toString())
//                Log.i("Name", key.toString())
                var address : List<Address> = Geocoder(current).getFromLocation(location!!.latitude, location.longitude, 1)

                for(element in address) {
//                    Log.i("City name", element.postalCode)
                }
            }

            override fun onKeyExited(key: String?) {
            }

            override fun onKeyMoved(key: String?, location: GeoLocation?) {
            }

            override fun onGeoQueryReady() {
                if (isDriverFound) {
                    isDriverFound = false
                    getClosestHouse()
                } else {
                    radius++
                }
            }

            override fun onGeoQueryError(error: DatabaseError?) {
            }
        })  */

        //////////////////////////////////////////////////////////////////////////

        var geoFirestore = GeoFirestore(FirebaseFirestore.getInstance().collection("Houses"))

        var geoQuery = geoFirestore.queryAtLocation(GeoPoint(26.8187543, 75.7926861), 3.6)

        geoQuery.addGeoQueryDataEventListener(object : GeoQueryDataEventListener,
            org.imperiumlabs.geofirestore.listeners.GeoQueryDataEventListener {
            override fun onDocumentEntered(documentSnapshot: DocumentSnapshot, location: GeoPoint) {
                var email : String = documentSnapshot.get("email").toString()

                Log.i("House Found", email)
//                Log.i("Name", key.toString())
            }

            override fun onDocumentExited(documentSnapshot: DocumentSnapshot) {
            }

            override fun onDocumentMoved(documentSnapshot: DocumentSnapshot, location: GeoPoint) {
            }

            override fun onDocumentChanged(documentSnapshot: DocumentSnapshot, location: GeoPoint) {
            }

            override fun onDataEntered(dataSnapshot: DataSnapshot?, location: GeoLocation?) {
            }

            override fun onDataExited(dataSnapshot: DataSnapshot?) {
            }

            override fun onDataMoved(dataSnapshot: DataSnapshot?, location: GeoLocation?) {
            }

            override fun onDataChanged(dataSnapshot: DataSnapshot?, location: GeoLocation?) {
            }

            override fun onGeoQueryReady() {
            }

            override fun onGeoQueryError(error: DatabaseError?) {
            }

            override fun onGeoQueryError(exception: Exception) {
            }

        })











        //////////////////////////////////////////////////////////////////////////

//        geoQuery.addGeoQueryDataEventListener(object : GeoQueryDataEventListener {
//            override fun onDataEntered(dataSnapshot: DataSnapshot?, location: GeoLocation?) {
////                var data = dataSnapshot!!.getValue(String::class.java)
//                Log.i("House Found", "data.toString()")
//            }
//
//            override fun onDataExited(dataSnapshot: DataSnapshot?) {
//            }
//
//            override fun onDataMoved(dataSnapshot: DataSnapshot?, location: GeoLocation?) {
//            }
//
//            override fun onDataChanged(dataSnapshot: DataSnapshot?, location: GeoLocation?) {
//            }
//
//            override fun onGeoQueryReady() {
//                if (isDriverFound) {
//                    isDriverFound = false
//                    getClosestHouse()
//                } else {
//                    radius++
//                }
//            }
//
//            override fun onGeoQueryError(error: DatabaseError?) {
//            }
//        })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        buildGoogleApiClient()

        mMap.setMyLocationEnabled(true)

    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mGoogleApiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onLocationChanged(location: Location) {
        mLastLocation = location

        val latlng = LatLng(location.latitude, location.longitude)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17F))

//        var userId: String = FirebaseAuth.getInstance().currentUser.uid
//
//        var reference = FirebaseDatabase.getInstance().getReference("Houses")
//
//        var geoFire = GeoFire(reference)
//
//        geoFire.setLocation(userId, GeoLocation(location.latitude, location.longitude))

    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.setInterval(1000)
        mLocationRequest.setFastestInterval(1000)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient,
            mLocationRequest,
            this
        )


    }

    override fun onConnectionSuspended(p0: Int) {
    }

//    override fun onStop() {
//        super.onStop()
//        var userId: String = FirebaseAuth.getInstance().currentUser.uid
//
//        var reference = FirebaseDatabase.getInstance().getReference("Houses")
//
//        var geoFire = GeoFire(reference)
//
//        geoFire.removeLocation(userId)
//    }

}
