package com.india.rentzgo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.av.smoothviewpager.Smoolider.SmoothViewpager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.gson.Gson
import com.india.rentzgo.data.properties.IndividualRoom
import com.squareup.picasso.Picasso


class ShowPropertyInformation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.myLooper()!!).postDelayed({
            val gson = Gson()
            val property: IndividualRoom =
                gson.fromJson(intent.getStringExtra("PROPERTYID"), IndividualRoom::class.java)
            initializeAllFields(property)
            var imageView = findViewById<ImageView>(R.id.image)
            var progressBar = findViewById<LottieAnimationView>(R.id.progressBar)
            imageView.layoutParams.height = 0
            imageView.layoutParams.width = 0
            progressBar.visibility = View.GONE
            loadImagesInAdapter(property.getPropertyId())
        }, 1000)
        setContentView(R.layout.activity_show_property_information)
        var viewPager = findViewById<SmoothViewpager>(R.id.homeImagesViewPager)
        viewPager.adapter = PropertyImagesAdapter
//        val tableLayout = findViewById<WormDotsIndicator>(R.id.tablayout)
//        tableLayout.setViewPager(viewPager)
    }

    private fun loadImagesInAdapter(propertyId: String?) {
        val reference =
            FirebaseStorage.getInstance().reference.child("Images").child("-MaqvSec9B2phfMhuTd4")
        reference.listAll().addOnSuccessListener {
            val list: ListResult? = it
            addImagesInViewpager(list, "-MaqvSec9B2phfMhuTd4")
        }.addOnFailureListener {
        }
    }

    private fun addImagesInViewpager(list: ListResult?, s: String) {
        val pager = findViewById<ViewPager>(R.id.homeImagesViewPager)
        PropertyImagesAdapter.thisList = ArrayList()
        pager.adapter = PropertyImagesAdapter
        for (index in 0 until list!!.items.size) {
            val view = layoutInflater.inflate(R.layout.images_layout, null)
            val propertyImage = view.findViewById<ImageView>(R.id.images)
            val filePath =
                FirebaseStorage.getInstance().reference.child("Images")
                    .child(s).child("$index")
            filePath.downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(propertyImage)
            }
            PropertyImagesAdapter.thisList.add(view)
            PropertyImagesAdapter.notifyDataSetChanged()
        }
    }

    private fun initializeAllFields(property: IndividualRoom) {
        var priceView = findViewById<TextView>(R.id.priceView)
        priceView.text = "₹ ${property.getPrice()}"
        var distance = findViewById<TextView>(R.id.distance)
        distance.text = property.getDistance()
        var propertyTitle = findViewById<TextView>(R.id.propertyTitle)
        propertyTitle.text = property.getPropertyTitle()
        var propertyLocationAddress = findViewById<TextView>(R.id.propertyLocationAddress)
        propertyLocationAddress.text = property.getAddress()
        var propertyTypes = findViewById<TextView>(R.id.propertyTypes)
        propertyTypes.text = property.getPropertyType()
        var maxPeople = findViewById<TextView>(R.id.maxPeople)
        maxPeople.text = property.getMaxPeople()
        var furnishing = findViewById<TextView>(R.id.furnishing)
        furnishing.text = property.getFurnishing()
        var facing = findViewById<TextView>(R.id.facing)
        facing.text = property.getFacing()
        var totalFloors = findViewById<TextView>(R.id.totalFloors)
        totalFloors.text = property.getTotalFloors()
        var currentFloor = findViewById<TextView>(R.id.currentFloor)
        currentFloor.text = property.getCurrentFloors()
        var parkingFacility = findViewById<TextView>(R.id.parkingFacility)
        parkingFacility.text = property.getParkingFacility()
        var bachelorsAllowed = findViewById<TextView>(R.id.bachelorsAllowed)
        bachelorsAllowed.text = if (property.isBachelorsAllowed() == true) "Yes" else "No"
        var nonVegAllowed = findViewById<TextView>(R.id.nonVegAllowed)
        nonVegAllowed.text = if (property.isNonVegAllowed() == true) "Yes" else "No"
        var drinkAndSmokingAllowed = findViewById<TextView>(R.id.drinkAndSmokingAllowed)
        drinkAndSmokingAllowed.text =
            if (property.isDrinkAndSmokingAllowed() == true) "Yes" else "No"
        var propertyDescription = findViewById<TextView>(R.id.propertyDescription)
        propertyDescription.text = property.getPropertyDescription()
    }
}