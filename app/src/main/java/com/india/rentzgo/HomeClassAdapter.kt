package com.india.rentzgo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeClassAdapter(private val mHouse : List<com.india.rentzgo.data.Property>) : RecyclerView.Adapter<HomeClassAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView : View) : RecyclerView.ViewHolder(listItemView)  {
        val distance = listItemView.findViewById<TextView>(R.id.distance)
        val rate = listItemView.findViewById<TextView>(R.id.rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeClassAdapter.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val demo = mHouse.get(position)
        val textView = holder.distance
        textView.setText("${demo.getDistanceInKilometer()}")
        val button = holder.rate
        button.text = demo.getPrice()
    }

    override fun getItemCount(): Int {
        return mHouse.size
    }
}