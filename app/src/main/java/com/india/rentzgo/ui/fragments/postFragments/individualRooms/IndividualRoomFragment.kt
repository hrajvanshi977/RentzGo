package com.india.rentzgo.ui.fragments.postFragments.individualRooms

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.india.rentzgo.PropertyPriceActivity
import com.india.rentzgo.utils.dialog.IndividualsRoomDialog
import com.india.rentzgo.R

class IndividualRoomFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_individual_room, container, false)

        val furnished = view.findViewById(R.id.furnishing) as EditText
        furnished.setOnClickListener {
            openDialog(R.array.furnishing, furnished, "Furnishing")
        }

        val facing = view.findViewById(R.id.facing) as EditText
        facing.setOnClickListener {
            openDialog(R.array.facing, facing, "Facing")
        }

        val parkingFacility = view.findViewById(R.id.parking_facility) as EditText
        parkingFacility.setOnClickListener {
            openDialog(R.array.parking_facility, parkingFacility, "Parking Facility")
        }

        val bachelorsAllowed = view.findViewById(R.id.bachelors_allowed) as EditText
        bachelorsAllowed.setOnClickListener {
            openDialog(R.array.yes_no, bachelorsAllowed, "Bachelors allowed?")
        }

        val nonVegAllowed = view.findViewById(R.id.non_veg_allowed) as EditText
        nonVegAllowed.setOnClickListener {
            openDialog(R.array.yes_no, nonVegAllowed, "Non-Veg allowed?");
        }

        val drinkSmokeAllowed = view.findViewById(R.id.drink_smoke_allowed) as EditText
        drinkSmokeAllowed.setOnClickListener {
            openDialog(R.array.yes_no, drinkSmokeAllowed, "Drink & smoke allowed?")
        }

        val nextButton = view.findViewById(R.id.nextIndividual) as TextView
        nextButton.setOnClickListener {
            val intent = Intent(view.context, PropertyPriceActivity::class.java)
            startActivity(intent)
        }
        return view
    }


    private fun openDialog(id: Int, textView: EditText, propertyFieldsText: String) {
        val dialog = IndividualsRoomDialog(id, textView, propertyFieldsText)
        dialog.show(parentFragmentManager, "Example Dialog")
    }
}