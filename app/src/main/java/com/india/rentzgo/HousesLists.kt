package com.india.rentzgo

import android.os.Bundle
import android.os.RecoverySystem
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentzgo.Users.Users
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.india.rentzgo.Users.Demo
import com.india.rentzgo.ui.gallery.GalleryFragment
import com.india.rentzgo.ui.home.HomeFragment
import com.india.rentzgo.ui.slideshow.SlideshowFragment

class HousesLists : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var dbReference: DatabaseReference

    lateinit var database: FirebaseDatabase

    lateinit var firebaseAuth: FirebaseAuth

    val firstFragment = HomeFragment()
    val secondFragment = GalleryFragment()
    val thirdFragment = SlideshowFragment()
    val fourthFragment = SlideshowFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses_lists)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        var bottomViewNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        var navController = findNavController(R.id.nav_host_fragment)

        bottomViewNavigation.setupWithNavController(navController)

//        setCurrentFragment(firstFragment)
//
//        bottomViewNavigation.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.navigation_home -> setCurrentFragment(firstFragment)
//                R.id.navigation_notifications -> setCurrentFragment(secondFragment)
//                R.id.navigation_account -> setCurrentFragment(thirdFragment)
//                R.id.navigation_myads -> setCurrentFragment(fourthFragment)
//            }
//            true
//        }

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {

        } else {
            val signInAccount: GoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)!!
            if (signInAccount != null) {
                val user = Users(
                    signInAccount.displayName.toString(),
                    signInAccount.email.toString(),
                    signInAccount.givenName.toString(),
                    signInAccount.familyName.toString(),
                    signInAccount.photoUrl.toString()
                )

                database = FirebaseDatabase.getInstance()

                dbReference = database.getReference()

                dbReference.child("Users").child(signInAccount.id.toString()).setValue(user);

                Log.i("FullName", signInAccount.displayName.toString())
                Toast.makeText(this, "User Added Successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun setCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.flFragment, fragment)
//            commit()
//        }

    override fun onStart() {
        super.onStart()

        val recyclerView = findViewById(R.id.home_recycler_view) as RecyclerView

        recyclerView.setHasFixedSize(true)

        var list = ArrayList<Demo>()


        for (i in 1..100) {
            var distance = "Km"
            list.add(Demo("$i " + distance, "${i * 1000}"))
        }

        var adapter = HomeClassAdapter(list)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}