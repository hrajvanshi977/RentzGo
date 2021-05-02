package com.india.rentzgo.utils

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class PermissionsUtils : AppCompatActivity() {

    private var mLocationPermissionGranted: Boolean = false

    private val PERMISSION_REQUEST_CODE: Int = 9001

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun getPermission() {

    }


}