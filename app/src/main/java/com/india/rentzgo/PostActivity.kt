package com.india.rentzgo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.india.rentzgo.ui.fragments.postFragments.PropertyTypeFragment
import com.india.rentzgo.ui.fragments.postFragments.individualRooms.IndividualRoomFragment


class PostActivity : AppCompatActivity() {
    lateinit var intentT: Intent
    val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        intentT = intent
        if (intentT.getStringExtra("IndividualRoom").equals("1")) {
            goForIndividualRoom()
            return
        }
        fragmentManager.beginTransaction().add(R.id.post_fragment_container, PropertyTypeFragment())
            .commit()
    }

    override fun finish() {
        super.finish()
        var extraString = intentT.getStringExtra("IndividualRoom")
        if (extraString.equals("1"))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

        extraString = intentT.getStringExtra("afterSuccess");
        if (extraString.equals("1")) {
            val intent = Intent(this, HousesLists::class.java)
            startActivity(intent)
        }
    }

    fun goToUploadImageAcivity() {
        val intent = Intent(this, MainActivityTest::class.java)
        startActivity(intent)
    }

    fun goForIndividualRoom() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.post_fragment_container, IndividualRoomFragment())
            .commit()
    }
}