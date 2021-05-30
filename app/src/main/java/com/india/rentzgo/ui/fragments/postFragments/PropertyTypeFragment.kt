package com.india.rentzgo.ui.fragments.postFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.india.rentzgo.CurrentPostingProperty
import com.india.rentzgo.MainActivityTest
import com.india.rentzgo.R
import com.india.rentzgo.data.base.Properties

class PropertyTypeFragment : ListFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_property_type, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.Houses,
            R.layout.activity_listitem
        )
        listAdapter = adapter
    }


    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        if (position == 0) {
            CurrentPostingProperty.PropertyType = Properties.INDIVIDUALROOM.toString()
//            goForIndividualRooms(IndividualRoomFragment(), "flag")
//            PostActivity().goToUploadImageAcivity()
            val intent = Intent(activity, MainActivityTest::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun goForImageUpload(imageFragment: Fragment, tag: String) {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        fragmentTransaction?.replace(R.id.post_fragment_container, imageFragment)
        fragmentTransaction?.addToBackStack(tag)
        fragmentTransaction?.commit()
    }

    fun goForIndividualRooms(propertType: Fragment, tag: String) {
        CurrentPostingProperty.PropertyType = Properties.INDIVIDUALROOM.toString()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        fragmentTransaction?.replace(R.id.post_fragment_container, propertType)
        fragmentTransaction?.addToBackStack(tag)
        fragmentTransaction?.commit()
    }
}