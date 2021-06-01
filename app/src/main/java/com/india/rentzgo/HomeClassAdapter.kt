package com.india.rentzgo

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.india.rentzgo.data.properties.IndividualRoom
import com.squareup.picasso.Picasso


class HomeClassAdapter(private var mHouse: ArrayList<IndividualRoom?>) :
    RecyclerView.Adapter<HomeClassAdapter.MyViewHolder>() {
    private val VIEW_TYPE_ITEM: Int = 1
    private val VIEW_TYPE_LOADING: Int = 0

    open class MyViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val listItemView = listItemView

        //        val distance: TextView = listItemView.findViewById(R.id.distance)
//        val rate: TextView = listItemView.findViewById(R.id.rate)
        fun bind(item: IndividualRoom) {
            listItemView.setOnClickListener {
                Toast.makeText(listItemView.context, "went to another activity", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(listItemView.context, ShowPropertyInformation::class.java)
                val gson = Gson()
                val myJson = gson.toJson(item)
                intent.putExtra("PROPERTYID", myJson)
                ContextCompat.startActivity(listItemView.context, intent, null)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mHouse[position] != null)
            return VIEW_TYPE_ITEM
        else
            return VIEW_TYPE_LOADING

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            var inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.layout_home, parent, false)
            return MyViewHolder(view)
        } else {
            var inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.row_progress, parent, false)
            return ProgressViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val demo = mHouse[position]
        if (demo != null) {
            val distance = holder.listItemView.findViewById<TextView>(R.id.distance)
            distance.text = "5km"
            val rate = holder.listItemView.findViewById<TextView>(R.id.rate)
            rate.text = demo!!.getPrice()
            loadImage(holder, demo.getPropertyId())
            holder.bind(demo)
        } else {
            return
        }
    }

    private fun loadImage(holder: MyViewHolder, propertyId: String?) {
        val propertyImage = holder.listItemView.findViewById<ImageView>(R.id.propertyImage)
        val progressbar = holder.listItemView.findViewById<LottieAnimationView>(R.id.progressbar)
        val filePath =
            FirebaseStorage.getInstance().reference.child("Images").child(propertyId!!).child("0")
        filePath.downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(propertyImage)
            Handler(Looper.myLooper()!!).postDelayed({
                progressbar.visibility = View.GONE
            }, 2000)
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