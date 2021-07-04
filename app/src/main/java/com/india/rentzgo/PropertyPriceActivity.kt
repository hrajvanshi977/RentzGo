package com.india.rentzgo

import android.app.AlertDialog
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.india.rentzgo.data.base.Properties
import com.india.rentzgo.data.properties.IndividualRoom
import com.india.rentzgo.utils.DBUtils
import single.LatitudeLongitude
import single.NearbyProperties
import java.util.*

class PropertyPriceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_price)
        val submit = findViewById<Button>(R.id.materialButton)

        var testing = findViewById<EditText>(R.id.propertyTitle)
        println(testing)
        submit.setOnClickListener {
            setBuilder()
//            getCurrentLocationAddress()
            CurrentPostingPropertyDetails.uniqueId = getUniqueKey()

            var individualRoom = getNewlyCreatedProperty(
                CurrentPostingPropertyDetails.uniqueId,
                CurrentPostingPropertyDetails.PropertyType
            )
//            DBUtils().saveProperty(
//                individualRoom,
//                CurrentPostingPropertyDetails.uniqueId
//            )

//            DBUtils().savePropertyImages(
//                CurrentPostingPropertyDetails.images,
//                CurrentPostingPropertyDetails.uniqueId
//            )

            Handler(Looper.myLooper()!!).postDelayed({
                val intent = Intent(this, SuccessfullyAdded::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }

    private fun getUniqueKey(): String {
        return FirebaseDatabase.getInstance().reference.push().key.toString()
    }

    private fun getCurrentLocationAddress(): String {
        Log.i("NearbyProperties", "${NearbyProperties.list.size}")
        val geoCode = Geocoder(this, Locale.getDefault())

        Log.i(
            "Latitude and Longitude",
            "${LatitudeLongitude.latitude}, ${LatitudeLongitude.longitude}"
        )
        val address: List<android.location.Address?> =
            geoCode.getFromLocation(26.9146122, 75.8137346, 1)

//        Log.i("My address", addresses.get(0)!!.getAddressLine(0))
        Log.i("Full address", address.toString())
        return address[0]!!.getAddressLine(0)
    }

    private fun getNewlyCreatedProperty(propertyId: String, propertyType: String): IndividualRoom {
        return getIndividualRoomObj(propertyId)
    }

    private fun getIndividualRoomObj(propertyId: String): IndividualRoom {
        var ownerId = FirebaseAuth.getInstance().currentUser.uid
        val price = findViewById<EditText>(R.id.priceView)

        var individualRoom = IndividualRoom(
            "",
            propertyId,
            ownerId,
            CurrentPostingPropertyDetails.propertyTitle,
            Properties.INDIVIDUALROOM.toString(),
            false,
            price.text.toString(),
            "",
            Date().toString(),
            2,
            getCurrentLocationAddress(),
            CurrentPostingPropertyDetails.maxPeople,
            CurrentPostingPropertyDetails.furnishing,
            CurrentPostingPropertyDetails.facing,
            CurrentPostingPropertyDetails.totalFloors,
            CurrentPostingPropertyDetails.currentFloor,
            CurrentPostingPropertyDetails.parkingFacility,
            CurrentPostingPropertyDetails.bachelorsAllowed,
            CurrentPostingPropertyDetails.nonVegAllowed,
            CurrentPostingPropertyDetails.drinkAndSmokingAllowed,
            CurrentPostingPropertyDetails.propertyDescription
        )

        DBUtils().savePropertyImages(
            CurrentPostingPropertyDetails.images,
            individualRoom
        )
        return individualRoom
    }

    private fun setBuilder() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_layout)
        }
        val dialog = builder.create()
        dialog.show()
    }

}