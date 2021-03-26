package com.example.project.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _user : MutableLiveData<String?> =MutableLiveData(null)
    val user : LiveData<String?> = _user
    fun test(){
        _user.value="foued"
        }
}