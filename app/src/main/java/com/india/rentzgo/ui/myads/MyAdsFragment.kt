package com.india.rentzgo.ui.myads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.india.rentzgo.R

class MyAdsFragment : Fragment() {

    private lateinit var myAdsViewModel: MyAdsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myAdsViewModel =
            ViewModelProvider(this).get(MyAdsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_myads, container, false)
        val textView: TextView = root.findViewById(R.id.myAdsTextView)
        myAdsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}