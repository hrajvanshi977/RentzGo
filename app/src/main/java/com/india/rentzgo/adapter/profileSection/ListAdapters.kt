package com.india.rentzgo.adapter.profileSection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.india.rentzgo.R
import com.india.rentzgo.entities.ProfileSettings

class ListAdapters : ArrayAdapter<ProfileSettings> {

    constructor(
        context: Context,
        arrayList: ArrayList<ProfileSettings>
    ) : super(context, R.layout.profile_section_list_view, arrayList)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var convertView: View? = convertView
        var profileOptions = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.profile_section_list_view, parent, false)
        }

        var startIcon: ImageView = convertView?.findViewById(R.id.startIcon)!!
        var optionTitle: TextView = convertView?.findViewById(R.id.optionTitle)!!
        var optionSubtitle: TextView = convertView?.findViewById(R.id.optionSubtitle)!!
        var endIcon: ImageView = convertView?.findViewById(R.id.endIcon)!!

        //for settings
        startIcon?.setImageResource(profileOptions!!.startIcon)
        optionTitle?.text = profileOptions!!.optionTitle
        optionSubtitle?.text = profileOptions!!.optionSubtitle
        endIcon?.setImageResource(profileOptions!!.endIcon)
        return convertView
    }
}