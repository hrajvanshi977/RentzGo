package com.india.rentzgo.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.india.rentzgo.HomeClassAdapter
import com.india.rentzgo.HousesLists
import com.india.rentzgo.R
import com.india.rentzgo.Users.Demo

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    lateinit var  fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("came to home", "hi there")
        Log.i("savedInstanceState", savedInstanceState.toString())

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val recyclerView = root?.findViewById(R.id.home_recycler_view) as RecyclerView

        recyclerView.setHasFixedSize(true)

        var list = ArrayList<Demo>()


        for (i in 1..100) {
            var distance = "Km"
            list.add(Demo("$i " + distance, "${i * 1000}"))
        }

        var adapter = HomeClassAdapter(list)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(activity)

        return root
    }
}