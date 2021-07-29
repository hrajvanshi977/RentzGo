package com.india.rentzgo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ChatAdapter(context: Context, var mChat: ArrayList<Chat>) :
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {
    private val LEFT_SIDE_MESSAGE = 0
    private val RIGHT_SIDE_MESSAGE = 1

    open class MyViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var senderMessageTv: TextView = listItemView.findViewById(R.id.senderMessage)
        var sendTimeTv: TextView = listItemView.findViewById(R.id.sendTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if(viewType == RIGHT_SIDE_MESSAGE) {
            var inflater = LayoutInflater.from(parent.context)
            var view = inflater.inflate(R.layout.sender_chat_layout, parent, false)
            return MyViewHolder(view)
        } else {
            var inflater = LayoutInflater.from(parent.context)
            var view = inflater.inflate(R.layout.receiver_chat_layout, parent, false)
            return MyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var message = mChat[position].getMessage()
        holder.senderMessageTv.text = message
        val str: List<String> = Date().toString().split(" ").get(3).split(":")
        holder.sendTimeTv.text = str[0] + ":" + str[1]
    }

    override fun getItemViewType(position: Int): Int {
        var currentUser = FirebaseAuth.getInstance().currentUser.uid
        return if (mChat[position].getSender() == currentUser) {
            RIGHT_SIDE_MESSAGE
        } else {
            LEFT_SIDE_MESSAGE
        }
    }

    override fun getItemCount(): Int {
        return mChat.size
    }
}