package com.ej.twowaybinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var mutableAge = MutableLiveData<Int>(0)

    fun plus(){
        mutableAge.value = mutableAge.value?.plus(1)
    }
}