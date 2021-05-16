package com.india.rentzgo

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.india.rentzgo.data.Property
import com.india.rentzgo.data.base.Address
import com.india.rentzgo.data.base.Properties
import com.india.rentzgo.utils.BaseUtil
import com.india.rentzgo.utils.DBUtils
import single.LatitudeLongitude
import java.util.*

class PropertyPriceActivity : AppCompatActivity() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_price)
        val submit = findViewById<Button>(R.id.materialButton)
        submit.setOnClickListener {
            setBuilder()
            for (index in 1..1000) {
                Handler().postDelayed({
                    val property = getPropertyInfo(index)
                    DBUtils().saveProperty(property, index)
                }, 1000)
            }
        }
    }

    private fun getPropertyInfo(i: Int): Property {
        firebaseFirestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser.uid
        val priceView = findViewById<EditText>(R.id.priceView)
        val properties = Property(
            "$i",
            Properties.INDIVIDUALROOM.toString(),
            false,
            BaseUtil().getDistanceInKilometer(
                LatitudeLongitude.latitude, LatitudeLongitude.longitude
            ),
            priceView.text.toString(),
            Date().toString(),
            "null",
            0,
            "",
            Address("india", "Rajasthan", "Jaipur", "Hazyavala")
        )
        return properties
    }

    fun setBuilder() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_layout)
        }
        val dialog = builder.create()
        dialog.show()
    }
}