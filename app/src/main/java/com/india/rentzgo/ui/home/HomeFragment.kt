package com.india.rentzgo.ui.home

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQueryEventListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.database.DatabaseError
import com.google.firebase.firestore.FirebaseFirestore
import com.india.rentzgo.HomeClassAdapter
import com.india.rentzgo.InfiniteScrollListener
import com.india.rentzgo.R
import com.india.rentzgo.data.Property
import com.india.rentzgo.data.base.Properties
import com.india.rentzgo.data.properties.IndividualRoom
import com.india.rentzgo.utils.BaseUtil
import single.NearbyProperties


class HomeFragment : Fragment(), GeoQueryEventListener, InfiniteScrollListener.OnLoadMoreListener {
    private lateinit var homeViewModel: HomeViewModel
    lateinit var fragment: Fragment
    var container: ViewGroup? = null
    var sun = ArrayList<String>()
    var res = ArrayList<IndividualRoom?>()
    lateinit var inflater: LayoutInflater
    lateinit var adapter: HomeClassAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var manager: LinearLayoutManager
    var current = 0
    var scrolled = 0
    var total = 0
    lateinit var homeProgressBar: LottieAnimationView
    lateinit var centerHomeProgressBar: ProgressBar
    var index: Int = 0;

    lateinit var locationSearch: EditText
    var isScrolling = false

    private val AUTOCOMPLETE_REQUEST_CODE = 1

    private lateinit var infiniteScrollListener: InfiniteScrollListener
    var root: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (root != null) {
            (root!!.parent as? ViewGroup)?.removeView(root)
        } else {
            root = inflater.inflate(R.layout.fragment_home, container, false)
            Handler(Looper.myLooper()!!).post {
                showHomes()
                adapter = HomeClassAdapter(res)
                manager = LinearLayoutManager(activity)
                infiniteScrollListener = InfiniteScrollListener(manager, this)
                homeProgressBar = root!!.findViewById(R.id.homeProgressBar)
                centerHomeProgressBar = root!!.findViewById(R.id.centerHomeProgressBar)
                homeViewModel =
                    ViewModelProvider(this).get(HomeViewModel::class.java)
                recyclerView = root!!.findViewById(R.id.home_recycler_view) as RecyclerView
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = manager
                recyclerView.adapter = adapter
                recyclerView.addOnScrollListener(infiniteScrollListener)
                infiniteScrollListener.setLoaded()


                if (!Places.isInitialized()) {
                    Places.initialize(requireContext(), "AIzaSyCHQzzmlMHXd9Rb9qmLKlUGXZnpIXkKQgE")
                }
                Places.createClient(requireContext())
                locationSearch = root!!.findViewById(R.id.locationSearch)

                locationSearch.setOnClickListener {

                    val fields = listOf(Place.Field.ID, Place.Field.NAME)

                    // Start the autocomplete intent.
                    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(requireContext())
                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
                }
            }
            /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

                    if(index >= NearbyProperties.list.size) {
                        return
                    }
                    current = manager.childCount
                    total = adapter.itemCount
                    scrolled = manager.findFirstVisibleItemPosition()

                    if (isScrolling && (current + scrolled >= total)) {
                        homeProgressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
    //                        fetchData(SharedPreferenceHouseLists.housesLists)
                            fetchDataTest()
                        }, 2000)
                        isScrolling = false
                    }
                }
            })*/

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
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.i(TAG, "Place: ${place.name}, ${place.id}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, status.statusMessage)
                    }
                }
                Activity.RESULT_CANCELED -> {
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onStart() {
        super.onStart()
        /* var list = SharedPreferenceHouseLists.housesLists

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
         } */

    }

    private fun showHomes() {
        BaseUtil().sortByDistance()
        fetchDataTest()
    }

    private fun fetchDataTest() {
//        val executor = Executors.newSingleThreadExecutor()
//        val handler = Handler(Looper.getMainLooper())
//        executor.execute {

        var runnable = Runnable {
            var temp = index
            if (index < NearbyProperties.list.size) {
                for (i in temp..temp + 1) {
                    index = i
                    if (index >= NearbyProperties.list.size) {
                        break
                    }
                    Log.i("In the dangerous", "${NearbyProperties.list.get(i).id}")
                    var key = NearbyProperties.list[i].id
                    val distance = NearbyProperties.list[i].distance.toString()
                    var firebaseFirestore = FirebaseFirestore.getInstance()

                    val path =
                        firebaseFirestore.collection("Properties/${key}/${Properties.INDIVIDUALROOM}")
                            .document("BasicInfo")
                    path.get().addOnSuccessListener {
                        if (it.data != null) {
                            var individualRoom: IndividualRoom =
                                it.toObject(IndividualRoom::class.java) as IndividualRoom
                            individualRoom.setDistance(distance)
                            var dis = individualRoom.getDistance()
                            println("The distance is $dis")
                            res.add(individualRoom)
                            adapter.notifyDataSetChanged()
                            centerHomeProgressBar.visibility = View.GONE
                        }
                    }.addOnFailureListener {
                        Log.d("failed", "get failed with", it)
                    }
                }
            }
        }
        Thread(runnable).start()
//        }
    }

    /* private fun fetchData(list: ArrayList<String>) {
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
     }*/

    var flag = true
    override fun onKeyEntered(key: String?, location: GeoLocation?) {
        centerHomeProgressBar.visibility = View.GONE
        sun.add(key.toString())
        if (sun.size == 10) {
            val temp = sun;
            sun.clear()
//            addInRecyclerView(temp)
            sun.add(key.toString())
            flag = false
        }
    }

    /*  private fun addInRecyclerView(temp: ArrayList<String>) {
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
      } */

    override fun onKeyExited(key: String?) {
    }

    override fun onKeyMoved(key: String?, location: GeoLocation?) {
    }

    override fun onGeoQueryReady() {
    }

    override fun onGeoQueryError(error: DatabaseError?) {

    }

    override fun onLoadMore() {
        adapter.addNulData()
        Handler().postDelayed({
            var firebaseFirestore = FirebaseFirestore.getInstance()
            val path =
                firebaseFirestore.collection("Properties/${1}/${Properties.INDIVIDUALROOM}")
                    .document("BasicInfo")
            path.get().addOnSuccessListener {
                if (it.data != null) {
                    val property: IndividualRoom =
                        it.toObject(IndividualRoom::class.java) as IndividualRoom
                    adapter.removeNull()
                    res.add(property)
                    res.add(property)
                    res.add(property)
                    res.add(property)
                    res.add(property)
                    adapter.notifyDataSetChanged()
                    infiniteScrollListener.setLoaded();
//                    homeProgressBar.visibility = View.GONE
                    centerHomeProgressBar.visibility = View.GONE
                }
            }.addOnFailureListener {
                Log.d("failed", "get failed with", it)
            }
        }, 1500)
    }
}


interface MyCallback {
    fun onCallback(list: ArrayList<String>)
}

interface OnItemClickListener {
    fun onItemClick(item: Property)
}