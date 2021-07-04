package com.india.rentzgo.ui.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.india.rentzgo.LoginActivity
import com.india.rentzgo.R
import com.india.rentzgo.adapter.profileSection.ListAdapters
import com.india.rentzgo.entities.ProfileSettings
import com.india.rentzgo.utils.BaseUtil
import com.india.rentzgo.utils.DBUtils


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        var fullName = root.findViewById<TextView>(R.id.fullName)

        fullName.text = DBUtils().getCurrentUserName()

        var profilePhoto = root.findViewById<ImageView>(R.id.profilePhoto)

        Glide.with(requireContext()).load(DBUtils().getProfileUri()).into(profilePhoto)

        var listView = root.findViewById<ListView>(R.id.list_view)

        var arrayList: ArrayList<ProfileSettings> = ArrayList()

        arrayList.add(
            ProfileSettings(
                R.drawable.ic_baseline_settings_24,
                "Settings",
                "Security and privacy",
                R.drawable.ic_baseline_keyboard_arrow_right_24
            )
        )

        arrayList.add(
            ProfileSettings(
                R.drawable.ic_baseline_favorite_border_24,
                "Favourite properties",
                "See properties you liked",
                R.drawable.ic_baseline_keyboard_arrow_right_24
            )
        )

        arrayList.add(
            ProfileSettings(
                R.drawable.ic_baseline_call_24,
                "Help and Support",
                "Help centers and terms",
                R.drawable.ic_baseline_keyboard_arrow_right_24
            )
        )

        arrayList.add(
            ProfileSettings(
                R.drawable.ic_baseline_language_24,
                "Select language",
                "English",
                R.drawable.ic_baseline_keyboard_arrow_right_24
            )
        )

        arrayList.add(
            ProfileSettings(
                R.drawable.ic_baseline_exit_to_app_24,
                "Log Out",
                "",
                R.drawable.ic_baseline_keyboard_arrow_right_24
            )
        )

        val listAdapter = ListAdapters(requireContext(), arrayList)

        listView.adapter = listAdapter

        listView.isClickable = true

        listView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {

                }
                1 -> {

                }
                2 -> {

                }
                3 -> {

                }
                4 -> {
                    goForLogout()
                }
            }
        }
        return root
    }

    private fun goForLogout() {
        val baseUtil = BaseUtil()
        var builder = android.app.AlertDialog.Builder(requireContext(), R.style.AlertDialogStyle)
        builder.setMessage("Are you sure you want to Log out?")
        builder.setCancelable(true)
        builder.setNegativeButton(
            "Yes"
        ) { dialog, id ->
            try {
                baseUtil.setLoader(requireContext())
                baseUtil.logoutFromGoogle(requireContext())
                baseUtil.logoutFromFirebase()
            } catch (exception: Exception) {
                Log.d(TAG, "goForLogout: ${exception.printStackTrace()}")
            }
            Handler(Looper.myLooper()!!).postDelayed({
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }, 2000)
        }
        builder.setPositiveButton(
            "No"
        )
        { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert!!.show()
    }
}