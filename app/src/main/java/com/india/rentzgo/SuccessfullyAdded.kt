package com.india.rentzgo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import single.LatitudeLongitude

class SuccessfullyAdded : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successfully_added)
        Log.i(
            "Locations are",
            "Latitude->${LatitudeLongitude.latitude}, Longitude->${LatitudeLongitude.longitude}"
        )

        val text = findViewById<TextView>(R.id.goToHomePage)
        text.setOnClickListener {
            val intent = Intent(this, HousesLists::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        val viewYourProperties = findViewById<Button>(R.id.viewYourProperties)
        viewYourProperties.setOnClickListener {
            val intent = Intent(this, HousesLists::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("MyAds", "1")
            startActivity(intent)
            finish()
        }
        val postAnotherProperty = findViewById<Button>(R.id.postAnotherProperty)
        postAnotherProperty.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("afterSuccess", "1")
            startActivity(intent)
            finish()
        }
    }
}