package com.india.rentzgo

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

object ImageAdapter : PagerAdapter() {
    var thisList = ArrayList<View>()

    override fun getCount(): Int {
        return thisList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = thisList.get(position)
        container.addView(view)
        return view
    }

    //see https://stackoverflow.com/questions/13664155/dynamically-add-and-remove-view-to-viewpager for more info

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(thisList.get(position))
    }

    fun addView(view: View): Int {
        return addView(view, thisList.size)
    }

    fun addView(view: View, position: Int) : Int{
        thisList.add(position, view);
        return position
    }
}