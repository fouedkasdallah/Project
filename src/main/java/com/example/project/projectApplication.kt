package com.example.project

import android.app.Application

class projectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        application=this
    }
    companion object {
        var application : Application?=null//Context?=null
            private set
    }

}