package com.india.rentzgo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.india.rentzgo.data.Property


class HomeClassAdapter(private var mHouse: ArrayList<Property?>) :
    RecyclerView.Adapter<HomeClassAdapter.MyViewHolder>() {
    private val VIEW_TYPE_ITEM: Int = 1
    private val VIEW_TYPE_LOADING: Int = 0

    open class MyViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val listItemView = listItemView

//        val distance: TextView = listItemView.findViewById(R.id.distance)
//        val rate: TextView = listItemView.findViewById(R.id.rate)

        fun bind(item: Property) {
            listItemView.setOnClickListener {
                Toast.makeText(listItemView.context, "went to another activity", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(listItemView.context, ShowPropertyInformation::class.java)
                ContextCompat.startActivity(listItemView.context, intent, null)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mHouse.get(position) != null)
            return VIEW_TYPE_ITEM
        else
            return VIEW_TYPE_LOADING

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeClassAdapter.MyViewHolder {
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
        val demo = mHouse.get(position)
        if (demo != null) {
            val distance = holder.listItemView.findViewById<TextView>(R.id.distance)
            distance.setText("${demo!!.getDistanceInKilometer()}")
            val rate = holder.listItemView.findViewById<TextView>(R.id.rate)
            rate.text = demo!!.getPrice()
            holder.bind(demo)
        } else {
            return
        }
    }

    internal class ProgressViewHolder(itemView: View) : MyViewHolder(itemView)

    override fun getItemCount(): Int {
        return mHouse.size
    }

    fun addNulData() {
        mHouse.add(null)
        notifyItemInserted(mHouse.size - 1)
    }

    fun removeNull() {
        mHouse.removeAt(mHouse.size - 1)
        notifyItemRemoved(mHouse.size)
    }


}