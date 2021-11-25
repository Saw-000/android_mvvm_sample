package com.example.mvvm_sample

import android.app.Application

class ExtendedApplication: Application() {
    companion object {
        private lateinit var instance: ExtendedApplication
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}