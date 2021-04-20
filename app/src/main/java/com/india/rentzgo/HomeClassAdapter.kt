package com.india.rentzgo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.india.rentzgo.Users.Demo

class HomeClassAdapter(private val mHouse : List<Demo>) : RecyclerView.Adapter<HomeClassAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView : View) : RecyclerView.ViewHolder(listItemView)  {
        val distance = listItemView.findViewById<TextView>(R.id.distance)
        val rate = listItemView.findViewById<TextView>(R.id.rate)
//        val recyclerView = listItemView.findViewById(R.id.home_images) as RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeClassAdapter.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val demo = mHouse.get(position)
        val textView = holder.distance
        textView.setText(demo.name)

//        val recyclerView = holder.recyclerView
//
//        recyclerView.setHasFixedSize(true)
//
//        val list = listOf<String>("",  "" , "")
//
//        recyclerView.adapter = ImageClassAdapter(list)

//        recyclerView.layoutManager = LinearLayoutManager(, LinearLayoutManager.HORIZONTAL, false)


        val button = holder.rate
        button.text = demo.city
    }

    override fun getItemCount(): Int {
        return mHouse.size
    }
}