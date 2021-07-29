package com.india.rentzgo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentzgo.data.Users.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatList : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    lateinit var chatListAdapter : ChatListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        recyclerView = findViewById(R.id.allChatsRecyclerView)
        val firestoreDatabase = FirebaseFirestore.getInstance()
        val path = firestoreDatabase.collection("Users").document(FirebaseAuth.getInstance().currentUser.uid)
        path.get().addOnSuccessListener {
            if (it.data != null) {
                var user = it.toObject(Users::class.java) as Users
                chatListAdapter = ChatListAdapter(this, user!!.getUserChats())
                chatListAdapter.notifyDataSetChanged()
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = chatListAdapter
            }
        }
    }
}