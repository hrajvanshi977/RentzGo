package com.india.rentzgo.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQueryEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.india.rentzgo.HomeClassAdapter
import com.india.rentzgo.R
import com.india.rentzgo.data.Property
import com.india.rentzgo.data.SharedPreferenceHouseLists
import com.india.rentzgo.data.base.Properties

class HomeFragment : Fragment(), GeoQueryEventListener {
    private lateinit var homeViewModel: HomeViewModel
    lateinit var fragment: Fragment
    var container: ViewGroup? = null
    var sun = ArrayList<String>()
    var newL = ArrayList<String>()
    var dummy = ArrayList<Property>()
    var res = ArrayList<Property>()
    lateinit var inflater: LayoutInflater
    var adapter = HomeClassAdapter(res)
    lateinit var recyclerView: RecyclerView
    lateinit var manager: LinearLayoutManager
    var current = 0
    var scrolled = 0
    var total = 0
    lateinit var homeProgressBar: ProgressBar
    lateinit var centerHomeProgressBar: ProgressBar
    var index: Int = 0;

    var isScrolling = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        manager = LinearLayoutManager(activity)
        homeProgressBar = root.findViewById(R.id.homeProgressBar)
        centerHomeProgressBar = root.findViewById(R.id.centerHomeProgressBar)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        recyclerView = root.findViewById(R.id.home_recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
                if (recyclerView.adapter?.itemCount!! > 0) {
                    centerHomeProgressBar.visibility = View.GONE
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                current = manager.childCount
                total = adapter.itemCount
                scrolled = manager.findFirstVisibleItemPosition()

                if (isScrolling && (current + scrolled >= total)) {
                    homeProgressBar.visibility = View.VISIBLE
                    Handler().postDelayed({
                        fetchData(SharedPreferenceHouseLists.housesLists)
                    }, 2000)
                    isScrolling = false
                }
            }
        })

//        for(index in 1..1000) {
//            Handler().postDelayed({
//                val property = Property(
//                    "$index",
//                    Properties.INDIVIDUALROOM.toString(),
//                    false,
//                    BaseUtil().getDistanceInKilometer(
//                        LatitudeLongitude.latitude, LatitudeLongitude.longitude
//                    ),
//                    "",
//                    Date().toString(),
//                    "null",
//                    0,
//                    "",
//                    Address("india", "Rajasthan", "Jaipur", "Hazyavala")
//                )
//                res.add(property)
//                adapter = HomeClassAdapter(res)
//                recyclerView.adapter = adapter
//            }, 2000)
//        }
        return root
    }

    override fun onStart() {
        super.onStart()
        var list = SharedPreferenceHouseLists.housesLists

        if (list.size != 0) {
            Handler().postDelayed({
                fetchData(list)
            }, 2000)

        } else {
            val radius = 30.0
            val reference = FirebaseDatabase.getInstance().getReference().child("Users")
            val geoFire = GeoFire(reference)
            val geoQuery = geoFire.queryAtLocation(GeoLocation(26.8361984, 75.7729436), radius)
            geoQuery.addGeoQueryEventListener(this)
        }
    }

    private fun fetchData(list: ArrayList<String>) {
        var i = 0
        for (i in index..index + 5) {
            if (i >= list.size)
                break
            var key = list.get(i)
            var firebaseFirestore = FirebaseFirestore.getInstance()
            val path =
                firebaseFirestore.collection("Properties/${key}/${Properties.INDIVIDUALROOM}")
                    .document("BasicInfo")
            path.get().addOnSuccessListener {
                if (it.data != null) {
                    val property: Property = it.toObject(Property::class.java) as Property
                    res.add(property)
                    adapter.notifyDataSetChanged()
                    homeProgressBar.visibility = View.GONE
                    centerHomeProgressBar.visibility = View.GONE
                }
            }.addOnFailureListener {
                Log.d("failed", "get failed with", it)
            }
        }
        index = i
    }

    var flag = true
    override fun onKeyEntered(key: String?, location: GeoLocation?) {
//        centerHomeProgressBar.visibility = View.GONE
        sun.add(key.toString())
        if (sun.size == 10) {
            val temp = sun;
            sun.clear()
            addInRecyclerView(temp)
            sun.add(key.toString())
            flag = false
        }
    }

    private fun addInRecyclerView(temp: ArrayList<String>) {
        val runnable = Runnable {
            synchronized(this) {
                Thread.sleep(500)
                for (index in 0..temp.size - 1) {
                    var firebaseFirestore = FirebaseFirestore.getInstance()
                    val path =
                        firebaseFirestore.collection("Properties/${temp.get(index)}/${Properties.INDIVIDUALROOM}")
                            .document("BasicInfo")
                    path.get().addOnSuccessListener {
                        if (it.data != null) {
                            val property: Property = it.toObject(Property::class.java) as Property
                            res.add(property)
                            centerHomeProgressBar.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        }
                    }.addOnFailureListener {
                        Log.d("failed", "get failed with", it)
                    }
                }
            }
        }
        var thread = Thread(runnable)
        thread.start()
    }

    override fun onKeyExited(key: String?) {
    }

    override fun onKeyMoved(key: String?, location: GeoLocation?) {
    }

    override fun onGeoQueryReady() {
    }

    override fun onGeoQueryError(error: DatabaseError?) {

    }
}

interface MyCallback {
    fun onCallback(list: ArrayList<String>)
}