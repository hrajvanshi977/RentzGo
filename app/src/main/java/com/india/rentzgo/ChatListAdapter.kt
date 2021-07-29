package com.india.rentzgo

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentzgo.data.Users.Users
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ChatListAdapter(context: Context, var mUsers: ArrayList<String>) :
    RecyclerView.Adapter<ChatListAdapter.MyViewHolder>() {
    var contex = context

    open class MyViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        var profileIv = listItemView.findViewById<ImageView>(R.id.profilePhoto)
        var userName = listItemView.findViewById<TextView>(R.id.userName)
        var lastMessage = listItemView.findViewById<TextView>(R.id.lastMessage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.user_chat_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (mUsers.size != 0) {
            try {
                val firestoreDatabase = FirebaseFirestore.getInstance()
                val path = firestoreDatabase.collection("Users").document(mUsers[position])
                var user: Users? = null
                path.get().addOnSuccessListener {
                    if (it.data != null) {
                        user = it.toObject(Users::class.java) as Users
                        Glide.with(contex).load(user?.getPhotoUrlString()).into(holder.profileIv)
                        holder.userName.text = user!!.getFirstName() + " " + user!!.getLastName()
                        holder.lastMessage.text = "Hi there!"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(
                    TAG,
                    "onBindViewHolder: " + "error while fetching userchats, user is found null"
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }
}