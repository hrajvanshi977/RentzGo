package com.india.rentzgo

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.india.rentzgo.data.properties.IndividualRoom


class HomeClassAdapter(var context: Context, var mHouse: ArrayList<IndividualRoom?>) :
    RecyclerView.Adapter<HomeClassAdapter.MyViewHolder>() {
    private val VIEW_TYPE_ITEM: Int = 1
    private val VIEW_TYPE_LOADING: Int = 0

    open class MyViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var listItemView = listItemView
        var propertyImage: ImageView = listItemView.findViewById(R.id.propertyImage)
        var distance: TextView = listItemView.findViewById(R.id.distance)
        var rate: TextView = listItemView.findViewById(R.id.rate)
        var propertyTitle: TextView = listItemView.findViewById(R.id.propertyTitle)
        var address: TextView = listItemView.findViewById(R.id.address)

        //      val distance: TextView = listItemView.findViewById(R.id.distance)
//      val rate: TextView = listItemView.findViewById(R.id.rate)
        fun bind(property: IndividualRoom) {
            listItemView.setOnClickListener {
                Toast.makeText(listItemView.context, "went to another activity", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(listItemView.context, ShowPropertyInformation::class.java)
                val gson = Gson()
                val myJson = gson.toJson(property)
                intent.putExtra("PROPERTYID", myJson)
                ContextCompat.startActivity(listItemView.context, intent, null)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mHouse[position] != null)
            VIEW_TYPE_ITEM
        else
            VIEW_TYPE_LOADING
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            var inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.layout_home, parent, false)
            MyViewHolder(view)
        } else {
            var inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.row_progress, parent, false)
            ProgressViewHolder(view)
        }
    }

    override fun getItemId(position: Int): Long {
        return position as Long
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val property = mHouse[position]
        if (property != null) {
            var progressbar =
                holder.listItemView.findViewById<LottieAnimationView>(R.id.progressbar)
            Log.d(ContentValues.TAG, "onBindViewHolder: ${property.getCoverPhoto()}")
            Glide.with(context).load(Uri.parse(property.getCoverPhoto()))
                .into(holder.propertyImage)
            progressbar.visibility = View.GONE
            holder.distance.text = property.getDistance()
            holder.rate.text = "₹ ${property.getPrice()}"
            holder.propertyTitle.text = property.getPropertyTitle()
            holder.address.text = property.getAddress()
            holder.bind(property)
            return
        }
    }

    internal class ProgressViewHolder(itemView: View) : MyViewHolder(itemView)

    override fun getItemCount(): Int {
        return mHouse.size
    }

    fun addNullData() {
        mHouse.add(null)
    }

    fun removeNullData() {
        mHouse.removeAt(mHouse.size - 1)
    }
}