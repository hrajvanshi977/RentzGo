package com.india.rentzgo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.av.smoothviewpager.Smoolider.SmoothViewpager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.gson.Gson
import com.india.rentzgo.data.properties.IndividualRoom
import com.like.LikeButton


class ShowPropertyInformation : AppCompatActivity() {
    private lateinit var viewPagerProgressBar: ProgressBar
    private lateinit var likeButton: LikeButton
    private lateinit var goBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.myLooper()!!).postDelayed({
            val gson = Gson()
            val property: IndividualRoom =
                gson.fromJson(intent.getStringExtra("PROPERTYID"), IndividualRoom::class.java)
            initializeAllFields(property)
            var imageView = findViewById<ImageView>(R.id.image)
            imageView.layoutParams.height = 0
            imageView.layoutParams.width = 0
            var middleProgressBar = findViewById<LottieAnimationView>(R.id.middleProgressBar)
            middleProgressBar.visibility = View.GONE
            loadImagesInAdapter(property.getPropertyId())
        }, 1000)
        setContentView(R.layout.activity_show_property_information)
        initializeAllFields()
        onClickProgress()
        var viewPager = findViewById<SmoothViewpager>(R.id.homeImagesViewPager)
        viewPager.adapter = PropertyImagesAdapter
//        val tableLayout = findViewById<WormDotsIndicator>(R.id.tablayout)
//        tableLayout.setViewPager(viewPager)
    }

    private fun onClickProgress() {
        likeButton.setOnClickListener {
            if (!likeButton.isLiked) {
                likeButton.isLiked = true
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Property Added in favourites",
                    Snackbar.LENGTH_LONG
                ).show()

            } else {
                likeButton.isLiked = false
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Property Removed from favourites",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        goBack.setOnClickListener {
            finish()
        }
    }

    private fun loadImagesInAdapter(propertyId: String?) {
        val reference =
            FirebaseStorage.getInstance().reference.child("Images").child(propertyId!!)
        reference.listAll().addOnSuccessListener {
            val list: ListResult? = it
            addImagesInViewpager(list, propertyId!!)
        }.addOnFailureListener {
        }
    }

    override fun finish() {
        super.finish()
        PropertyImagesAdapter.thisList.clear()
        PropertyImagesAdapter.notifyDataSetChanged()
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
                Glide.with(applicationContext).load(it).into(propertyImage)
                viewPagerProgressBar.visibility = View.GONE
            }
            PropertyImagesAdapter.thisList.add(view)
            PropertyImagesAdapter.notifyDataSetChanged()
        }
    }

    private fun initializeAllFields() {
        likeButton = findViewById(R.id.like)
        viewPagerProgressBar = findViewById(R.id.viewPagerProgressBar)
        goBack = findViewById(R.id.goBack)
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