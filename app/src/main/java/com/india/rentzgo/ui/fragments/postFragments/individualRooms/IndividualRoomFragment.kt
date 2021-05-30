package com.india.rentzgo.ui.fragments.postFragments.individualRooms

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.india.rentzgo.CurrentPostingProperty
import com.india.rentzgo.PropertyPriceActivity
import com.india.rentzgo.R
import com.india.rentzgo.utils.dialog.IndividualsRoomDialog

class IndividualRoomFragment : Fragment() {
    private lateinit var propertyType: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_individual_room, container, false)
        initializeField(view)
        val furnished = view.findViewById(R.id.furnishing) as EditText
        furnished.setOnClickListener {
            openDialog(R.array.furnishing, furnished, "Furnishing")
        }

        val facing = view.findViewById(R.id.facing) as EditText
        facing.setOnClickListener {
            openDialog(R.array.facing, facing, "Facing")
        }

        val parkingFacility = view.findViewById(R.id.parkingFacility) as EditText
        parkingFacility.setOnClickListener {
            openDialog(R.array.parking_facility, parkingFacility, "Parking Facility")
        }

        val bachelorsAllowed = view.findViewById(R.id.bachelorsAllowed) as EditText
        bachelorsAllowed.setOnClickListener {
            openDialog(R.array.yes_no, bachelorsAllowed, "Bachelors allowed?")
        }

        val nonVegAllowed = view.findViewById(R.id.nonVegAllowed) as EditText
        nonVegAllowed.setOnClickListener {
            openDialog(R.array.yes_no, nonVegAllowed, "Non-Veg allowed?");
        }

        val drinkSmokeAllowed = view.findViewById(R.id.drinkAndSmokingAllowed) as EditText
        drinkSmokeAllowed.setOnClickListener {
            openDialog(R.array.yes_no, drinkSmokeAllowed, "Drink & smoke allowed?")
        }

        val nextButton = view.findViewById(R.id.nextIndividual) as TextView
        nextButton.setOnClickListener {
            CurrentPostingProperty.propertyTitle = view.findViewById<EditText>(R.id.propertyTitle).text.toString()
            CurrentPostingProperty.maxPeople = view.findViewById<EditText>(R.id.maxPeople).text.toString()
            CurrentPostingProperty.furnishing = view.findViewById<EditText>(R.id.furnishing).text.toString()
            CurrentPostingProperty.facing = view.findViewById<EditText>(R.id.facing).text.toString()
            CurrentPostingProperty.totalFloors = view.findViewById<EditText>(R.id.totalFloors).text.toString()
            CurrentPostingProperty.currentFloor = view.findViewById<EditText>(R.id.currentFloor).text.toString()
            CurrentPostingProperty.parkingFacility = view.findViewById<EditText>(R.id.parkingFacility).text.toString()
            CurrentPostingProperty.propertyDescription = view.findViewById<EditText>(R.id.propertyDescription).text.toString()
            CurrentPostingProperty.bachelorsAllowed = view.findViewById<EditText>(R.id.bachelorsAllowed).text.toString().equals("Yes")
            CurrentPostingProperty.drinkAndSmokingAllowed = view.findViewById<EditText>(R.id.drinkAndSmokingAllowed).text.toString().equals("Yes")
            CurrentPostingProperty.nonVegAllowed = view.findViewById<EditText>(R.id.nonVegAllowed).text.toString().equals("Yes")
            val intent = Intent(view.context, PropertyPriceActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun initializeField(view: View) {
        propertyType = view.findViewById(R.id.propertyTitle)
    }

    private fun openDialog(id: Int, textView: EditText, propertyFieldsText: String) {
        val dialog = IndividualsRoomDialog(id, textView, propertyFieldsText)
        dialog.show(parentFragmentManager, "Example Dialog")
    }

    fun getPropertyTitle(): String {
        return propertyType.text.toString()
    }
}