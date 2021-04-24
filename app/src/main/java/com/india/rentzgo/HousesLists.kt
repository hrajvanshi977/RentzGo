package com.india.rentzgo

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
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
import com.india.rentzgo.ui.doPost.PostFragment
import com.india.rentzgo.ui.gallery.GalleryFragment
import com.india.rentzgo.ui.home.HomeFragment
import com.india.rentzgo.ui.myads.MyAdsFragment
import com.india.rentzgo.ui.notifications.NotificationFragment
import com.india.rentzgo.ui.profile.ProfileFragment
import com.india.rentzgo.ui.slideshow.SlideshowFragment

class HousesLists : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var dbReference: DatabaseReference

    lateinit var database: FirebaseDatabase

    lateinit var firebaseAuth: FirebaseAuth

    private val homeFragment = HomeFragment()
    private val myAdsFragment = MyAdsFragment()
    private val doPostFragment = PostFragment()
    private val notificationFragment= NotificationFragment()
    private val profileFragment = ProfileFragment()
    private val fragmentManager  = supportFragmentManager



    private var activeFragment : Fragment  = homeFragment

    lateinit var navController: NavController;


    fun getInstance() : HousesLists {
        return this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses_lists)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        val bottomViewNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
//
//        var navController = findNavController(R.id.nav_host_fragment)
//
//        bottomViewNavigation.setupWithNavController(navController);

        fragmentManager.beginTransaction().apply {
            add(R.id.nav_host_fragment, homeFragment, getString(R.string.title_home)).show(homeFragment)
            add(R.id.nav_host_fragment, myAdsFragment, getString(R.string.title_myAds)).hide(myAdsFragment)
            add(R.id.nav_host_fragment, doPostFragment ,getString(R.string.title_doPost)).hide(doPostFragment)
            add(R.id.nav_host_fragment, notificationFragment, getString(R.string.title_notifications)).hide(notificationFragment)
            add(R.id.nav_host_fragment, profileFragment, getString(R.string.title_profile)).hide(profileFragment)
        }.commit()
        initListeners()
//        setCurrentFragment(firstFragment)


//        makeRec()

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

    private fun initListeners() {
        var bottomViewNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        bottomViewNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
//                    setCurrentFragment(homeFragment)
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.navigation_myads ->  {
//                    setCurrentFragment(myAdsFragment)
                    fragmentManager.beginTransaction().hide(activeFragment).show(myAdsFragment).commit()
                    activeFragment = myAdsFragment
                    true
                }
                R.id.navigation_doPost ->  {
//                    setCurrentFragment(doPostFragment)
                    fragmentManager.beginTransaction().hide(activeFragment).show(doPostFragment).commit()
                    activeFragment = doPostFragment
                    postActivity()
                    true
                }
                R.id.navigation_notifications ->  {
//                    setCurrentFragment(notificationFragment)
                    fragmentManager.beginTransaction().hide(activeFragment).show(notificationFragment).commit()
                    activeFragment = notificationFragment
                    true
                }
                R.id.navigation_profile ->  {
//                    setCurrentFragment(profileFragment)
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
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

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        var bottomViewNavigation = findViewById<AnimatedBottomBar>(R.id.bottom_nav_view)
//
//        bottomViewNavigation.setupWithNavController(menu!!, navController);
//
//        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
//        bottomViewNavigation.setupWithNavController(menu!!, navController)
//        return true
//    }
//    override fun onSupportNavigateUp(): Boolean {
//        navController.navigateUp()
//        return true
//    }


//    private fun setCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(, fragment)
//            commit()
//        }

//    override fun onStart() {
//        super.onStart()
//
//        val recyclerView = findViewById(R.id.home_recycler_view) as RecyclerView
//
//        recyclerView.setHasFixedSize(true)
//
//        var list = ArrayList<Demo>()
//
//
//        for (i in 1..100) {
//            var distance = "Km"
//            list.add(Demo("$i " + distance, "${i * 1000}"))
//        }
//
//        var adapter = HomeClassAdapter(list)
//
//        recyclerView.adapter = adapter
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//    }
}