package com.india.rentzgo.ui.myads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyAdsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is My Ads Fragment"
    }
    val text: LiveData<String> = _text
}