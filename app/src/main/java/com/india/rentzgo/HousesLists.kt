package com.india.rentzgo

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.india.rentzgo.ui.doPost.PostFragment
import com.india.rentzgo.ui.home.HomeFragment
import com.india.rentzgo.ui.myads.MyAdsFragment
import com.india.rentzgo.ui.notifications.NotificationFragment
import com.india.rentzgo.ui.profile.ProfileFragment
import single.LatitudeLongitude
import single.NearbyProperties
import java.util.*
import kotlin.collections.ArrayList


class HousesLists : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    var list = ArrayList<String>()
    private val homeFragment = HomeFragment()
    private val myAdsFragment = MyAdsFragment()
    private val doPostFragment = PostFragment()
    private val notificationFragment = NotificationFragment()
    private val profileFragment = ProfileFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = homeFragment

    lateinit var locationSearch: EditText

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var latitude: Double? = null
    var longitude: Double? = null
    var str = ""
    var a = "1"
    fun getInstance(): HousesLists {
        return this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses_lists)
        println("size of the nearby list is ${NearbyProperties.list.size}")

       /* var sharedPreferences: SharedPreferences = getSharedPreferences("RentzGo", MODE_PRIVATE)
        var gson = Gson()
        var json: String? = sharedPreferences.getString("Houses", "")
        var type = object : TypeToken<ArrayList<String?>?>() {}.type
        var list: ArrayList<String> = gson.fromJson(json, type)
        SharedPreferenceHouseLists.housesLists = list
        */
        Thread {
            loadHouses()
        }.start()
        Places.initialize(this, "AIzaSyCHQzzmlMHXd9Rb9qmLKlUGXZnpIXkKQgE")

        /*locationSearch = findViewById(R.id.locationSearch)

        locationSearch.setOnClickListener {
            var list =
                Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME) as ArrayList<Place.Field>

            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, list).build(this)

            startActivityForResult(intent, 100)

        }
         */
        getCurrentLocationAddress()
        val intentT = intent
        if (intentT.getStringExtra("MyAds").equals("1")) {
            activeFragment = myAdsFragment
        }

        /* if (intentT.getStringExtra("MyAds").equals("1")) {
        fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
            .commit()
        activeFragment = myAdsFragment
    }
   */
        Thread {
            showFragment(activeFragment)
            initListeners()
        }.start()

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
        } else {
            val signInAccount: GoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)!!
            if (signInAccount != null) {
                Log.i("FullName", signInAccount.displayName.toString())
                Toast.makeText(this, "User Added Successfully", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun showFragment(activeFragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            if (activeFragment == homeFragment) {
                add(R.id.nav_host_fragment, homeFragment, getString(R.string.title_home)).show(
                    homeFragment
                )
            } else {
                add(R.id.nav_host_fragment, homeFragment, getString(R.string.title_home)).hide(
                    homeFragment
                )
            }

            if (activeFragment == myAdsFragment) {
                add(R.id.nav_host_fragment, myAdsFragment, getString(R.string.title_myAds)).show(
                    myAdsFragment
                )
            } else {
                add(R.id.nav_host_fragment, myAdsFragment, getString(R.string.title_myAds)).hide(
                    myAdsFragment
                )
            }

            if (activeFragment == doPostFragment) {
                add(R.id.nav_host_fragment, doPostFragment, getString(R.string.title_doPost)).show(
                    doPostFragment
                )
            } else {
                add(R.id.nav_host_fragment, doPostFragment, getString(R.string.title_doPost)).hide(
                    doPostFragment
                )
            }
            if (activeFragment == notificationFragment) {
                add(
                    R.id.nav_host_fragment,
                    notificationFragment,
                    getString(R.string.title_notifications)
                ).show(notificationFragment)
            } else {
                add(
                    R.id.nav_host_fragment,
                    notificationFragment,
                    getString(R.string.title_notifications)
                ).hide(notificationFragment)
            }

            if (activeFragment == profileFragment) {
                add(
                    R.id.nav_host_fragment,
                    profileFragment,
                    getString(R.string.title_profile)
                ).show(
                    profileFragment
                )
            } else {
                add(
                    R.id.nav_host_fragment,
                    profileFragment,
                    getString(R.string.title_profile)
                ).hide(
                    profileFragment
                )
            }
        }.commit()
    }

    private fun addFragment() {
        fragmentManager.beginTransaction().apply {
            add(R.id.nav_host_fragment, homeFragment, getString(R.string.title_home)).show(
                homeFragment
            )
            add(R.id.nav_host_fragment, myAdsFragment, getString(R.string.title_myAds)).hide(
                myAdsFragment
            )
            add(R.id.nav_host_fragment, doPostFragment, getString(R.string.title_doPost)).hide(
                doPostFragment
            )
            add(
                R.id.nav_host_fragment,
                notificationFragment,
                getString(R.string.title_notifications)
            ).hide(notificationFragment)
            add(
                R.id.nav_host_fragment,
                profileFragment,
                getString(R.string.title_profile)
            ).hide(
                profileFragment
            )
        }.commit()
    }
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(data!!)

        }
    }*/

    private fun initListeners() {
        var bottomViewNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomViewNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
                        .commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.navigation_myads -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(myAdsFragment)
                        .commit()
                    activeFragment = myAdsFragment
                    true
                }
                R.id.navigation_doPost -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(doPostFragment)
                        .commit()
                    activeFragment = doPostFragment
                    postActivity()
                    true
                }
                R.id.navigation_notifications -> {
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(notificationFragment).commit()
                    activeFragment = notificationFragment
                    true
                }
                R.id.navigation_profile -> {
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(profileFragment)
                        .commit()
                    activeFragment = profileFragment
                    true
                }
                else -> false
            }
            true
        }
    }

    private fun postActivity() {
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent);
    }

    private fun getCurrentLocationAddress() {
        val geoCode = Geocoder(this, Locale.getDefault())

        Log.i(
            "Latitude and Longitude",
            "${LatitudeLongitude.latitude}, ${LatitudeLongitude.longitude}"
        )
        var addresses: List<android.location.Address?> =
            geoCode.getFromLocation(28.659576, 77.172284, 1)

//        Log.i("My address", addresses.get(0)!!.getAddressLine(0))
        Log.i("Full address", addresses.toString())
    }


    private fun loadHouses() {
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
                    Toast.makeText(this, "${latitude}-${longitude}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
