package com.india.rentzgo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.india.rentzgo.ui.postfragments.BlankFragment
import com.india.rentzgo.ui.postfragments.PostFragmentStep1
import com.india.rentzgo.ui.postfragments.PostFragmentStep2


class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

//
//
//        val list = ArrayList<String>()
//
//        list.add("Individual Rooms")
//        list.add("PGs")
//        list.add("Hostels")
//        list.add("Flats")
//        list.add("Houses/Villas")
//
//        val propertyType: ArrayAdapter<String> =
//            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
//
//        listView.adapter = propertyType

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.fragment_container, BlankFragment())
            .commit()

//        val listView: ListView = BlankFragment().listView

//        listView.setOnItemClickListener { parent, view, position, id ->
//            Log.i("this is the position", position.toString() + " " + listView.get(position))
//
//            val fragmentTransaction = supportFragmentManager.beginTransaction()
//            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
//            fragmentTransaction.replace(R.id.fragment_container, PostFragmentStep2()).commit()
//        }
    }

    fun replaceFragmentWithAnimation(fragment: Fragment, tag: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_left,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_left
        )
//        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

    fun finish(view: View) {
        finish()
    }
}