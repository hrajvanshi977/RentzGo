package com.india.rentzgo

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentzgo.data.Users.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.india.rentzgo.utils.DBUtils

class ChatActivity : AppCompatActivity() {
    lateinit var chatAdapter: ChatAdapter
    var mChat = ArrayList<Chat>()
    private var ownerId: String = ""
    lateinit var firebaseDatabase: FirebaseDatabase
    private val currentUserId = FirebaseAuth.getInstance().currentUser.uid
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        recyclerView = findViewById(R.id.chatRecyclerView)
        ownerId = intent.getStringExtra("OWNERID").toString()
        firebaseDatabase = FirebaseDatabase.getInstance()
        var messageEt = findViewById<EditText>(R.id.messageEt)
        var sendBtn = findViewById<ImageView>(R.id.sendBtn)
        chatAdapter = ChatAdapter(this, mChat)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter
        recyclerView.scrollToPosition(chatAdapter.itemCount)

        readAlreadyExistingMessages()

        sendBtn.setOnClickListener {
            val currentMessage = messageEt.text.toString()
            if (currentMessage.isNotEmpty() && currentMessage != "") {
                var user: Users? = DBUtils().getCurrentUserFromSharedPreference(this)

                Log.i("current user", "onCreate: ${user?.getFirstName()}")
                updateUserChats(user, ownerId)

                mChat.add(Chat(currentUserId, currentUserId, currentMessage))
                messageEt.setText("")
                chatAdapter.notifyDataSetChanged()
                recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
                var chatId: String = getChatId(ownerId, currentUserId)
                val information: MutableMap<String, String> = HashMap()
                information["sender"] = currentUserId
                information["receiver"] = ownerId
                information["message"] = currentMessage

                var reference = FirebaseDatabase.getInstance().reference.child("Chat")
                reference.child(chatId).push().setValue(information)
            }
        }
    }

    private fun updateUserChats(user: Users?, ownerId: String) {
        if(user != null ) {
           var userChats: ArrayList<String> = user.getUserChats()
            if(!(userChats!!.contains(ownerId)) && ownerId != FirebaseAuth.getInstance().currentUser.uid) {
                userChats.add(ownerId)
                user.setUserChats(userChats)
                DBUtils().updateUserWithUserChats(user)
                DBUtils().saveUserIntoSharedPreference(user, this)
            }
        }
    }

    private fun readAlreadyExistingMessages() {
        val chatId = getChatId(ownerId, currentUserId)
        var dbReference = FirebaseDatabase.getInstance().reference.child("Chat").child(chatId)

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mChat.clear()
                for (dataSnapshot in snapshot.children) {
                    val chat = dataSnapshot.getValue(Chat::class.java)!!
                    if (chat != null) {
                        Log.d("sender", "onDataChange: ${chat.getSender()}")
                        Log.d("receiver", "onDataChange: ${chat.getReceiver()}")
                        Log.d("message", "onDataChange: ${chat.getMessage()}")
                        mChat.add(chat)
                        chatAdapter.notifyDataSetChanged()
                        recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "onCancelled: " + "occurred error while getting data from chat")
            }
        })
    }

    private fun getChatId(ownerId: String, currentUserId: String): String {
        return if (ownerId[0] > currentUserId[0]) {
            ownerId + currentUserId
        } else {
            currentUserId + ownerId
        }
    }
}