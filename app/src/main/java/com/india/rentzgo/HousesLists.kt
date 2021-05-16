package com.india.rentzgo

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.india.rentzgo.data.SharedPreferenceHouseLists
import com.india.rentzgo.ui.doPost.PostFragment
import com.india.rentzgo.ui.home.HomeFragment
import com.india.rentzgo.ui.myads.MyAdsFragment
import com.india.rentzgo.ui.notifications.NotificationFragment
import com.india.rentzgo.ui.profile.ProfileFragment


class HousesLists : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    var list = ArrayList<String>()
    private val homeFragment = HomeFragment()
    private val myAdsFragment = MyAdsFragment()
    private val doPostFragment = PostFragment()
    private val notificationFragment = NotificationFragment()
    private val profileFragment = ProfileFragment()
    private val fragmentManager = supportFragmentManager
    var isFound = false;
    private var activeFragment: Fragment = homeFragment

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
        var sharedPreferences : SharedPreferences = getSharedPreferences("RentzGo", MODE_PRIVATE)
        var gson = Gson()
        var json: String? = sharedPreferences.getString("Houses", "")
        var type = object: TypeToken<ArrayList<String?>?>(){}.type
        var list: ArrayList<String> = gson.fromJson(json, type)
        SharedPreferenceHouseLists.housesLists = list
        loadHouses()

        val bottomViewNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

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
            add(R.id.nav_host_fragment, profileFragment, getString(R.string.title_profile)).hide(
                profileFragment
            )
        }.commit()
        initListeners()

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
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
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
