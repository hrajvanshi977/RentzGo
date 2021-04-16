package com.india.rentzgo.Utils

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionsUtils : AppCompatActivity() {

    private var mLocationPermissionGranted: Boolean = false

    private val PERMISSION_REQUEST_CODE: Int = 9001

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun getPermission() {

    }


}