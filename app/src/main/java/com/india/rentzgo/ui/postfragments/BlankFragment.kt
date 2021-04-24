package com.india.rentzgo.ui.postfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.view.get
import androidx.fragment.app.ListFragment
import com.india.rentzgo.R

class BlankFragment : ListFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
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
            goForIndividualRooms(PostFragmentStep2(), "flag")
        }
    }

    private fun goForIndividualRooms(propertType : Fragment, tag : String) {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        fragmentTransaction?.replace(R.id.fragment_container, propertType)
        fragmentTransaction?.addToBackStack(tag)
        fragmentTransaction?.commit()
    }
}