package com.example.lab1.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "951007, Shestakovich Zahar"
    }
    val text: LiveData<String> = _text
}