package com.india.rentzgo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.india.rentzgo.Users.Demo

class ImageClassAdapter(private val mHouse: List<String>) :
    RecyclerView.Adapter<ImageClassAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val image = listItemView.findViewById(R.id.image_card) as ImageView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageClassAdapter.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.image_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val demo = mHouse.get(position)
        val imageView = holder.image
        imageView.setImageResource(R.drawable.house_image)
    }

    override fun getItemCount(): Int {
        return mHouse.size
    }
}